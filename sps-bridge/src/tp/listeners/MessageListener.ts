import {Listener} from "./Listener";
import {Message} from "../../message/Message";
import {MessageQueue} from "../../queue/MessageQueue";
import {ProtocolMessage} from "../../message/ProtocolMessage";

export abstract class MessageListener<M extends Message> implements Listener<M> {

    inboundQueue: MessageQueue<Message, ProtocolMessage>;

    protected constructor(inboundQueue: MessageQueue<Message, ProtocolMessage>) {
        this.inboundQueue = inboundQueue;
    }

    public abstract onMessage(message: M): void;
}
