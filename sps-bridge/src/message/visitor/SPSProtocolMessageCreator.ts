import {ProtocolMessageCreator} from "./ProtocolMessageCreator";
import {FunctionMessage} from "../FunctionMessage";
import {ProtocolMessage} from "../ProtocolMessage";
import {SPSProtocolMessage} from "../SPSProtocolMessage";

export class SPSProtocolMessageCreator implements ProtocolMessageCreator {

    createFrom(message: FunctionMessage): ProtocolMessage {
        return new SPSProtocolMessage(message.id, message.address, message.functionCode, message.parkingLotId);
    }

}
