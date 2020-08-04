import {Message} from "../Message";

export abstract class MessageBuilder {
    
    protected build(message: Message, jsonString: string): Message {
        message.id = jsonString['id'];
        message.address = jsonString['address'];
        message.messageType = jsonString['messageType'];
        return message;
    }
}
