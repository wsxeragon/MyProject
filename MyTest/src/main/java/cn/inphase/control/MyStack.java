package cn.inphase.control;

public class MyStack {
    // 存数据
    private Integer[] arr = null;
    // 存最小值
    private Integer[] arr0 = null;
    private int default_size = 10;
    private double limit = 0.8f;
    private double expand = 1.5f;

    public MyStack() {
        arr = new Integer[default_size];
        arr0 = new Integer[default_size];
    }

    public MyStack(int size) {
        arr = new Integer[size];
        arr0 = new Integer[size];
    }

    // 判断是否需要扩容
    public boolean check() {
        if (arr.length == 0)
            return false;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                count = i;
                break;
            }
        }
        if (count == 0)
            return false;
        double rate = (double) (count + 1) / (double) arr.length;
        if (rate < limit)
            return false;
        else
            return true;
    }

    // 扩容
    public void reSize() {
        int newSize = (int) (arr.length * expand);
        Integer[] newArr = new Integer[newSize];
        Integer[] newArr0 = new Integer[newSize];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
            newArr0[i] = arr0[i];
        }
        arr = newArr;
        arr0 = newArr0;
    }

    // 返回最小值
    public int min() {
        int index = 0;
        for (int i = 0; i < arr0.length; i++) {
            if (arr0[i] == null) {
                index = i;
                break;
            }
        }
        if (index == 0)
            throw new RuntimeException("empty stack exception");
        return arr0[index - 1];
    }

    public int pop() {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                index = i;
                break;
            }
        }
        if (index == 0)
            throw new RuntimeException("empty stack exception");
        int val = arr[index - 1];
        arr[index - 1] = null;
        arr0[index - 1] = null;
        return val;
    }

    public void push(int a) {
        if (check())
            reSize();
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                index = i;
                break;
            }
        }
        arr[index] = a;
        if (index == 0)
            arr0[index] = a;
        else
            arr0[index] = a <= arr0[index - 1] ? a : arr0[index - 1];
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(7);
        myStack.push(4);
        myStack.push(8);
        myStack.push(11);
        myStack.push(5);
        myStack.push(23);
        myStack.push(6);
        myStack.push(5);
        myStack.push(4);
        myStack.push(3);
        myStack.push(2);
        System.out.println("最小值：" + myStack.min());
        myStack.pop();
        System.out.println("最小值：" + myStack.min());
        myStack.pop();
        System.out.println("最小值：" + myStack.min());
    }
}
