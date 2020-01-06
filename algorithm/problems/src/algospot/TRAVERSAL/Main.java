package algospot.TRAVERSAL;

import java.io.*;
import java.util.Arrays;
import java.util.List;
/*
https://algospot.com/judge/problem/read/TRAVERSAL
트리 순회 순서 변경

2
7
27 16 9 12 54 36 72
9 12 16 27 36 54 72
6
409 479 10 838 150 441
409 10 479 150 838 441
 */

public class Main {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    final static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException{
        int totalCaseNum = Integer.parseInt(br.readLine());
        for(int i=0; i<totalCaseNum; i++) {
            br.readLine();
            System.out.println(run(br.readLine(), br.readLine()));
        }
    }
    public static String run(String pStr, String iStr) {
        List<String> pList = Arrays.asList(pStr.split(" "));
        List<String> iList = Arrays.asList(iStr.split(" "));
        recursive(pList, iList);
        sb.deleteCharAt(sb.length()-1);
        String ret = sb.toString();
        sb.setLength(0);
        return ret;
    }
    public static void recursive(List<String> pList, List<String> iList) {
        int totalHeight = pList.size();
        if(totalHeight == 0) return;

        String root = pList.get(0);
        int rootIdx = iList.indexOf(root);
        int leftHeight = rootIdx;

        recursive(pList.subList(1, 1+leftHeight), iList.subList(0, leftHeight));
        recursive(pList.subList(rootIdx + 1, totalHeight), iList.subList(rootIdx + 1, totalHeight));
        sb.append(root).append(" ");
    }
}
