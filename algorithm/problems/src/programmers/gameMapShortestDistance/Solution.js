function solution(maps) {
    const [rowLen, colLen] = [maps.length, maps[0].length];
    const INF = 100_000_003;
    const queue = [new Pair(0, 0, 1)];
    const dirs = [
        [-1, 0],
        [1, 0],
        [0, -1],
        [0, 1],
    ];

    let dist = array(rowLen, colLen, INF);
    let visited = array(rowLen, colLen, false);

    while (queue.length > 0) {
        const { row, col, count } = queue.shift();

        if (visited[row][col]) continue;
        visited[row][col] = true;

        for (const [moveRow, moveCol] of dirs) {
            const [newRow, newCol, newCount] = [
                row + moveRow,
                col + moveCol,
                count + 1,
            ];

            if (isOut(newRow, newCol, maps)) continue;

            if (dist[newRow][newCol] > newCount) {
                dist[newRow][newCol] = newCount;
                queue.push(new Pair(newRow, newCol, newCount));
            }
        }
    }

    if (dist[rowLen - 1][colLen - 1] === INF) return -1;
    else return dist[rowLen - 1][colLen - 1];
}

function array(row, col, initValue) {
    return Array(row)
        .fill(0)
        .map(() => Array(col).fill(initValue));
}

function isOut(row, col, maps) {
    if (row < 0 || col < 0) return true;
    if (row >= maps.length || col >= maps[0].length) return true;
    if (maps[row][col] === 0) return true;

    return false;
}

class Pair {
    constructor(row, col, count) {
        this.row = row;
        this.col = col;
        this.count = count;
    }
}
