// 프로그래머스 문제
// https://programmers.co.kr/learn/courses/30/lessons/1832#
class Solution {
    MOD: number = 20170805 as const;
    cache: number[][][] | undefined;

    public solution(
        rowLen: number,
        colLen: number,
        cityMap: number[][]
    ) {
        this.cache = makeArray3D(0, rowLen + 1, colLen + 1, 2);

        for (let row = 1; row <= rowLen; row++) {
            for (let col = 1; col <= colLen; col++) {
                if (cityMap[row - 1][col - 1] == 1) continue;

                if (col - 2 >= 0) {
                    const flag =
                        cityMap[row - 1][col - 2] == 2 ? 1 : 2;
                    this.cache[row][col][1] = this.getCache(
                        row,
                        col - 1,
                        flag
                    );
                }

                if (row - 2 >= 0) {
                    const flag =
                        cityMap[row - 2][col - 1] == 2 ? 0 : 2;
                    this.cache[row][col][0] = this.getCache(
                        row - 1,
                        col,
                        flag
                    );
                }
            }
        }
        return this.getCache(rowLen, colLen, 2);
    }
    public getCache(row: number, col: number, flag: number): number {
        if (!this.cache)
            throw new Error("cache가 초기화되어 있지 않습니다!");
        if (row == 1 && col == 1) return 1;
        if (flag < 2) return this.cache[row][col][flag];
        return (
            (this.cache[row][col][0] + this.cache[row][col][1]) %
            this.MOD
        );
    }
}

type ArrOrNum = Array<ArrOrNum> | number;

function makeArray3D(
    initValue: number,
    ...args: number[]
): number[][][] {
    return makeArray(initValue, ...args) as number[][][];
}

function makeArray(initValue: number, ...args: number[]): ArrOrNum {
    if (args.length === 0) return initValue;
    return Array(args[0])
        .fill(0)
        .map((_) => makeArray(initValue, ...args.slice(1)));
}

const s = new Solution();
console.log(
    s.solution(3, 6, [
        [0, 2, 0, 0, 0, 2],
        [0, 0, 2, 0, 1, 0],
        [1, 0, 0, 2, 2, 0],
    ])
);
