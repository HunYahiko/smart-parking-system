import {Topic} from "./topics/Topic";
import {Parser} from "./parsers/Parser";
import {MessageParser} from "./parsers/MessageParser";
import {Message} from "../message/Message";
import {Listener} from "./listeners/Listener";
import {FunctionMessageParser} from "./parsers/FunctionMessageParser";
import {FunctionMessageTopic} from "./topics/FunctionMessageTopic";

export abstract class GenericMessageTPL<T extends Topic, P extends MessageParser<Message, string>, L extends Listener<Message>> {
    protected topic: T;
    protected parser: P;
    protected listener: L;

    protected constructor(topic: T, parser: P, listener: L) {
        this.topic = topic;
        this.parser = parser;
        this.listener = listener;
    }

    getParser(): P {
        return this.parser;
    }

    getTopic(): T {
        return this.topic;
    }

    getListener(): L {
        return this.listener;
    }
}
