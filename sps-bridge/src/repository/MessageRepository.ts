// import LinkedList from "typescript-collections/dist/lib/LinkedList";
// import {Message} from "../message/Message";
//
// export class MessageRepository {
//
//     private static instance: MessageRepository;
//     private messageList: LinkedList<Message>;
//
//     constructor(messageList: LinkedList<Message>) {
//         this.messageList = messageList;
//     }
//
//     public static getInstance(): MessageRepository {
//         if (!MessageRepository.instance) {
//             this.instance = new MessageRepository(new LinkedList<Message>());
//         }
//         return this.instance;
//     }
//
//     public getAll(): LinkedList<Message> {
//         return new LinkedList<Message>();
//     }
//
//     public getById(id: number): Message {
//         return this.messageList.toArray().filter(message => message.id === id)[0];
//     }
//
//     public delete(message: Message) {
//         this.messageList.remove(message);
//     }
//
//     public deleteById(id: number) {
//         const message: Message = this.getById(id);
//         this.delete(message);
//     }
//
//     public add(message: Message) {
//         this.messageList.add(message);
//     }
// }
