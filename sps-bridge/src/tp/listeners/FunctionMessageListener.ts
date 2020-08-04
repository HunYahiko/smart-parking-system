import {MessageListener} from "./MessageListener";
import {FunctionMessage} from "../../message/FunctionMessage";
import {MessageQueue} from "../../queue/MessageQueue";
import {Message} from "../../message/Message";
import {ProtocolMessage} from "../../message/ProtocolMessage";

export class FunctionMessageListener extends MessageListener<FunctionMessage> {

    constructor(inboundQueue: MessageQueue<Message, ProtocolMessage>) {
        super(inboundQueue);
    }

    onMessage(message: FunctionMessage) {
        this.inboundQueue.enqueueMessage(message);
    }

}
