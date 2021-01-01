import { editDistanceDP, editDistanceRecursive } from "../minimumCost";

test("editDistanceRecursive", () => {
  expect(editDistanceRecursive("sunday", "saturday")).toBe(3);
});

test("editDistanceDP", () => {
  expect(editDistanceDP("sunday", "saturday")).toBe(3);
});
