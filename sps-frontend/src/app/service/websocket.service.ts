import { Injectable } from '@angular/core';
import {RxStomp, RxStompConfig} from '@stomp/rx-stomp';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private rxStomp: RxStomp;

  constructor() { }

  getConnection(): RxStomp {
    if (this.rxStomp == null) {
      this.rxStomp = new RxStomp();
      this.rxStomp.configure(this.getStompConfig());
      this.rxStomp.activate();
    }
    return this.rxStomp;
  }

  private getStompConfig(): RxStompConfig {
    const rxStompConfig = new RxStompConfig();
    rxStompConfig.webSocketFactory = () => new SockJS('http://localhost:8080/parkingApp');
    rxStompConfig.connectHeaders = {
      login: 'admin',
      password: 'admin'
    };
    return rxStompConfig;
  }
}
