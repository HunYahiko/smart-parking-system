import {WebSocketConnectionImpl} from "./WebSocketConnectionImpl";
import {RxStomp} from "@stomp/rx-stomp";
import {GenericMessageTPL} from "../tp/GenericMessageTPL";
import {Message} from "../message/Message";
import {MessageParser} from "../tp/parsers/MessageParser";
import {Topic} from "../tp/topics/Topic";
import {map} from "rxjs/operators";
import {Listener} from "../tp/listeners/Listener";
import {forEach} from "typescript-collections/dist/lib/arrays";

export class WebSocketConnectionWrapper {

    private _websocketConnection: WebSocketConnectionImpl;

    public constructor(websocketConnection: WebSocketConnectionImpl) {
        this._websocketConnection = websocketConnection;
    }

    public connect() {
        this._websocketConnection.connect(() => {}, () => {});
    }

    public getConnection(): RxStomp {
        return this._websocketConnection.getConnection();
    }

    public registerTPL(tpl: GenericMessageTPL<Topic, MessageParser<Message, string>, Listener<Message>>) {
        const destination = tpl.getTopic().getDestination();
        const parser = tpl.getParser();
        const listener = tpl.getListener();
        console.log('watching topic ' + destination);
        this.getConnection().watch(destination).pipe(map((message) => {
            const messageArray: Array<Message> = new Array<Message>();
            const jsonMessage: string = JSON.parse(message.body);
            for (let i = 0; i < jsonMessage.length; ++i) {
                messageArray.push(parser.parse(jsonMessage[i]));
            }
            return messageArray;
        })).subscribe((messages) => {
            console.log("New messages from " + destination + " destination");
            for(let message of messages) {
                listener.onMessage(message);
            }
        });
    }

    get websocketConnection(): WebSocketConnectionImpl {
        return this._websocketConnection;
    }

    set websocketConnection(value: WebSocketConnectionImpl) {
        this._websocketConnection = value;
    }
}


