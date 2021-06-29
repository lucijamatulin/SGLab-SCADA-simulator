export interface Info {
  name: string;
}

export interface Authentication {
  username: string;
  password: string;
}

export interface Communication {
  port: number;
  messageLength: number;
}

export interface MachineProperties {
  key: string;
  dataType: string;
  bounds: Map<string, number>;
  rampUp: number;
  rampDown: number;
}

export interface CommandProperties {
  key: string;
  messageType: string;
  dataType: string;
}

export interface Machine {
  id: number;
  data: Map<string, string>;
  measurements: Map<string, MachineProperties>;
  commands: Map<string, CommandProperties>;
}

export interface Configuration {
  version: string;
  info: Info;
  authentication: Authentication;
  communication: Communication;
  machines: Array<Machine>;
}
