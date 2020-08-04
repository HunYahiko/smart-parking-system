import {ProtocolMessage} from "../message/ProtocolMessage";
import {Handler} from "./Handler";

export interface ProtocolMessageHandler<M extends ProtocolMessage, R> extends Handler<M, R> {

    handle(protocolMessage: M): Promise<R>;
}
