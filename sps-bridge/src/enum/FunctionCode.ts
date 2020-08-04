
export enum FunctionCode {
    STATUS_CHECK = 1,
    BLOCK_LOT = 2,
    UNBLOCK_LOT = 3

}

export function stringValueOfCode(code: FunctionCode): string {
    if (code == FunctionCode.STATUS_CHECK) {
        return 'status';
    }
    if (code == FunctionCode.BLOCK_LOT) {
        return  'blockLot';
    }
    if (code == FunctionCode.UNBLOCK_LOT) {
        return 'unblockLot';
    }
}


