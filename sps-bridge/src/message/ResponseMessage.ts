import {Message} from "./Message";
import {ProtocolMessageCreator} from "./visitor/ProtocolMessageCreator";
import {ProtocolMessage} from "./ProtocolMessage";
import {ResponseStatus} from "../enum/ResponseStatus";
import {FunctionCode} from "../enum/FunctionCode";

export class ResponseMessage extends Message {

    private _responseStatus: ResponseStatus;
    private _responseData: string;
    private _parkingLotId: string;
    private _functionCode: FunctionCode;

    constructor(responseStatus: ResponseStatus, responseData: string, parkingLotId: string) {
        super();
        this._responseStatus = responseStatus;
        this._responseData = responseData;
        this._parkingLotId = parkingLotId;
    }

    get responseStatus(): ResponseStatus {
        return this._responseStatus;
    }

    set responseStatus(value: ResponseStatus) {
        this._responseStatus = value;
    }

    get responseData(): string {
        return this._responseData;
    }

    set responseData(value: string) {
        this._responseData = value;
    }

    get parkingLotId(): string {
        return this._parkingLotId;
    }

    set parkingLotId(value: string) {
        this._parkingLotId = value;
    }

    get functionCode(): FunctionCode {
        return this._functionCode;
    }

    set functionCode(value: FunctionCode) {
        this._functionCode = value;
    }

    protected toJSON() {
        return {
            id: this.id,
            address: this.address,
            messageType: this.messageType,
            responseData: this.responseData,
            responseStatus: this.responseStatus,
            parkingLotId: this.parkingLotId,
            functionCode: this.functionCode
        };
    }

    createProtocolMessage(protocolMessageCreator: ProtocolMessageCreator): ProtocolMessage {
        return undefined;
    }
}
