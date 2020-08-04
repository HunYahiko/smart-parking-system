
import {Message} from "../message/Message";
import {Handler} from "./Handler";

export interface MessageHandler<M extends Message, R> extends Handler<M, R> {

    handle(message: M): Promise<R>;
}
