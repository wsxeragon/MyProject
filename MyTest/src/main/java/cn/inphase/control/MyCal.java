package cn.inphase.control;

import java.util.Arrays;
import java.util.Stack;

public class MyCal {

    // 存储数据
    private Stack<Integer> intStack = new Stack<>();
    // 存储操作符
    private Stack<String> opeStack = new Stack<>();

    static String[] opes = new String[] { "+", "-", "*", "/", "(", ")" };

    public String addBlank(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (!Arrays.asList(opes).contains("" + str.charAt(i))) {
                sb.append(str.charAt(i));
            } else {
                sb.append(" ");
                sb.append(str.charAt(i));
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public int analysisExp(String exp) {
        exp = addBlank(exp);
        String[] arrs = exp.split(" ");
        for (int i = 0; i < arrs.length; i++) {
            if (arrs[i].equals(""))
                continue;
            // 数字
            if (!Arrays.asList(opes).contains(arrs[i])) {
                intStack.push(Integer.parseInt(arrs[i]));
                // 如果是最后一个数字，那循环计算
                if (i == arrs.length - 1) {
                    while (opeStack.size() != 0) {
                        cal();
                    }
                }
                continue;
            }
            // + - 优先度最低，遇到+ - 可以直接计算栈里的值
            if (arrs[i].equals("+") || arrs[i].equals("-")) {
                while (opeStack.size() != 0 && !opeStack.peek().equals("(")) {
                    cal();
                }
                opeStack.push(arrs[i]);
                continue;
            }
            // * / 优先度最大，如果上一个还是* / 怎计算 否则先不计算，放入即可
            if (arrs[i].equals("*") || arrs[i].equals("/")) {
                while (opeStack.size() != 0 && (opeStack.peek().equals("*") || opeStack.peek().equals("/"))) {
                    cal();
                }
                opeStack.push(arrs[i]);
                continue;
            }
            if (arrs[i].equals("(")) {
                opeStack.push(arrs[i]);
                continue;
            }
            // 遇到 )，一直计算到遇到第一个 (
            if (arrs[i].equals(")")) {
                while (!opeStack.peek().equals("(")) {
                    cal();
                }
                opeStack.pop();
                continue;
            }
        }
        return intStack.peek();
    }

    private void cal() {
        String o = opeStack.pop();
        int a = intStack.pop();
        int b = intStack.pop();
        if (o.equals("+")) {
            intStack.push(b + a);
        }
        if (o.equals("-")) {
            intStack.push(b - a);
        }
        if (o.equals("*")) {
            intStack.push(b * a);
        }
        if (o.equals("/")) {
            intStack.push(b / a);
        }
    }

    public static void main(String[] args) {
        MyCal myCal = new MyCal();
        String exp = "(3+ (4-1)*3)/2 +2";
        System.out.println(myCal.analysisExp(exp));
    }
}
