import {MessageQueue} from "./MessageQueue";
import {Message} from "../message/Message";
import {injectable} from "inversify";
import {FunctionMessage} from "../message/FunctionMessage";
import {SPSProtocolMessageCreator} from "../message/visitor/SPSProtocolMessageCreator";
import {ProtocolMessage} from "../message/ProtocolMessage";
import {SerialPortWrapper} from "../serial/SerialPortWrapper";
import {Handler} from "../handler/Handler";
import {ResponseMessage} from "../message/ResponseMessage";
import {WebSocketConnectionWrapper} from "../websocket/WebSocketConnectionWrapper";
import {stringValueOfCode} from "../enum/FunctionCode";


@injectable()
export class IncomingMessageQueue extends MessageQueue<Message, ProtocolMessage> {

    private webSocketConnectionWrapper: WebSocketConnectionWrapper;

    public constructor(messageHandler: Handler<ProtocolMessage, ResponseMessage>,
                       webSocketConnectionWrapper: WebSocketConnectionWrapper) {
        super(messageHandler);
        this.webSocketConnectionWrapper = webSocketConnectionWrapper;
    }

    handleMessage(input: Message, cb): void {
        console.log("Processing message...");
        this.convertMessage(input)
            .then(protocolMessage => {
                this.messageHandler.handle(protocolMessage)
                    .then(responseMessage => {
                        const inputMessage: FunctionMessage = <FunctionMessage> input;
                        const responseMessageCast = <ResponseMessage> responseMessage;
                        responseMessageCast.functionCode = inputMessage.functionCode;
                        this.publishToServer(responseMessageCast);
                        cb(null, 'message ' + input.toString() +
                           ' was sent and processed with response ' + responseMessage + ' status ' + responseMessageCast.responseStatus);
                    });
            });
    }

    private convertMessage(message: Message): Promise<ProtocolMessage> {
        return new Promise<ProtocolMessage>(((resolve, reject) => {
            const protocolMessage: ProtocolMessage = message.createProtocolMessage(new SPSProtocolMessageCreator());
            resolve(protocolMessage);
        }));
    }

    private publishToServer(responseMessage: ResponseMessage) {
        const json: string = JSON.stringify(responseMessage);
        const destination: string = '/app/' + stringValueOfCode(responseMessage.functionCode);
        console.log("publishToServer: message: " + json + ', destination: ' + destination);
        this.webSocketConnectionWrapper.getConnection()
            .publish({destination: destination, body: json});
    }

}
