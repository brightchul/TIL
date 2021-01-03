import { makeArray } from "./util";

export function isSubsetSumRecursive(
  arr: number[],
  n: number,
  x: number
): boolean {
  // 종료 조건 1 : x가 0이면 성공 종료 조건이다.
  if (x === 0) return true;

  // 종료 조건 2 : x가 0이 아니고 남은 원소가 없다면 실패 종료 조건이다.
  if (n === 0) return false;

  // x보다 큰 원소는 무시해도 된다.
  if (arr[0] > x) return isSubsetSumRecursive(arr.slice(1), n - 1, x);

  // 부분집합에 원소를 포함하지 않는 경우와
  // 원소를 포함하는 경우 각각에 대해 재귀 호출합니다.
  return (
    isSubsetSumRecursive(arr.slice(1), n - 1, x) || // 포함하지 않는 경우
    isSubsetSumRecursive(arr.slice(1), n - 1, x - arr[0]) // 포함하는 경우
  );
}

export function isSubsetSumDP(arr: number[], n: number, x: number) {
  // 합이 x인 부분 집합이 존재하는지의 결과를 저장해둘 2차원 배열
  const cache = makeArray<boolean>(false, n + 1, x + 1);

  // 맨처음 열 true로 초기화
  for (let i = 0; i <= n; i++) cache[i][0] = true;

  for (let row = 1; row <= n; row++) {
    const value = arr[row - 1];

    for (let col = 1; col <= x; col++) {
      if (col < value) {
        cache[row][col] = cache[row - 1][col];
      } else {
        if (cache[row - 1][col]) {
          cache[row][col] = cache[row - 1][col];
        } else {
          cache[row][col] = cache[row - 1][col - value];
        }
      }
    }
  }
  return cache[n][x];
}

// 좀더 단순화
export function isSubsetSumDP2(arr: number[], x: number) {
  // 합이 x인 부분 집합이 존재하는지의 결과를 저장해둘 2차원 배열
  const n = arr.length;
  const cache = makeArray<boolean>(false, n + 1, x + 1);

  // 맨처음 열 true로 초기화
  for (let i = 0; i <= n; i++) cache[i][0] = true;

  for (let row = 1; row <= n; row++) {
    const value = arr[row - 1];

    for (let col = 1; col <= x; col++) {
      if (col < value || cache[row - 1][col]) {
        cache[row][col] = cache[row - 1][col];
      } else {
        cache[row][col] = cache[row - 1][col - value];
      }
    }
  }
  return cache[n][x];
}
