import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {
    private Deque<Integer> queue;
    private Deque<Integer> minQueue;
    /** initialize your data structure here. */
    public MinStack() {
        queue = new ArrayDeque<>();
        minQueue = new ArrayDeque<>();
    }

    public void push(int x) {
        queue.push(x);
        if (minQueue.isEmpty()||x<=minQueue.peek())
            minQueue.push(x);
    }

    public void pop() {
        int out = queue.pop();
        if (out == minQueue.peek())
            minQueue.pop();
    }

    public int top() {
        return queue.peek();
    }

    public int getMin() {
        if (!minQueue.isEmpty())
            return minQueue.peek();
        else
            return -1;
    }
}
