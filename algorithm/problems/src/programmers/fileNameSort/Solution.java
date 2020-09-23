package programmers.fileNameSort;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
  public String[] solution(String[] files) {
    int len = files.length;

    FileName[] arr = new FileName[len];
    for (int i = 0; i < len; i++) arr[i] = new FileName(files[i]);
    Arrays.sort(arr);

    String[] answer = new String[len];
    for (int i = 0; i < len; i++) answer[i] = arr[i].toString();
    return answer;
  }

  static class FileName implements Comparable<FileName> {
    static final Pattern r = Pattern.compile("(\\D+)(\\d+)(.*)");
    private final String head, tail, origin;
    private final int number;

    public FileName(String fileName) {
      origin = fileName;
      Matcher m = r.matcher(fileName.toLowerCase());
      m.find();
      head = m.group(1);
      number = Integer.parseInt(m.group(2));
      tail = m.group(3);
    }

    @Override
    public int compareTo(FileName o) {
      if (!head.equals(o.head)) return head.compareTo(o.head);
      return number - o.number;
    }

    @Override
    public String toString() {
      return origin;
    }
  }
}
