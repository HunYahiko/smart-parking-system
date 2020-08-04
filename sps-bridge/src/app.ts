import "reflect-metadata";
import {WebSocketConnectionImpl} from "./websocket/WebSocketConnectionImpl";
import {IncomingMessageQueue} from "./queue/IncomingMessageQueue";
import {FunctionMessageTPL} from "./tp/FunctionMessageTPL";
import {FunctionMessageTopic} from "./tp/topics/FunctionMessageTopic";
import {FunctionMessageParser} from "./tp/parsers/FunctionMessageParser";
import {FunctionMessageListener} from "./tp/listeners/FunctionMessageListener";
import {WebSocketConnectionWrapper} from "./websocket/WebSocketConnectionWrapper";
import {GenericMessageTPL} from "./tp/GenericMessageTPL";
import {Topic} from "./tp/topics/Topic";
import {MessageParser} from "./tp/parsers/MessageParser";
import {Message} from "./message/Message";
import {Listener} from "./tp/listeners/Listener";
import {SPSProtocolMessageHandler} from "./handler/SPSProtocolMessageHandler";
import SerialPort from "serialport";
import {SerialPortWrapper} from "./serial/SerialPortWrapper";
import Delimiter = SerialPort.parsers.Delimiter;
import Readline from "@serialport/parser-readline";

Object.assign(global, { WebSocket: require('websocket').w3cwebsocket });

const webSocketConnection = new WebSocketConnectionImpl();
const serialPort = new SerialPort('COM11', {
    autoOpen: false,
    baudRate: 9600,
    dataBits: 8,
    stopBits: 1
});
const serialParser: Delimiter = serialPort.pipe(new Readline({delimiter: '\n'}));
const serialPortWrapper: SerialPortWrapper = new SerialPortWrapper(serialPort, serialParser);

serialPort.open(error => {
  if (error) {
      console.log("failed to open");
  }
});
serialPort.on('error', error => {
    console.log(error);
});
serialPort.on('open', () => {
    const webSocketConnectionWrapper = new WebSocketConnectionWrapper(webSocketConnection);
    const inboundMessageQueue = new IncomingMessageQueue(new SPSProtocolMessageHandler(serialPortWrapper),
        webSocketConnectionWrapper);
    inboundMessageQueue.startQueueProcessing();

    const genericTPL = new FunctionMessageTPL(new FunctionMessageTopic(),
        new FunctionMessageParser(),
        new FunctionMessageListener(inboundMessageQueue));


    webSocketConnectionWrapper.connect();
    webSocketConnectionWrapper.registerTPL(genericTPL);
    webSocketConnectionWrapper.getConnection();
});

function f(genericTPL: GenericMessageTPL<Topic, MessageParser<Message, string>, Listener<Message>>) {
    console.log(genericTPL.getTopic().getDestination());
}
