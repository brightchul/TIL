import { isInterleavingDP, isInterleavingRecursive } from "../interleaving";

test("isInterleavingRecursive", () => {
  expect(isInterleavingRecursive("bcc", "bbca", "bbcbcac")).toBeTruthy();
  expect(isInterleavingRecursive("bca", "bbca", "bbcbcac")).toBeFalsy();
});

test("isInterleavingDP", () => {
  expect(isInterleavingDP("bcc", "bbca", "bbcbcac")).toBeTruthy();
});
