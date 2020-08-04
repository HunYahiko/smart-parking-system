
export interface Parser<M, T> {
    parse(object: T) : M
}
