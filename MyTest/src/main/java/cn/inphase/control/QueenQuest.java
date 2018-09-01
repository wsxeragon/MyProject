package cn.inphase.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class QueenQuest {

    @Test
    public void test1() {
        List<Integer> numbers = new LinkedList<>();
        numbers.add(0);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        int[] column = new int[8];
        int[] cross1 = new int[8];
        int[] cross2 = new int[8];
        boolean hasResult = true;
        for (int i = 0; i < 8; i++) {
            if (!hasResult) {
                break;
            }
            List<Integer> tempnumbers = new ArrayList<>();
            tempnumbers.addAll(numbers);
            while (true) {
                if (tempnumbers.size() == 0) {
                    System.out.println("无结果");
                    hasResult = false;
                    break;
                }
                int temp = tempnumbers.get(new Random().nextInt(tempnumbers.size()));
                int tempCross1 = i - temp;
                int tempCross2 = temp + i;
                if (i == 0) {
                    numbers.remove(numbers.indexOf(temp));
                    cross1[i] = tempCross1;
                    cross2[i] = tempCross2;
                    column[i] = temp;
                    break;
                }
                boolean flag = true;
                for (int j = 0; j <= i; j++) {
                    if (tempCross1 == cross1[j] || tempCross2 == cross2[j]) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    tempnumbers.remove(tempnumbers.indexOf(temp));
                    continue;
                }
                numbers.remove(numbers.indexOf(temp));
                cross1[i] = tempCross1;
                cross2[i] = tempCross2;
                column[i] = temp;
                break;
            }
        }
        for (int i = 0; i < column.length; i++) {
            System.out.println(column[i]);
            System.out.println("正对角线" + cross1[i]);
            System.out.println("负对角线" + cross2[i]);
        }
    }

}
