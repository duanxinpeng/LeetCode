import java.util.HashMap;

public class LinkedList {
    class Node {
        int val;
        Node next;
        Node random;
        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    // LeeCode-138 复制带随机指针的链表
    HashMap<Node,Node> map138 = new HashMap<>();
    public Node copyRandomList(Node head) {
        if (head == null)
            return null;
        if (map138.containsKey(head))
            return map138.get(head);
        Node tmp = new Node(head.val);
        //必须先put，再递归!!!!!必须先put！！！
        map138.put(head,tmp);

        tmp.next = copyRandomList(head.next);
        tmp.random = copyRandomList(head.random);
        return tmp;
    }
    // LeeCode-328 奇偶链表
    public ListNode oddEvenList(ListNode head) {
        if (head == null)
            return null;
        ListNode odd = new ListNode(-1);
        ListNode even = new ListNode(-1);
        ListNode p = head, pOdd = odd, pEven = even;
        int i = 1;
        while (p != null) {
            if (i%2==0) {
                pEven.next = p;
                p = p.next;
                pEven = pEven.next;
                pEven.next = null;
            } else {
                pOdd.next = p;
                p = p.next;
                pOdd = pOdd.next;
                pOdd.next = null;
            }
            i++;
        }
        pOdd.next = even.next;
        return odd.next;
    }
    // LeeCode-25 k 个一组反转链表
    public ListNode reverse_25(ListNode head) {
        // 必须用指针p，head不能动，head需要作为tail的有用！
        // 因为java里面所有的参数传递都是值传递，所以对head的修改会直接影响引用head的指向！
        ListNode p = head;
        ListNode dnode = new ListNode(-1);
        while (p != null) {
            ListNode tmp = p;
            p = p.next;
            tmp.next = dnode.next;
            dnode.next = tmp;
        }
        return dnode.next;
    }
    public ListNode reverseKGroup(ListNode head, int k) {
        int i = 0;
        ListNode res = new ListNode(-1);
        ListNode tail = res;
        ListNode p = head;
        while (true) {
            // 为什么是小于 k-1 ？
            // 因为需要截断前 k 个节点
            // 所以必须停在第k个节点，而不能停在第 k 个节点的下一个节点，
            while (p!=null && i < k-1) {
                p = p.next;
                i++;
            }
            // 如果 p == null 说明必然是节点数量不够了！
            if (p == null) {
                tail.next = head;
                return res.next;
            } else {
                // 因为需要截断链表，所以必须先把 head 挪过去！ 所以需要先对 head 做一下保存！
                ListNode tmp = head;
                head = p.next;
                // 截断链表！
                p.next = null;
                p = head;

                tail.next = reverse_25(tmp);
                // 调用 reverse 之后，tmp 就变成了尾部了！！！
                // 主要还是因为 reverse 函数没有直接在参数 head 上做改动！
                tail = tmp;
            }
            // 别忘了置零！
            i = 0;
        }
    }
}