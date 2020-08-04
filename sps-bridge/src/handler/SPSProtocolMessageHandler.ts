import {ProtocolMessageHandler} from "./ProtocolMessageHandler";
import {SPSProtocolMessage} from "../message/SPSProtocolMessage";
import {SerialPortWrapper} from "../serial/SerialPortWrapper";
import {ResponseMessage} from "../message/ResponseMessage";


export class SPSProtocolMessageHandler implements ProtocolMessageHandler<SPSProtocolMessage, ResponseMessage> {

    private readonly _serialPortWrapper: SerialPortWrapper;

    constructor(serialPortWrapper: SerialPortWrapper) {
        this._serialPortWrapper = serialPortWrapper;
    }

    async handle(protocolMessage: SPSProtocolMessage): Promise<ResponseMessage> {
        return new Promise<ResponseMessage>(resolve => {
            this._serialPortWrapper.sendMessageAndWaitResponse(protocolMessage)
                .then(responseMessage => {
                    resolve(responseMessage);
                });
        });
    }
}
