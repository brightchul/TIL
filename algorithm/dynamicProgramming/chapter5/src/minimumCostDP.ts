import { getMinimum } from "./util";

export function editDistance(
  str1: string,
  str2: string,
  m: number = str1.length,
  n: number = str2.length
) {
  const EditD = Array(m + 1)
    .fill(0)
    .map((o: number) => Array(n + 1).fill(0));

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
