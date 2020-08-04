import {MessageParser} from "./MessageParser";
import {FunctionMessage} from "../../message/FunctionMessage";
import {FunctionMessageBuilder} from "../../message/builders/FunctionMessageBuilder";

export class FunctionMessageParser implements MessageParser<FunctionMessage, string> {

    parse(object: string): FunctionMessage {
        const functionMessage: FunctionMessage = new FunctionMessage();
        const functionMessageBuilder: FunctionMessageBuilder = new FunctionMessageBuilder();
        return functionMessageBuilder.build(functionMessage, object);
    }

}
