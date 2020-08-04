import {ProtocolMessage} from "./ProtocolMessage";
import {FunctionCode} from "../enum/FunctionCode";

export class SPSProtocolMessage extends ProtocolMessage {

    private _functionCode: FunctionCode;
    private _parkingLotId: string;

    constructor(id: string, address: number, functionCode: FunctionCode, parkingLotId: string) {
        super(id, address);
        this.functionCode = functionCode;
        this.parkingLotId = parkingLotId;
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

    toSerialString(): string {
        return this.address + "," + this.functionCode + '\n';
    }


}
