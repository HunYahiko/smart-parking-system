import {ProtocolMessage} from "../message/ProtocolMessage";
import {ResponseStatus} from "../enum/ResponseStatus";
import {ResponseMessage} from "../message/ResponseMessage";
import {SPSProtocolMessage} from "../message/SPSProtocolMessage";
import {MessageType} from "../message/MessageType";
import SerialPort = require("serialport");
import Delimiter = SerialPort.parsers.Delimiter;

export class SerialPortWrapper {

    private serialPort: SerialPort;
    private readonly parser: Delimiter;

    public constructor(
        serialPort: SerialPort,
        parser: Delimiter
    ) {
        this.serialPort = serialPort;
        this.parser = parser;
    }

    public async openAndPipePort() {
        return new Promise<void>(((resolve, reject) => {
            this.serialPort.pipe(this.parser);
            this.serialPort.open((error) => reject(error));
            while(!this.serialPort.isOpen);
            resolve();
        }));
    }


    public closePort() {
        this.serialPort.close((error => {
            console.log("Failed to close port, most probably was already closed");
        }))
    }

    public async sendMessageAndWaitResponse(message: ProtocolMessage): Promise<ResponseMessage> {
        return new Promise<ResponseMessage>(resolve => {
            const readTimeout = new Promise<ResponseMessage>(resolve => {
                const responseMessage = SerialPortWrapper
                    .createResponseMessage(<SPSProtocolMessage>message, ResponseStatus.UNRESPONSIVE);
                setTimeout(() => resolve(responseMessage), 300);
            });

            Promise.race([this.sendAndWaitResponse(message), readTimeout])
                .then(data => {
                    resolve(data);
                    this.parser.removeAllListeners('data');
                });
        });
    }

    private sendAndWaitResponse(message: ProtocolMessage): Promise<ResponseMessage> {
        return new Promise<ResponseMessage>(resolve => {
            const serialMessage: string = message.toSerialString();
            console.log("sendAndWaitResponse: This is sending serialMessage: " + serialMessage);
            this.serialPort.write(serialMessage);
            this.parser.once('data', data => {
                const responseMessage: ResponseMessage = SerialPortWrapper
                    .createResponseMessage(<SPSProtocolMessage>message, ResponseStatus.OK);
                responseMessage.responseData = data;
                console.log("sendAndWaitResponse: This is what we got as response: " + data);
                resolve(responseMessage);
                this.parser.off('data',() => {});
            });
        })
    }

    private static createResponseMessage(protocolMessage: SPSProtocolMessage, responseStatus: ResponseStatus): ResponseMessage {
        const responseMessage: ResponseMessage = new ResponseMessage(responseStatus, null, protocolMessage.parkingLotId);
        responseMessage.id = protocolMessage.id;
        responseMessage.address = protocolMessage.address;
        responseMessage.messageType = MessageType.RESPONSE_MESSAGE;
        return responseMessage;
    }
}

