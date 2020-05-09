package programmers.travelPath;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        Solution s= new Solution();
        String[][] ar = new String[1][];
        s.solution(ar);
    }
    public String[] solution(String[][] tickets) {
        Stack<String> stack = new Stack<>();
        stack.add("aba");
        stack.add("aca");
        stack.add("aaa");
        System.out.println(stack);
        stack.sort((a,b)->a.hashCode()-b.hashCode());
        System.out.println(stack);
        return new String[]{};
    }
}
