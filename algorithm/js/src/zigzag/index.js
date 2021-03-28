function solution(n, row, col) {
  const sum = row + col;
  const startValue = getStartNumArr(n)[sum];

  let amount = sum % 2 > 0 ? row : col;
  let minus = sum <= n + 1 ? 1 : sum - n;

  return startValue + amount - minus;
}

function getStartNumArr(n) {
  const result = [0, 0, 1];
  if (n < 2) return result;

  let i = 3,
    amount = 1;

  for (; amount < n; i++) {
    result[i] = result[i - 1] + amount++;
  }

  result[i] = result[i - 1] + amount;

  for (; amount > 1; i++) {
    result[i] = result[i - 1] + amount--;
  }
  return result;
}

export { solution, getStartNumArr };
