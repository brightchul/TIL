package programmers.chuseokTraffic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

  public int solution(String[] lines) {

    Req[] reqArr = new Req[lines.length];
    int[] timeArr = new int[lines.length * 2];

    // 전처리
    settingArr(reqArr, timeArr, lines);

    // 순회하면서 확인
    int answer = 0;
    for (int one : timeArr) {

      // 확인할 시간 구간 정하기 (1초)
      int startTime = one - 999;
      int endTime = one;

      // 확인 시간 동안 적용되는 시간 카운트
      int count = 0;
      for (Req req : reqArr) {
        if (endTime < req.inMs || req.outMs < startTime) continue;
        count++;
      }
      answer = Math.max(answer, count);
    }

    return answer;
  }

  public void settingArr(Req[] reqArr, int[] timeArr, String[] lines) {
    Pattern pattern = Pattern.compile("(\\d{2}:\\d{2}:\\d{2}\\.\\d{3})|(\\d{1}\\.{0,1}\\d{0,3}s)");

    int idx = 0;
    for (String one : lines) {
      Matcher matcher = pattern.matcher(one);
      int outMs = convertTimeStringToMs(getNextMatchText(matcher));
      int duringTime = convertSecondStringToMS(getNextMatchText(matcher));

      Req req = new Req(outMs, duringTime);

      timeArr[idx * 2] = outMs;
      timeArr[idx * 2 + 1] = req.inMs;
      reqArr[idx++] = req;
    }
  }

  public int convertTimeStringToMs(String timeString) {
    return Integer.parseInt(timeString.substring(0, 2)) * 3600000
        + Integer.parseInt(timeString.substring(3, 5)) * 60000
        + (int) (Double.parseDouble(timeString.substring(6)) * 1000);
  }

  public int convertSecondStringToMS(String secondString) {
    String str = secondString.substring(0, secondString.length() - 1);
    int result = (int) (Double.parseDouble(str) * 1000);
    return result;
  }

  public String getNextMatchText(Matcher matcher) {
    matcher.find();
    return matcher.group();
  }
}

class Req {
  int inMs, outMs;

  Req(int outMs, int duringTime) {
    this.outMs = outMs;
    inMs = outMs - duringTime + 1;
  }
}
