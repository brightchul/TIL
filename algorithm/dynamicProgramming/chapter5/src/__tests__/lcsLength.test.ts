import {
  lcsLengthRecursive,
  lcsLengthMemo,
  lcsLengthDP,
  lcsTextDP,
  lcsTextDPBook,
} from "../lcsLength";

const str11 = "ABCDE";
const str12 = "AEBDF";

const str21 = `AAACCGTGAGTTATTCGTTCTAGAA`;
const str22 = `CACCCCTAAGGTACCTTTGGTTC`;

test("lcsLengthRecursive", () => {
  expect(lcsLengthRecursive(str11, str12, str11.length, str12.length)).toBe(3);
  // 테스트 케이스가 길어서 재귀호출로는 감당이 안됨
  // expect(lcsLengthRecursive(str21, str22, str21.length, str22.length)).toBe(14); // 성능 떨어져서 못씀
});

test("lcsLengthMemo", () => {
  expect(lcsLengthMemo(str11, str12)).toBe(3);
  expect(lcsLengthMemo(str21, str22)).toBe(14);
});

test("lcsLengthDP", () => {
  expect(lcsLengthDP(str11, str12)).toBe(3);
  expect(lcsLengthDP(str21, str22)).toBe(14);
});

test("lcsTextDP", () => {
  expect(lcsTextDP(str11, str12)).toBe("ABD");
  expect(lcsTextDP(str21, str22)).toBe(`ACCTAGTATTGTTC`);
});

test("lcsTextDPBook", () => {
  expect(lcsTextDPBook(str11, str12)).toBe("ABD");
  expect(lcsTextDPBook(str21, str22)).toBe(`ACCTAGTATTGTTC`);
});
