import { Injectable } from '@angular/core';
import {RxStomp, RxStompConfig} from '@stomp/rx-stomp';
import * as SockJS from 'sockjs-client';
import {environment} from '../../environments/environment';

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
    rxStompConfig.webSocketFactory = () => new SockJS(environment.back_end_url + '/parkingApp');
    rxStompConfig.connectHeaders = {
      login: 'admin',
      password: 'admin'
    };
    return rxStompConfig;
  }
}
