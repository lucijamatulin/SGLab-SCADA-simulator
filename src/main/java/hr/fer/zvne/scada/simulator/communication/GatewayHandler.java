package hr.fer.zvne.scada.simulator.communication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hr.fer.zvne.scada.simulator.communication.workers.WorkerInterface;
import hr.fer.zvne.scada.simulator.config.ConfigurationModel;
import hr.fer.zvne.scada.simulator.models.Data;
import hr.fer.zvne.scada.simulator.models.Message;
import hr.fer.zvne.scada.simulator.models.messages.*;
import hr.fer.zvne.scada.simulator.models.values.ConnectionStatus;
import hr.fer.zvne.scada.simulator.models.values.Quality;
import hr.fer.zvne.scada.simulator.models.values.Source;
import hr.fer.zvne.scada.simulator.models.values.Type;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GatewayHandler {
    private Socket socket = null;
    private ServerSocket server = null;
    private int port;
    private ObjectMapper objectMapper = new ObjectMapper();
    private InputStream in = null;
    private OutputStream out = null;
    private int messageLength;
    private boolean running;
    private BlockingQueue<Message> queue;
    private RoutingHandler router;
    private DeviceRegistry deviceRegistry;
    private Boolean authenticated = false;
    private String username;
    private String password;

    public GatewayHandler(ConfigurationModel configurationModel) {
        this.port = configurationModel.getCommunication().getPort();
        this.messageLength = configurationModel.getCommunication().getMessageLength();
        this.username = configurationModel.getAuthentication().getUsername();
        this.password = configurationModel.getAuthentication().getPassword();
        this.queue = new LinkedBlockingDeque<>();
        this.deviceRegistry = new DeviceRegistry(configurationModel, queue);
        this.router = new RoutingHandler(configurationModel, deviceRegistry);
    }

    public void gatewayInit() {
        this.running = true;
        try {
            server = new ServerSocket(this.port);
            System.out.println("Server started.");
            System.out.println("Waiting for a client...");

            socket = server.accept();
            System.out.println("Client accepted. \n\n");

            in = socket.getInputStream();
            out = socket.getOutputStream();

            startProcessingQueueMessages();
            startReceivingClientMessages();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMessage(ObjectNode response, OutputStream out) {
        try {
            byte[] messageBytes = objectMapper.writeValueAsBytes(response);
            byte[] messageLengthBytes = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(messageBytes.length).array();

            out.write(messageLengthBytes);
            out.write(messageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObjectNode prepareResponse(Message message) {
        ObjectNode response = null;
        String messageType = message.getType();
        String key;
        Object value;
        WorkerInterface worker;

        switch (messageType) {
            case "authentication_request":
                AuthenticationRequest authenticationRequest = objectMapper.convertValue(message, AuthenticationRequest.class);
                if (authenticationRequest.getBody().getUsername().equals(username) &&
                        authenticationRequest.getBody().getUsername().equals(password)) {
                    authenticated = true;
                }

                AuthenticationResponse authenticationResponse = new AuthenticationResponse("authentication_response",
                        new AuthenticationResponseBody(authenticated, ConnectionStatus.CONNECTED.name(),
                                new Data[]{new Data("SIM.mjerenja1:97", 1.05, Quality.GOOD.name(),
                                        Timestamp.valueOf(LocalDateTime.now()),
                                        Type.EVENT.name(), Source.APPLICATION.name())}));


                response = authenticationResponse.toObjectNode();
                break;

            case "command_request":
                CommandRequest commandRequest = objectMapper.convertValue(message, CommandRequest.class);
                key = commandRequest.getBody().getData()[0].getKey();
                value = commandRequest.getBody().getData()[0].getValue();

                worker = router.route(key, deviceRegistry);

                if (worker != null) {
                    worker.processCommand(key, value, deviceRegistry);
                } else {
                    System.out.println("The process key does not belong to any device.");
                }

                CommandResponse commandResponse = new CommandResponse("command_response",
                        new CommandResponseBody(commandRequest.getBody().getId(), new Boolean[]{true}));
                response = commandResponse.toObjectNode();
                break;

            case "change_data_request":
                ChangeDataRequest changeDataRequest = objectMapper.convertValue(message, ChangeDataRequest.class);
                key = changeDataRequest.getBody().getData()[0].getKey();
                value = changeDataRequest.getBody().getData()[0].getValue();

                worker = router.route(key, deviceRegistry);

                if (worker != null) {
                    worker.processCommand(key, value, deviceRegistry);
                } else {
                    System.out.println("The process key does not belong to any device.");
                }

                ChangeDataResponse changeDataResponse = new ChangeDataResponse("change_data_response",
                        new ChangeDataResponseBody(changeDataRequest.getBody().getId(), new Boolean[]{true}));

                response = changeDataResponse.toObjectNode();
                break;

            case "data_changed_unsolicited":
                DataChangedUnsolicited dataChangedUnsolicited = objectMapper.convertValue(message, DataChangedUnsolicited.class);
                response = dataChangedUnsolicited.toObjectNode();
                break;

        }
        return response;
    }

    private Message readMessage(int messageLength, InputStream in) {
        byte[] messageLen;
        int len;
        Message message = null;

        try {
            messageLen = in.readNBytes(this.messageLength);

            len = ByteBuffer.wrap(messageLen).order(ByteOrder.BIG_ENDIAN).getInt();
            byte[] messageBuffer;

            messageBuffer = in.readNBytes(len);
            message = objectMapper.readValue(messageBuffer, Message.class);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public void stopServer() {
        this.running = false;
    }

    private void startProcessingQueueMessages() {
        new Thread(() -> {
            ObjectNode response = null;

            while (true) {
                try {
                    response = prepareResponse(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writeMessage(response, out);
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startReceivingClientMessages() throws IOException {
        while (running) {
            if (!socket.isConnected()) {
                System.out.println("Connection lost.");
                break;
            }

            while (in.available() != 0) {
                Message message = readMessage(messageLength, in);
                System.out.println(message.toString());
                ObjectNode response = prepareResponse(message);
                writeMessage(response, out);
                out.flush();
            }
        }
    }
}


