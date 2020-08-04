import {Connection} from "./Connection";
import {RxStomp, RxStompConfig} from "@stomp/rx-stomp";
import SockJS from "sockjs-client";
import {inject, injectable} from "inversify";


@injectable()
export class WebSocketConnectionImpl implements Connection<RxStomp> {

    private rxStomp: RxStomp;
    private rxStompConfig: RxStompConfig;

    public constructor() {}

    connect(onConnectedCallback, onDisconnectedCallback) {
        this.configureConnection();
        this.rxStomp.activate();
    }

    disconnect() {
        this.rxStomp.deactivate();
    }

    getConnection(): RxStomp {
        return this.rxStomp;
    }

    onConnected(onConnectedCallback) {
    }

    onDisconnected(onDisconnectedCallback) {
    }

    private configureConnection() {
        this.initConfig();
        this.rxStomp.configure(this.rxStompConfig);
    }

    private initConfig() {
        if (!this.rxStomp || !this.rxStompConfig) {
            this.rxStomp = new RxStomp();
            this.rxStompConfig = new RxStompConfig();
        }
        this.rxStompConfig.webSocketFactory = () => {
            return new SockJS(
                'http://localhost:8080/parkingApp');
        };

        this.rxStompConfig.connectHeaders = {
            bridge_id: 'BRIDGE1-GROUNDLEVEL'
        };

        // this.rxStompConfig.debug = (msg => {
        //    console.log(msg);
        // });
    }
}
