import {Topic} from "./Topic";

export class FunctionMessageTopic implements Topic {

    getDestination(): string {
        return '/user/messages/function';
    }
}
