package com.ll;

public class Calc {
  public static int run(String exp) { // 10 + (10 + 5)
    exp = exp.trim(); //좌우 공백 제거
    exp = stripOuterBracket(exp);// stripOuterBracket에서 실행하고 리턴받은 exp 값을 exp에 넣어준다

    // 연산기호가 없으면 바로 리턴
    if (!exp.contains(" ")) return Integer.parseInt(exp); // 

    boolean needToMultiply = exp.contains(" * "); // 곱하기 여부(o -> Ture, x -> False)
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - "); // 더하기 여부(o -> Ture, x -> False) 또는 빼기 여부(o -> Ture, x -> False)

    boolean needToCompound = needToMultiply && needToPlus; // needToMultiply랑 needToPlus의 여부 (o -> Ture, x -> False)
    boolean needToSplit = exp.contains("(") || exp.contains(")"); //"(" 여부 (o -> Ture, x -> False) 또는 ")" 여부(o -> Ture, x -> False)

    if (needToSplit) {  // 800 + (10 + 5)

      int splitPointIndex = findSplitPointIndex(exp); //

      String firstExp = exp.substring(0, splitPointIndex); // 0부터 splitPointIndex까지 있는 값을 firstExp에 넣는다.
      String secondExp = exp.substring(splitPointIndex + 1); // splitPointIndex + 1부터 끝까지 있는 값을 secondExp에 넣는다

      char operator = exp.charAt(splitPointIndex);

      exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp); // 실행한 firstExp 값이랑

      return Calc.run(exp); // 위에서 담은 exp를 돌려준다.

    } else if (needToCompound) {
      String[] bits = exp.split(" \\+ "); // +를 기준으로 쪼갠다

      return Integer.parseInt(bits[0]) + Calc.run(bits[1]); // TODO
    }
    if (needToPlus) {
      exp = exp.replaceAll("\\- ", "\\+ \\-"); // -가 있는 부분을 + -로 바꿔준다

      String[] bits = exp.split(" \\+ "); // +를 기준으로 쪼개서 String[] bits에 넣는다

      int sum = 0; // 정수를 담는 sum이라는 변수에 0를 넣는다.

      for (int i = 0; i < bits.length; i++) { // bits의 길이와 i값을 증가하면서 i가 bits의 길이값를 초과하지 않을 떄까지 반복한다
        sum += Integer.parseInt(bits[i]);
      }

      return sum; //sum에 있는 값을 돌려준다.
    } else if (needToMultiply) {
      String[] bits = exp.split(" \\* "); // *를 기준으로 쪼갠다

      int rs = 1; // 정수를 담는 rs라는 변수에 1를 넣는다

      for (int i = 0; i < bits.length; i++) { // bits의 길이와 i값을 증가하면서 i가 bits의 길이값를 초과하지 않을 떄까지 반복한다
        rs *= Integer.parseInt(bits[i]);
      }
      return rs; // rs에 담겨있는 값을 돌려준다.
    }

    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다"); 
  }

  private static int findSplitPointIndexBy(String exp, char findChar) {
    int bracketCount = 0; // 정수를 담는 bracketCount 변수에 0를 넣는다

    for (int i = 0; i < exp.length(); i++) { // 정수를 담는 i에 0를 넣고 i값을 증가시키면서 exp.length()의 값보다 초과되지 않을 때까지 반복한다  
      char c = exp.charAt(i); // 

      if (c == '(') { // c랑 "("이 같으면 
        bracketCount++; // bracketCount 1증가
      } else if (c == ')') { // c랑 "("이 같으면
        bracketCount--;
      } else if (c == findChar) { // c랑 "("이 같으면
        if (bracketCount == 0) return i; // bracketCount 값이랑 0이 같으면 i값를 돌려준다
      }
    }
    return -1;
  }

  private static int findSplitPointIndex(String exp) {
    int index = findSplitPointIndexBy(exp, '+');

    if (index >= 0) return index;

    return findSplitPointIndexBy(exp, '*');
  }

  private static String stripOuterBracket(String exp) {
    int outerBracketCount = 0;

    while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
      outerBracketCount++;
    }

    if (outerBracketCount == 0) return exp;


    return exp.substring(outerBracketCount, exp.length() - outerBracketCount);
  }
}