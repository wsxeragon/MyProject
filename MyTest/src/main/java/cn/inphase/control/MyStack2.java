package cn.inphase.control;

public class MyStack2 {

    private Node first;
    private Node first0;

    public void push(int val) {
        Node node = new Node(first, val);
        Node node0 = new Node(first, val);
        if (first != null) {
            node0.setVal(first.getVal() < val ? first.getVal() : val);
        }
        node.setNext(first);
        node0.setNext(first0);
        first = node;
        first0 = node0;
    }

    public int pop() {
        if (first == null) {
            throw new RuntimeException("空栈");
        }
        int val = first.getVal();
        first = first.getNext();
        first0 = first0.getNext();
        return val;
    }

    public int min() {
        if (first == null) {
            throw new RuntimeException("空栈");
        }
        return first0.getVal();
    }

    public static void main(String[] args) {
        MyStack2 stack = new MyStack2();
        stack.push(3);
        stack.push(5);
        stack.push(2);
        stack.push(5);
        System.out.println(stack.min());
        stack.pop();
        stack.pop();
        System.out.println(stack.min());
        stack.push(0);
        System.out.println(stack.min());
    }

}

class Node {

    private Node next;
    private int val;

    public Node() {
        super();
    }

    public Node(Node next, int val) {
        super();
        this.next = next;
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

}
