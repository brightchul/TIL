import {
  numOfPathsRecursive,
  numOfPathsDP,
  numOfPathsDP2,
} from "../rectangleTotalPath";

test("numOfPathsRecursive test", () => {
  expect(numOfPathsRecursive(3, 4)).toBe(10);
});
test("numOfPathsDP test", () => {
  expect(numOfPathsDP(3, 4)).toBe(10);
});
test("numOfPathsDP2 test", () => {
  expect(numOfPathsDP2(3, 4)).toBe(10);
});
