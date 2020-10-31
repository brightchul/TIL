package programmers.winter201031_2;

public class Solution {
  public static void main(String[] args) {
    Solution s = new Solution();

//    System.out.println(s.solution("hellopython", "abcdefghijk", 3));
    System.out.println(s.solution("qyyigoptvfb", "abcdefghijk", 3));
    System.out.println(s.solution("qyyigoptvfb", "abcdefghijk", -8));

  }

  public String solution(String encrypted_text, String key, int rotation) {
    int len = key.length();
    char[] arr = new char[len];
    if(rotation < 0) {
      rotation = len - (-rotation) % len;
    } else {
    rotation = rotation % len;

    }
    int idx = 0;
    for (int i = rotation; i < len; i++) {
      arr[idx++] = encrypted_text.charAt(i);
    }
    for (int i = 0; i < rotation; i++) {
      arr[idx++] = encrypted_text.charAt(i);
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < len; i++) {
      int target = arr[i] - 96;
      int keyOne = key.charAt(i) - 96;

      int result = target - keyOne;
      if(result <= 0 ) result += 26;
      result += 96;
      sb.append((char)result);
    }
    return sb.toString();
  }

    public String en(String encrypted_text, String key, int rotation) {
      String answer = "";
      char[] arr = new char[key.length()];
      for (int i = 0; i < encrypted_text.length(); i++) {
        int target = encrypted_text.charAt(i) - 96;
        int keyOne = key.charAt(i) - 96;
        arr[i] = (char)(((target + keyOne - 1) % 26) + 1 + 96);
      }

      StringBuilder sb = new StringBuilder();
      int start = key.length() - rotation%key.length();
      for(int i=start; i < key.length(); i++) {
          sb.append(arr[i]);
      }
      for(int i=0; i<start; i++) {
          sb.append(arr[i]);
      }

      return sb.toString();
    }
}
