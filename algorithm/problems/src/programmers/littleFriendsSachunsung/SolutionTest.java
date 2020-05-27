package programmers.littleFriendsSachunsung;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void solution() {
        Solution s= new Solution();
        {
            int m=3, n=3;
            String[] arr = {"DBA", "C*A", "CDB"};
            assertEquals("ABCD", s.solution(m, n, arr));
        }
        {
            int m=2, n=4;
            String[] arr = {"NRYN", "ARYA"};
            assertEquals("RYAN", s.solution(m, n, arr));
        }
        {
            int m=4, n=4;
            String[] arr = {".ZI.", "M.**", "MZU.", ".IU."};
            assertEquals("MUZI", s.solution(m, n, arr));
        }
        {
            int m=2, n=2;
            String[] arr = {"AB", "BA"};
            assertEquals("IMPOSSIBLE", s.solution(m, n, arr));
        }
        {
            int m=2, n=2;
            String[] arr = {"A.", ".A"};
            assertEquals("A", s.solution(m, n, arr));
        }
        {
            int m=2, n=4;
            String[] arr = {
                    "B..A",
                    "B..A"};
            assertEquals("AB", s.solution(m, n, arr));
        }
        {
            int m=2, n=4;
            String[] arr = {
                    "BA*C",
                    "B.CA"};
            assertEquals("IMPOSSIBLE", s.solution(m, n, arr));
        }
        {
            int m=2, n=5;
            String[] arr = {
                    "BA*C*",
                    "B.C.A"};
            assertEquals("BCA",
                    s.solution(m, n, arr));
        }
        {
            int m=1, n=10;
            String[] arr = {
                    "ABCDEEDCBA"};
            assertEquals("EDCBA", s.solution(m, n, arr));
        }
        {
            int m=2, n=4;
            String[] arr = {
                    "ADBA",
                    "CDBC"};
            assertEquals("BDAC",
                    s.solution(m, n, arr));
        }
        {
            int m=4, n=4;
            String[] arr = {
                    "A..*",
                    "B*.*",
                    "..B.",
                    "..A."
            };
            assertEquals("BA",
                    s.solution(m, n, arr));
        }
    }
}