import {Message} from "./Message";
import {ProtocolMessageCreator} from "./visitor/ProtocolMessageCreator";
import {ProtocolMessage} from "./ProtocolMessage";
import {FunctionCode} from "../enum/FunctionCode";

export class FunctionMessage extends Message {

    private _functionCode: FunctionCode;
    private _parkingLotId: string;

    constructor() {
        super();
    }

    get functionCode(): FunctionCode {
        return this._functionCode;
    }

    set functionCode(value: FunctionCode) {
        this._functionCode = value;
    }

    get parkingLotId(): string {
        return this._parkingLotId;
    }

    set parkingLotId(value: string) {
        this._parkingLotId = value;
    }

    createProtocolMessage(protocolMessageCreator: ProtocolMessageCreator): ProtocolMessage {
        return protocolMessageCreator.createFrom(this);
    }

    public toString(): string {
        return super.toString() + ',' + this.functionCode + ',' + this.parkingLotId;
    }
}



