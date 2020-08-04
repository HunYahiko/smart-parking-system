// import {inject, injectable, named} from "inversify";
// import {SerialPortWrapper} from "../serial/SerialPortWrapper";
// import {MessageHandler} from "./MessageHandler";
// import {Message} from "../message/Message";
// import {MessageQueue} from "../queue/MessageQueue";
//
// export class InboundMessageHandler implements MessageHandler<Message> {
//
//     private serialPortWrapper: SerialPortWrapper;
//
//     public constructor(serialPortWrapper: SerialPortWrapper
//     ) {
//         this.serialPortWrapper = serialPortWrapper;
//     }
//
//     async handle(message: Message) {
//         sendSerialData(message)
//             .then((responseMessage) => {
//                 // create new ResponseMessage and put it into outboundQueue;
//             });
//         while(true) {
//             const message: Message = this.incomingMessageQueue.getNextMessage();
//
//             if (message !== undefined) {
//                 console.log("sending message...");
//                 this.serialPortWrapper.openAndPipePort().then(() => {
//                     this.sendDataViaSerialAndWait(message)
//                         .then((responseMessage) => {
//                             console.log("response gotten");
//                         })
//                         .catch((error) => {
//                             console.log(error);
//                         })
//                         .finally(() => {
//                             this.serialPortWrapper.closePort();
//                         });
//                 });
//             }
//         }
//         /*
//         TODO
//         1. send data to serial and await for response. If response is error, put in outbound queue
//         a new UnresponsiveParkingLot message;
//         If everything is fine, send ParkingLotDataMessage;
//          */
//     }
//
//     private sendSerialData
//
//
//     private async sendDataViaSerialAndWait(message: Message) {
//         this.serialPortWrapper.sendMessageAndWaitResponse(message);
//     }
// }
