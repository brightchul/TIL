package programmers.compression3;

import java.util.*;

public class Solution2 {
    static final private String[] initArr = "0ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    private Map<String, Integer> map = new HashMap<>();
    Stack<String> stack = new Stack<>();

    public int[] solution(String msg) {
        init(msg);
        List<Integer> ret = new ArrayList<>();
        while(!stack.isEmpty()) {
            int idx = findLongestWordIdx();
            ret.add(idx);
        }
        return convertListToArr(ret);
    }
    public int findLongestWordIdx() {
        String one = "";
        int idx = -1;
        while(!stack.isEmpty()) {
            if(!map.containsKey(one + stack.peek())) break;
            idx = map.get((one += stack.pop()));
        }
        if(!stack.isEmpty())
            map.put(one + stack.peek(), map.size());
        return idx;
    }
    public void init(String msg) {
        initMap();
        initMsgStack(msg);
    }
    public void initMsgStack(String msg) {
        stack = new Stack<>();
        for(int i = msg.length()-1; i > -1; i--) {
            stack.push(msg.charAt(i) + "");
        }
    }
    public void initMap() {
        map.clear();
        for(int i=0; i<initArr.length; i++)
            map.put(initArr[i], i);
    }
    public int[] convertListToArr(List<Integer> list) {
        int[] ret = new int[list.size()];
        for(int i=0; i<ret.length; i++) ret[i] = list.get(i);
        return ret;
    }
}
