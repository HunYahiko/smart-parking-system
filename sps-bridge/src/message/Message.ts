import {MessageType} from "./MessageType";
import {ProtocolMessage} from "./ProtocolMessage";
import {ProtocolMessageCreator} from "./visitor/ProtocolMessageCreator";

export abstract class Message {

    private _id: string;
    private _address: number;
    private _messageType: MessageType;

    protected constructor() {
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get address(): number {
        return this._address;
    }

    set address(value: number) {
        this._address = value;
    }

    get messageType(): MessageType {
        return this._messageType;
    }

    set messageType(value: MessageType) {
        this._messageType = value;
    }

    protected toJSON() {
        return {
            id: this.id,
            address: this.address,
            messageType: this.messageType
        };
    }

    public toString() {
        return this.id + ' ' + this.address + ' ' + this.messageType;
    }

    public abstract createProtocolMessage(protocolMessageCreator: ProtocolMessageCreator) : ProtocolMessage;

}
