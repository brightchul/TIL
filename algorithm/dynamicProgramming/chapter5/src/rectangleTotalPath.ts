import { makeArray } from "./util";

export function numOfPathsRecursive(m: number, n: number): number {
  // 예제 코드에서 0~m, 0~n까지의 코드로 되어 있는데 이렇게 될 경우 +1만큼 row, col이 추가된다.
  // 따라서 m-1, n-1씩을 줄여서 하던가 아니면 종료조건에서 0이 아닌 1로 해야 한다.

  // 종료 조건
  if (m === 1 && n === 1) return 0;
  if (m === 1 || n === 1) return 1;
  // 재귀 호출
  return numOfPathsRecursive(m - 1, n) + numOfPathsRecursive(m, n - 1);
}

export function numOfPathsDP(m: number, n: number): number {
  const cache = makeArray<number>(0, m, n);

  for (let row = 1; row < m; row++) cache[row][0] = 1;
  for (let col = 1; col < n; col++) cache[0][col] = 1;

  for (let row = 1; row < m; row++) {
    for (let col = 1; col < n; col++) {
      cache[row][col] = cache[row - 1][col] + cache[row][col - 1];
    }
  }
  return cache[m - 1][n - 1];
}

export function numOfPathsDP2(m: number, n: number): number {
  const cache = makeArray<number>(0, m + 1, n + 1);

  cache[0][2] = cache[2][0] = 1;
  for (let row = 1; row <= m; row++) {
    for (let col = 1; col <= n; col++) {
      cache[row][col] = cache[row - 1][col] + cache[row][col - 1];
    }
  }
  return cache[m][n];
}
