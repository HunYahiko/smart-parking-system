
export interface Connection<T> {

    connect(onConnectedCallback, onDisconnectedCallback);
    disconnect();
    onConnected(onConnectedCallback);
    onDisconnected(onDisconnectedCallback);
    getConnection(): T;
}
