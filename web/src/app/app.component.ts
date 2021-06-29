import { Component, OnInit } from '@angular/core';
import { BackendService } from './services/backend.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Configuration, Authentication, Communication, CommandProperties, Info, Machine, MachineProperties } from './models/configuration';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {
  connected = true;


  authentication: Authentication = {
    username: undefined,
    password: undefined
  }
  communication: Communication = {
    messageLength: 0, port: 0
  }
  info: Info = {
    name: ""
  }
  machines: Array<Machine> = new Array<Machine>();
  commands: Map<string, CommandProperties> = new Map<string, CommandProperties>();
  data: Map<string, string> = new Map<string, string>();
  measurements: Map<string, MachineProperties> = new Map<string, MachineProperties>();
  machine: Machine = {
    commands: this.commands, data: this.data, id: 0, measurements: this.measurements
  }
  measurement: MachineProperties = {
    bounds: undefined, dataType: "", key: "", rampDown: 0, rampUp: 0
  }

  // @ts-ignore
  configuration: Configuration = {
    "authentication": this.authentication, "communication": this.communication,
    "info": this.info, "version": "", "machines": this.machines
  }


  constructor(private api: BackendService, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.api.getConfiguration().subscribe(
      (response: Configuration) => {
        this.configuration.info = response.info;
        this.configuration.authentication = response.authentication;
        this.configuration.communication = response.communication;
        this.configuration.machines = response.machines;
        console.log(this.configuration);
      },

      error => {
        this._snackBar.open(error, null, {
          duration: 3000
        });
      }
    );
  }

  connect(): void {
    this.connected = true;
          }


  disconnect(): void {
    this.connected = false;
  }


  stringify(val: any) {
    return JSON.stringify(val);
  }
}
