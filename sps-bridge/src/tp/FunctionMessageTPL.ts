import {GenericMessageTPL} from "./GenericMessageTPL";
import {FunctionMessageTopic} from "./topics/FunctionMessageTopic";
import {FunctionMessageParser} from "./parsers/FunctionMessageParser";
import {FunctionMessageListener} from "./listeners/FunctionMessageListener";

export class FunctionMessageTPL extends GenericMessageTPL<FunctionMessageTopic, FunctionMessageParser, FunctionMessageListener> {

    constructor(topic: FunctionMessageTopic,
                parser: FunctionMessageParser,
                listener: FunctionMessageListener) {
        super(topic, parser, listener);
    }
}
