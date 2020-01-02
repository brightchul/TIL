package programmers.wordConversation;
/*
코딩테스트 연습 > 깊이/너비 우선 탐색(DFS/BFS) > 단어 변환
 */

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution("hit","cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}));
        System.out.println(s.solution("hit","cog", new String[]{"hot", "dot", "dog", "lot", "log"}));
        System.out.println(s.solution("hit","hhh", new String[]{"hhh", "hht"}));
        System.out.println(s.solution("aaaaa","bbbbb", new String[]{"aaaac", "aaaab", "aaabb","akbbb", "aabbb", "abbbb","bbbbb"}));
    }
    public int solution(String begin, String target, String[] words) {
        if(!isContain(words, target)) return 0;
        MyGraph myGraph = new MyGraph();
        insertWordsToGraph(myGraph, begin, words);
        return myGraph.dfs(begin, target);
    }
    public void insertWordsToGraph(MyGraph graph, String one, String[] arr) {
        int limit = arr.length;
        for(int i=0; i<limit; i++) {
            if(checkWords(one, arr[i])) graph.addEdge(one, arr[i]);
            for(int j=i+1; j<limit; j++) {
                if(checkWords(arr[i], arr[j])) graph.addEdge(arr[i], arr[j]);
            }
        }
    }
    public boolean isContain(String[] arr, String target) {
        for(int i=0; i<arr.length; i++) {
            if(arr[i].equals(target)) return true;
        }
        return false;
    }

    public boolean checkWords(String one, String target) {
        int count = 0, len = one.length();
        for(int i=0; i<len; i++) {
            if(one.charAt(i) != target.charAt(i)) count++;
            if(count > 1) return false;
        }
        return true;
    }
}

class MyGraph {
    final static int MAX_VALUE = Integer.MAX_VALUE;
    private HashMap<String, ArrayList<String>> vMap = new HashMap<>();
    private LinkedList<String> passedList = new LinkedList<>();
    public void addEdge(String exOne, String newOne) {
        if(!vMap.containsKey(exOne)) vMap.put(exOne, new ArrayList<>());
        if(!vMap.containsKey(newOne)) vMap.put(newOne, new ArrayList<>());
        vMap.get(exOne).add(newOne);
        vMap.get(newOne).add(exOne);
    }
    public int dfs(String start, String end) {
        if(start.equals(end)) return 0;
        int ret = _dfs(start, end, 0);
        return ret == MAX_VALUE ? 0 : ret;
    }
    private int _dfs(String start, String end, int count) {
        if(start.equals(end)) return count;
        passedList.push(start);
        ArrayList<String> oneList = this.vMap.get(start);
        int ret = MAX_VALUE;
        for(int i=0; i<oneList.size(); i++) {
            String one = oneList.get(i);
            if(passedList.indexOf(one) > -1) continue;
            ret = Math.min(ret, _dfs(one, end, count+1));
        }
        passedList.pop();
        return ret;
    }
}