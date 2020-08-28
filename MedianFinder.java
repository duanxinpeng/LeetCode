import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class MedianFinder {
    private Queue<Integer> high;
    private Queue<Integer> low;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
        high = new PriorityQueue<>();
        low = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addNum(int num) {
        if (low.size() > high.size()) {
            low.add(num);
            high.add(low.poll());
        } else {
            high.add(num);
            low.add(high.poll());
        }
    }

    public double findMedian() {
        if (low.size() > high.size())
            return low.peek();
        return (double) (low.peek() + high.peek()) / 2;
    }

}
