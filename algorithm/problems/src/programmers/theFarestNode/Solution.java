package programmers.theFarestNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Solution {
    public int solution(int n, int[][] edge) {
        MyGraph graph = new MyGraph(n);
        for(int[] one : edge) {
            graph.addEdge(one[0], one[1]);
        }
        return graph.findDeepestCount();
    }
}

class MyGraph {
    ArrayList<Integer>[] data;
    HashSet<Integer> set = new HashSet<>();
    int[] passed;
    public MyGraph(int n) {
        passed = new int[n+1];
        data = new ArrayList[n+1];
        for(int i=0; i<=n; i++)
            data[i] = new ArrayList<>();
    }
    public void addEdge(int a, int b) {
        data[a].add(b);
        data[b].add(a);
    }
    public int findDeepestCount() {
        set.clear();
        Arrays.fill(passed, 0);
        set.add(1);
        bfs(0);
        return set.size();
    }
    public void bfs(int depth) {
        HashSet<Integer> newSet = new HashSet<>();

        for(int one:set) {
            passed[one]++;
        }
        for(int one : set) {
            for(int other: data[one]) {
                if(passed[other] > 0) continue;
                newSet.add(other);
            }
        }
        if(newSet.size() > 0) {
            set = newSet;
            bfs(depth+1);
        }
    }
}