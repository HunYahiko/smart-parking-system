import {Parser} from "./Parser";
import {Message} from "../../message/Message";

export interface MessageParser<M extends Message, T> extends Parser<M, T> {
}
