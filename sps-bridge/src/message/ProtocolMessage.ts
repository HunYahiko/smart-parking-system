import {Message} from "./Message";

export abstract class ProtocolMessage {
    private _id: string;
    private _address: number;

    protected constructor(id: string, address: number) {
        this._id = id;
        this._address = address;
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

    public abstract toSerialString(): string;
}
