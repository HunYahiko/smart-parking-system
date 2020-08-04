import {MessageBuilder} from "./MessageBuilder";
import {FunctionMessage} from "../FunctionMessage";

export class FunctionMessageBuilder extends MessageBuilder {

    public build(message: FunctionMessage, jsonString: string): FunctionMessage {
        message = <FunctionMessage> super.build(message, jsonString);
        message.functionCode = jsonString['functionCode'];
        message.parkingLotId = jsonString['parkingLotId'];

        return message;
    }
}
