import java.util.Enumeration;

public class PerfectSquare {
    // 有效完全平方数
    public boolean isPerfectSquare(int num) {
        return binarySearch(num);
    }

    public boolean binarySearch(int num) {
        if (num < 2)
            return true;
        long low = 2, high = num / 2;
        while (low <= high) {
            //int mid = (low + high) / 2;
            long mid = low + (high - low) / 2;
            long multi = mid * mid;
            if (multi == num)
                return true;
            else if (multi > num)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return false;
    }

    public boolean newton(int num) {
        return false;
    }

    public static void main(String[] args) {
//        PerfectSquare ps = new PerfectSquare();
//        ps.binarySearch(16);
        double a = 12;
        int b = 12;
        System.out.println(a == b);
    }
}
