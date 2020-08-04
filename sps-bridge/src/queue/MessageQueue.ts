import Queue from 'better-queue';
import {Message} from "../message/Message";
import {injectable} from "inversify";
import {MessageHandler} from "../handler/MessageHandler";
import {Handler} from "../handler/Handler";

@injectable()
export abstract class MessageQueue<T extends Message, U> {
    private queue: Queue<T>;
    protected messageHandler: Handler<U, T>;

    protected constructor(messageHandler: Handler<U, T>) {
        this.messageHandler = messageHandler;
    }

    public enqueueMessage(message: T) {
        this.queue.push(message);
    }

    abstract handleMessage(input: Message, cb): void;

    public startQueueProcessing(): void {
        this.queue = new Queue<Message>((input, cb) => this.handleMessage(input, cb));
        this.queue.on('task_finish', (taskId, result) => {console.log(result)});
    }
}

