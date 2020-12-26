import { getMinimum, makeArray } from "./util";

// 책 원문은 포인터로 연산하지만 js에서는 그러지 않으니 인덱스값을 받아서 한다.
// slice를 써도 되겠지만 그러면 문자열 복사가 계속해서 일어나지 효율적이지 못하다.

export function editDistanceRecursive(str1: string, str2: string) {
  function calculate(idx1: number, idx2: number): number {
    // str1이 빈 문자열이면 str2의 모든 글자를 삽입하면 된다.
    if (str1.length <= idx1) return str2.length - idx2;

    // str2이 빈 문자열이면 str1의 모든 글자를 제거하면 된다.
    if (str2.length <= idx2) return str1.length - idx2;

    // 첫번째 글자가 같을 때는 첫 번째 글자를 무시하고 나머지 단어 간의 최소 교정 비용ㅇ르 구한다.
    if (str1[idx1] === str2[idx2]) return calculate(idx1 + 1, idx2 + 1);

    // 삭제 연산
    const remove: number = calculate(idx1 + 1, idx2);

    // 치환 연산
    const update: number = calculate(idx1 + 1, idx2 + 1);

    // 삽입 연산
    const insert: number = calculate(idx1, idx2 + 1);

    // 세 연산 이후 최소 교정 비용 간의 최소값에 1을 더해서 반환한다.
    return getMinimum(remove, update, insert) + 1;
  }
  return calculate(0, 0);
}

export function editDistanceDP(
  str1: string,
  str2: string,
  m: number = str1.length,
  n: number = str2.length
) {
  const EditD = makeArray(0, m + 1, n + 1);

  for (let j = 0; j <= n; j++) EditD[0][j] = j;

  for (let i = 0; i <= m; i++) EditD[i][0] = i;

  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      // 두 글자가 같다면
      if (str1[i - 1] == str2[j - 1]) EditD[i][j] = EditD[i - 1][j - 1];
      // 두 글자가 다르다면
      else
        EditD[i][j] =
          getMinimum(EditD[i - 1][j - 1], EditD[i][j - 1], EditD[i - 1][j]) + 1;
    }
  }
  return EditD[m][n];
}
