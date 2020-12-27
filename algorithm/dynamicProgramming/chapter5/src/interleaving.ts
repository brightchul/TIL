import { makeArray } from "./util";

export function isInterleavingRecursive(
  strA: string,
  strB: string,
  strC: string
): boolean {
  // 만약 모든 문자열이 빈 문자열인 경우
  if (!strA && !strB && !strC) return true;
  // strA, strB 문자열의 길이의 합이 C 문자열의 길이와 다를때)
  if (strA.length + strB.length !== strC.length) return false;

  function calculate(idxA: number, idxB: number, idxC: number) {
    // 만약 모든 문자열이 빈 문자열인 경우
    if (!strA[idxA] && !strB[idxB] && !strC[idxC]) return true;

    let caseA = false;
    let caseB = false;

    // strA첫글자와 strC의 첫 글자가 같은 경우
    if (strA[idxA] === strC[idxC]) caseA = calculate(idxA + 1, idxB, idxC + 1);

    // strA첫글자와 strC의 첫 글자가 같은 경우
    if (strB[idxB] === strC[idxC]) caseB = calculate(idxA, idxB + 1, idxC + 1);

    // 둘 중 하나라도 참이면 인터리빙
    return caseA || caseB;
  }
  return calculate(0, 0, 0);
}

export function isInterleavingDP(
  strA: string,
  strB: string,
  strC: string
): boolean {
  const M = strA.length;
  const N = strB.length;
  const lengthC = strC.length;

  //A와 B 문자열의 길이의 합이 C문자열의 길이와 다를 때
  if (lengthC !== M + N) return false;

  // 인터리빙 여부를 저장하는 2차원 배열
  const ilMatrix = makeArray<boolean | undefined>(undefined, M + 1, N + 1);
  ilMatrix[0][0] = true;

  // 첫번째 열을 채운다.
  for (let i = 1; i <= M; i++) {
    if (strA[i - 1] !== strC[i - 1]) {
      ilMatrix[i][0] = false;
    } else {
      ilMatrix[i][0] = ilMatrix[i - 1][0];
    }
  }

  // 첫번째 행을 채운다.
  for (let j = 1; j <= N; j++) {
    if (strB[j - 1] !== strC[j - 1]) {
      ilMatrix[0][j] = false;
    } else {
      ilMatrix[0][j] = ilMatrix[0][j - 1];
    }
  }

  // 나머지 셀을 채운다.
  for (let i = 1; i <= M; i++) {
    for (let j = 1; j <= N; j++) {
      // 현재 셀의 A, B, C의 글자
      let currentA = strA[i - 1];
      let currentB = strB[j - 1];
      let currentC = strC[i + j - 1];

      // C의 글자가 A의 글자와 같고 B의 글자와 다를때
      if (currentA === currentC && currentB !== currentC) {
        ilMatrix[i][j] = ilMatrix[i - 1][j];
      }

      // C의 글자가 B의 글자와 같고 A의 글자와 다를때
      else if (currentA !== currentC && currentB === currentC) {
        ilMatrix[i][j] = ilMatrix[i][j - 1];
      }

      // A, B, C 글자 모두가 같을 때
      else if (currentA === currentC && currentB === currentC) {
        ilMatrix[i][j] = ilMatrix[i - 1][j] || ilMatrix[i][j - 1];
      }

      // C의 글자가 A, B 두 글자 어느 쪽과도 다를 때
      else {
        ilMatrix[i][j] = false;
      }
    }
  }
  return ilMatrix[M][N];
}
