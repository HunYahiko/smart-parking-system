// import {MessageListener} from "./MessageListener";
// import {CommandMessage} from "../message/CommandMessage";
// import {inject, injectable, named} from "inversify";
// import {MessageQueue} from "../queue/MessageQueue";
// import {Message} from "../message/Message";
//
//
// @injectable()
// export class CommandMessageListener implements MessageListener<CommandMessage> {
//
//     private messageQueue: MessageQueue<Message>;
//
//     public constructor(
//         @inject("MessageQueue") @named("incoming") messageQueue: MessageQueue<Message>,
//     ) {
//         this.messageQueue = messageQueue;
//     }
//
//     onMessage(message: CommandMessage) {
//         console.log("got new command message: " + message);
//         this.messageQueue.enqueueMessage(message);
//     }
// }
