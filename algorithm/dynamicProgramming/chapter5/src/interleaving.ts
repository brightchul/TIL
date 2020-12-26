export function isInterleavingRecursive(
  strA: string,
  strB: string,
  strC: string
) {
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
}
