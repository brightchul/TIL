package programmers.doubleyPriorityQueue;
// TODO: 정리 할것
import java.util.TreeSet;

public class Solution {
    public int[] solution(String[] operations) {
        TreeSet<Integer> tSet = new TreeSet<>();
        for(String msg : operations)
            applyMsg(tSet, msg);

        return tSet.size() == 0 ? new int[]{0, 0} : new int[]{tSet.last(), tSet.first()};
    }
    public void applyMsg(TreeSet<Integer> tSet, String one) {
        String[] arr = one.split(" ");
        switch(arr[0]) {
            case "I":
                tSet.add(Integer.parseInt(arr[1]));
                break;
            case "D":
                if(tSet.size() == 0) break;
                if (arr[1].equals("1")) tSet.remove(tSet.last());
                else tSet.remove(tSet.first());
                break;
        }
    }
}
