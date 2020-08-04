
export interface Handler<T, R> {

    handle(message: T): Promise<R>;
}
