package baekjoon.p2672;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine());
        double[] xArr = new double[len << 1];
        double[] yArr = new double[len << 1];
        Rect[] rectArr = new Rect[len];

        int idx = 0;
        StringTokenizer st;
        while(len-- > 0) {
            st = new StringTokenizer(br.readLine(), " ");
            Rect rect = new Rect(
                    Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()),
                    Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())
            );

            rectArr[idx/2] = rect;

            xArr[idx] = rect.x1;
            yArr[idx++] = rect.y1;

            xArr[idx] = rect.x2;
            yArr[idx++] = rect.y2;
        }

        Arrays.sort(xArr);
        Arrays.sort(yArr);

        double area = 0.0;
        for(int x=0; x<xArr.length-1; x++) {
            YLoop : for(int y=0; y<yArr.length-1; y++) {
                for(Rect r : rectArr) {
                    if(r.isContained(xArr[x], yArr[y], xArr[x+1], yArr[y+1])) {
                        area += (xArr[x+1] - xArr[x]) * (yArr[y+1] - yArr[y]);
                        continue YLoop;
                    }
                }
            }
        }

        if(area % 1 == 0)
            System.out.printf("%d", (int)area);
        else
            System.out.printf("%.2f",area);
    }
}

class Rect {
    double x1, x2, y1, y2;
    Rect(double x1, double y1, double dx, double dy) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x1 + dx;
        this.y2 = y1 + dy;
    }
    boolean isContained(double x1, double y1, double x2, double y2) {
        return this.x1 <= x1 && this.y1 <= y1 && x2 <= this.x2 && y2 <= this.y2;
    }
}