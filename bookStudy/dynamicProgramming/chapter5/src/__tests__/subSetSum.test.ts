import {
  isSubsetSumRecursive,
  isSubsetSumDP,
  isSubsetSumDP2,
} from "../subSetSum";

let arr1 = [1, 2, 3, 4, 5];
let arr2 = [3, 2, 7, 1];

test("isSubsetSumRecursive test", () => {
  expect(isSubsetSumRecursive(arr1, arr1.length, 6)).toBeTruthy();
  expect(isSubsetSumRecursive(arr2, arr2.length, 6)).toBeTruthy();
});

test("isSubsetSumDP test", () => {
  expect(isSubsetSumDP(arr1, arr1.length, 6)).toBeTruthy();
  expect(isSubsetSumDP(arr2, arr2.length, 6)).toBeTruthy();
});

test("isSubsetSumDP2 test", () => {
  expect(isSubsetSumDP2(arr1, 6)).toBeTruthy();
  expect(isSubsetSumDP2(arr2, 6)).toBeTruthy();
});
