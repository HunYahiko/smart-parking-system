// import {Connection} from "./websocket/Connection";
// import {MessageType} from "./message/MessageType";
// import container from "./ioc/Installer";
// import {MessageQueue} from "./queue/MessageQueue";
// import {Message} from "./message/Message";
// import {inject, injectable, named} from "inversify";
// import {WebSocketConnectionWrapper} from "./websocket/WebSocketConnectionWrapper";
// import {WebSocketConnectionImpl} from "./websocket/WebSocketConnectionImpl";
// import {map} from "rxjs/operators";
// import {RxStomp} from "@stomp/rx-stomp";
//
// @injectable()
// export class RPiApp {
//
//     private readonly connection: Connection<RxStomp>;
//     private retriesCounter: number = 0;
//     private inboundMessageQueue: MessageQueue<Message>;
//     private outboundMessageQueue: MessageQueue<Message>;
//
//     public constructor(connection: Connection<RxStomp>,
//                        inboundMessageQueue: MessageQueue<Message>,
//                        outboundMessageQueue: MessageQueue<Message>) {
//         this.connection = connection;
//         this.inboundMessageQueue = inboundMessageQueue;
//         this.outboundMessageQueue = inboundMessageQueue;
//     }
//
//     public run() {
//         this.connection.connect(() => {}, () => {});
//         this.registerListenersToTopics();
//     }
//
//     private registerListenersToTopics() {
//         // const statusMessageTPL: StatusMessageTPL = new StatusMessageTPL();
//         // const webSocketConnectionWrapper: WebSocketConnectionWrapper
//         //     = new WebSocketConnectionWrapper(<WebSocketConnectionImpl>this.connection);
//         // webSocketConnectionWrapper.register(statusMessageTPL);
//     }
//
//
//     private onDisconnected() {
//         console.log("RPiApp.onDisconnected");
//         this.retriesCounter++;
//         console.log("retriesCounter: " + this.retriesCounter);
//         if (this.retriesCounter >= 10) {
//             console.log("Failed to reconnect after 10 retries!");
//             process.exit(-1);
//         } else {
//             console.log("Attempting to reconnect.");
//             this.connection.connect(this.onConnected.bind(this), this.onDisconnected.bind(this));
//         }
//     }
//
//     private onConnected() {
//         console.log("RPiApp.onConnected");
//         this.resetRetriesCounter();
//         //this.initListeners();
//     }
//
//     private resetRetriesCounter() {
//         this.retriesCounter = 0;
//     }
// }
