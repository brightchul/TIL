import { lcsLengthRecursive, lcsLengthMemo, lcsLengthDP } from "../lcsLength";

const str11 = "ABCDE";
const str12 = "AEBDF";

test("lcsLengthRecursive", () => {
  expect(
    lcsLengthRecursive(str11, str12, str11.length - 1, str12.length - 1)
  ).toBe(3);
});

test("lcsLengthMemo", () => {
  expect(lcsLengthMemo(str11, str12)).toBe(3);
});

test("lcsLengthDP", () => {
  expect(lcsLengthDP(str11, str12)).toBe(3);
});
