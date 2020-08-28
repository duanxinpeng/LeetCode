public class MedianFinderPlus {
    private int[] array;
    private int count;

    /**
     * initialize your data structure here.
     */
    public MedianFinderPlus() {
        array = new int[103];
        count = 0;
    }

    public void addNum(int num) {
        if (num > 100)
            array[102]++;
        else if (num < 0)
            array[0]++;
        else
            array[num+1]++;
        count++;
    }

    public double findMedian() {
        int sum = 0;
        if (count%2==0) {
            for (int i = 0; i < 103; i++) {
                sum += array[i];
                if (sum == count/2)
                    return (i+i-1)/2.0;
                else if (sum > count/2)
                    return i-1;
            }
        } else {
            int index = count/2+1;
            for (int i = 0; i < 103; i++) {
                sum+=array[i];
                if (sum >= index)
                    return i-1;
            }
        }
        return 0;
    }
}
