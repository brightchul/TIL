package programmers.lockAndKey;

public class Solution {
    public static void main(String[] args) {
        int[][] key = {{0, 0, 0}, {1, 0, 0}, {0, 1, 1}};
        int[][] lock = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        Solution s = new Solution();
        System.out.println(s.solution(key, lock));
//
//        int[][] key2 = {{0,0,0},{0,0,0},{0,0,0}};
//        int[][] lock2 = {{1,1,1},{1,1,1},{1,1,1}};
//        System.out.println(s.solution(key2,lock2));
    }
    public boolean solution(int[][] key, int[][] lock) {
        Point[] lockArr = getPointArr(lock, 0);
        if(lockArr.length == 0) return true;

        Point maxLockPoint = getMaxPoint(lockArr);
        int maxLockPointX = maxLockPoint.x;
        int maxLockPointY = maxLockPoint.y;

        int[][] key2 = key;
        for(int i=0; i<4; i++) {

            Point[] keyArr = getPointArr(key2, 1);
            Point maxKeyPoint = getMaxPoint(keyArr);

            for(int dy = -maxKeyPoint.y; dy <= maxLockPointY; dy++) {
                loop : for(int dx = -maxKeyPoint.x; dx <= maxLockPointX; dx++) {
                    int count = 0;
                    for(Point oneKey : keyArr) {
                        int keyX = oneKey.x + dx;
                        int keyY = oneKey.y + dy;
                        boolean isChecked = false;
                        for(Point oneLock : lockArr) {
                            if(oneLock.x == keyX && oneLock.y == keyY ) {
                                count++;
                                isChecked = true;
                                break;
                            }
                            // 일치하지 않더라도 다른 lock좌표와 일치할 수 있으니 여기서
                            // 일치하지 않지만 lock 범위안에 있는지 체크하면 안된다.
                        }
                        if(count == lockArr.length) return true;
                        if(!isChecked) {
                            if(0 <= keyX && keyX <= maxLockPointX && 0 <= keyY && keyY <= maxLockPointY) {
                                continue loop;
                            }
                        }
                    }
                }
            }
            key2 = rotate(key2);
        }
        return false;
    }
    public Point[] getPointArr(int[][] arr, int value) {
        int idx = 0, len = 0;
        for(int y = 0; y<arr.length; y++) {
            for(int x=0; x<arr.length; x++) {
                if(arr[y][x] == value) len++;
            }
        }
        Point[] ret = new Point[len];
        for(int y = 0; y<arr.length; y++) {
            for(int x=0; x<arr.length; x++) {
                if(arr[y][x] == value) ret[idx++] = new Point(x,y);
            }
        }
        return ret;
    }
    public Point getMaxPoint(Point[] arr) {
        Point p = new Point(0, 0);
        for(Point one : arr) {
            p.x = Math.max(p.x, one.x);
            p.y = Math.max(p.y, one.y);
        }
        return p;
    }
    public int[][] rotate(int[][] arr) {
        int len = arr.length;
        int[][] ret = new int[len][len];
        int maxRow = len-1;
        for(int y=0; y<len; y++) {
            for(int x=0; x<len; x++) {
                ret[x][maxRow-y] = arr[y][x];
            }
        }
        return ret;
    }
}

class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // 음수값까지 같이 사용하므로 minus까지 커버한다.
    void plus(int x, int y) {
        this.x+=x;
        this.y+=y;
    }
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }
}