// import {MessageListener} from "./MessageListener";
// import {StatusMessage} from "../message/StatusMessage";
// import {inject, injectable, named} from "inversify";
// import {Message} from "../message/Message";
// import {MessageQueue} from "../queue/MessageQueue";
// import {MessageType} from "../message/MessageType";
//
//
// @injectable()
// export class StatusMessageListener implements MessageListener<StatusMessage> {
//
//     private messageQueue: MessageQueue<Message>;
//
//     public constructor(
//         @inject("MessageQueue") @named("incoming") messageQueue: MessageQueue<Message>
//     ) {
//         this.messageQueue = messageQueue;
//     }
//
//     onMessage(message: StatusMessage) {
//         console.log(message.toString());
//         this.messageQueue.enqueueMessage(message);
//     }
// }
