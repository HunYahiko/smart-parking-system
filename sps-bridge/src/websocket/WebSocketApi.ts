import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

export class WebSocketApi {

    static webSocketEndPoint: string = "http://localhost:8080/parkingApp";
    static serverConnection: Stomp.Client;

    public static getConnection() : Stomp.Client {
        const websocket = new SockJS(this.webSocketEndPoint);
        return Stomp.over(websocket);
    }
}
