import { makeArray } from "./util";

export function lcsLengthRecursive(
  x: string,
  y: string,
  m: number,
  n: number
): number {
  // 종료 조건은 두 문자열 중 하낙 ㅏ빈 문자열일 떄이며 이 때의 LCS_LENGTH = 0
  if (m === 0 || n === 0) return 0;

  // 문자열의 마지막 글자를 비교해 조건에 따라 재귀 호출한다.
  if (x[m - 1] === y[n - 1]) return 1 + lcsLengthRecursive(x, y, m - 1, n - 1);
  else {
    return Math.max(
      lcsLengthRecursive(x, y, m, n - 1),
      lcsLengthRecursive(x, y, m - 1, n)
    );
  }
}

export function lcsLengthMemo(x: string, y: string): number {
  const m = x.length;
  const n = y.length;
  const LCSLTable = makeArray<number>(-1, m + 1, n + 1);

  function _lcsLengthMemo(m: number, n: number): number {
    // 종료 조건은 두 문자열 중 하나가 빈 문자열일 때이며 이 때의 LCS_LENGTH = 0
    if (m == 0 || n == 0) return 0;

    // 이미 캐시에 계산된 값이 있다면 캐시의 값을 반환한다. (!== -1)
    if (LCSLTable[m][n] !== -1) return LCSLTable[m][n];

    // 문자열의 마지막 글자를 비교에 조건에 따라 재귀 호출한다.
    if (x[m - 1] === y[n - 1])
      LCSLTable[m][n] = 1 + _lcsLengthMemo(m - 1, n - 1);
    else
      LCSLTable[m][n] = Math.max(
        _lcsLengthMemo(m, n - 1),
        _lcsLengthMemo(m - 1, n)
      );
    return LCSLTable[m][n];
  }

  return _lcsLengthMemo(m, n);
}

export function lcsLengthDP(x: string, y: string) {
  const lcsArr = makeLcsArr(x, y);
  return lcsArr[x.length][y.length];
}

function makeLcsArr(x: string, y: string): number[][] {
  const m = x.length;
  const n = y.length;

  const cache = makeArray<number>(0, m + 1, n + 1);

  for (let cacheXIdx = 1; cacheXIdx <= m; cacheXIdx++) {
    const xOne = x[cacheXIdx - 1];

    for (let cacheYIdx = 1; cacheYIdx <= n; cacheYIdx++) {
      const yOne = y[cacheYIdx - 1];

      if (xOne === yOne) {
        cache[cacheXIdx][cacheYIdx] = cache[cacheXIdx - 1][cacheYIdx - 1] + 1;
      } else {
        cache[cacheXIdx][cacheYIdx] = Math.max(
          cache[cacheXIdx - 1][cacheYIdx],
          cache[cacheXIdx][cacheYIdx - 1]
        );
      }
    }
  }
  return cache as number[][];
}

export function lcsTextDP(x: string, y: string): string {
  const lcsArr = makeLcsArr(x, y);
  let rowIdx = x.length;
  let colIdx = y.length;

  let result = "";
  while (rowIdx > 0 && colIdx > 0) {
    const curValue = lcsArr[rowIdx][colIdx];
    if (lcsArr[rowIdx - 1][colIdx] === curValue) {
      rowIdx--;
      continue;
    }
    if (lcsArr[rowIdx][colIdx - 1] === curValue) {
      colIdx--;
      continue;
    }
    result = x[rowIdx - 1] + result;
    rowIdx--;
    colIdx--;
  }
  return result;
}

export function lcsTextDPBook(x: string, y: string): string {
  const lcsArr = makeLcsArr(x, y);
  let rowIdx = x.length;
  let colIdx = y.length;

  let result = "";

  while (rowIdx > 0 && colIdx > 0) {
    if (x[rowIdx - 1] === y[colIdx - 1]) {
      result = x[rowIdx - 1] + result;
      rowIdx--;
      colIdx--;
    } else {
      if (lcsArr[rowIdx - 1][colIdx] > lcsArr[rowIdx][colIdx - 1]) {
        rowIdx--;
      } else {
        colIdx--;
      }
    }
  }
  return result;
}
