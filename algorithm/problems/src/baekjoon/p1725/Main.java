package baekjoon.p1725;
// 시간 초과..
//https://www.acmicpc.net/blog/vie  w/12
//https://yaaaajk.tistory.com/9
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine());
        int[] arr = new int[len];
        for(int i=0; i<len; i++)
            arr[i] = Integer.parseInt(br.readLine());

        System.out.println(solution(0, len-1, arr));
        System.out.println(solution2(arr));
    }
    public static int solution(int left, int right, int[] arr) {
        // 너비 1짜리일 때 리턴
        if(left == right) return arr[left];

        //
        int mid = (left + right) / 2;
        int ans = Math.max(solution(left, mid, arr),
                           solution(mid+1, right, arr));

        int low = mid;
        int high = mid+1;
        int height = Math.min(arr[low], arr[high]);
        ans = Math.max(ans, height * 2);

        while(left < low || high < right) {
            // high < right : 아직 오른편으로 갈 공간이 있음
            // low == left : 왼쪽으론 더이상 갈수 없음
            // mid는 저 위에서 한번 쓰이고 안쓰임
            // 그 이후에는 low, high만 가지고 다른 영역들을 체크 하고 크기 비교를 함
            // 아래 조건은 오른편으로 계속 가는 조건
            if(high < right && ((low == left) || arr[low-1]<arr[high+1])) {
                high++;
                height = Math.min(height, arr[high]);
            } else { // 이조건은 왼편으로 확장해가는 조건
                low--;
                height = Math.min(height, arr[low]);
            }
            ans = Math.max(ans, height * (high - low +1));
        }
        return ans;

    }

    public static int solution2(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> tempMap = new HashMap<>();
        for(int one : arr) {
            if(map.containsKey(one)) continue;
            map.put(one, 0);
            tempMap.put(one, 0);
        }

        for(Integer key : map.keySet()) {
            if(arr[0] >= key) {
                map.put(key, 1);
            }
        }
        for(int i=1; i<arr.length; i++) {
            for(Integer key : map.keySet()) {
                if(arr[i] >= key) {
                    if(arr[i-1]>= key) {
                        tempMap.put(key, tempMap.get(key)+1);
                    } else {
                        map.put(key, Math.max(map.get(key), tempMap.get(key)));
                        tempMap.put(key, 1);
                    }
                }
            }

        }
        int result = 0;

        for(Integer key : map.keySet()) {
            map.put(key, Math.max(map.get(key), tempMap.get(key)));
            result = Math.max(result, key * map.get(key));
        }

        return result;
    }
}
