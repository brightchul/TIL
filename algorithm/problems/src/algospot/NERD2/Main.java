package algospot.NERD2;

import java.io.*;
import java.util.*;

/*
2
4
72 50
57 67
74 55
64 60
5
1 5
2 4
3 3
4 2
5 1

 */

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    TreeMap<Integer, Integer> coords = new TreeMap<>();

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        Main m = new Main();

        int totalCaseNum = int_(br.readLine());
        while(totalCaseNum-- > 0) {
            int pointCount = int_(br.readLine());
            int resultCount = 0;

            while(pointCount-- > 0) {
                st = new StringTokenizer(br.readLine(), " ");
                resultCount += m.registed(int_(st.nextToken()), int_(st.nextToken()));
            }
            sb.append(resultCount).append("\n");
            m.clear();
        }
        System.out.println(sb.toString());
    }
    public boolean isContained(int x, int y) {
        Map.Entry<Integer, Integer> v = coords.higherEntry(x);
        if(v == null) return false;
        return y < v.getValue();
    }
    public void removeContained(int x, int y) {
        Map.Entry<Integer, Integer> one;
        while((one = coords.lowerEntry(x)) != null) {
            if(one.getValue() > y) break;
            coords.remove(one.getKey());
        }
    }
    public int registed(int x, int y) {
        if(isContained(x, y)) return  size();
        removeContained(x, y);
        coords.put(x, y);
        return size();
    }
    public int size() {
        return coords.size();
    }
    public void clear() {
        coords.clear();
    }
    public static int int_(String str) {
        return Integer.parseInt(str);
    }
}
