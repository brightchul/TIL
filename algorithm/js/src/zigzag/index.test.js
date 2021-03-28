import { getStartNumArr, solution } from ".";
describe("zigzag", () => {
  describe("solution test", () => {
    test("n === 2", () => {
      expect(solution(2, 1, 1)).toEqual(1);
      expect(solution(2, 1, 2)).toEqual(2);
      expect(solution(2, 2, 1)).toEqual(3);
      expect(solution(2, 2, 2)).toEqual(4);
    });
    test("n === 5", () => {
      expect(solution(5, 1, 1)).toEqual(1);
      expect(solution(5, 2, 1)).toEqual(3);
      expect(solution(5, 3, 3)).toEqual(13);
      expect(solution(5, 4, 3)).toEqual(18);
      expect(solution(5, 4, 1)).toEqual(10);
      expect(solution(5, 1, 4)).toEqual(7);
      expect(solution(5, 1, 5)).toEqual(15);
      expect(solution(5, 5, 1)).toEqual(11);
      expect(solution(5, 5, 5)).toEqual(25);
    });
  });

  describe("getStartNumArr test", () => {
    test("n === 1", () => {
      expect(getStartNumArr(1)).toEqual([0, 0, 1]);
    });

    test("n > 1", () => {
      expect(getStartNumArr(2)).toEqual([0, 0, 1, 2, 4]);
      expect(getStartNumArr(3)).toEqual([0, 0, 1, 2, 4, 7, 9]);
      expect(getStartNumArr(4)).toEqual([0, 0, 1, 2, 4, 7, 11, 14, 16]);
    });
  });
});
