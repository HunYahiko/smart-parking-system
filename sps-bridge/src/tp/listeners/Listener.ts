
export interface Listener<M> {
    onMessage(message: M): void;
}
