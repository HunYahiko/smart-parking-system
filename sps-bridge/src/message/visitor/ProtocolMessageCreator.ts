import {ProtocolMessage} from "../ProtocolMessage";
import {FunctionMessage} from "../FunctionMessage";

export interface ProtocolMessageCreator {
    createFrom(message: FunctionMessage): ProtocolMessage;
}
