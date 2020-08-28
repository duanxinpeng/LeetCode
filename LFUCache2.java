import java.util.HashMap;

public class LFUCache2 {
    class Node {
        //频率
        int cnt;
        int key,value;
        Node last,next;

        public Node(int cnt, int key, int value) {
            this.cnt = cnt;
            this.key = key;
            this.value = value;
            this.last = null;
            this.next = null;
        }

        public Node(int cnt, int key, int value, Node last, Node next) {
            this.cnt = cnt;
            this.key = key;
            this.value = value;
            this.last = last;
            this.next = next;
        }
    }
    class DList {
        Node head,tail;
        public DList() {
            this.head = new Node(-1,-1,-1);
            this.tail = new Node(-1,-1,-1);
            this.head.next = this.tail;
            this.tail.last = this.head;
        }
        //是否为空
        public boolean isEmpty() {
            if (this.head.next== this.tail) {
                return true;
            }
            return false;
        }
        public void remove(Node node) {
            node.last.next = node.next;
            node.next.last = node.last;
        }
        public Node pop() {
            Node tmp = this.tail.last;
            remove(tmp);
            return tmp;
        }
        public void push(Node node) {
            node.last = this.head;
            node.next = this.head.next;
            this.head.next = node;
            node.next.last = node;
        }
    }
    private int capacity;
    private int size;
    //在频率相同的情况下，就是一个LRU，用HashMap存储不同的key
    private HashMap<Integer,DList> fmap;
    //用于存放每个node
    private HashMap<Integer,Node> nmap;
    //用于标识最小的频率
    private int minFreq = 0;

    public LFUCache2(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.fmap = new HashMap<>();
        this.nmap = new HashMap<>();
    }

    public int get(int key) {
        // minFreq, fmap
        if (capacity==0||!nmap.containsKey(key)) {
            return -1;
        } else {
            //查询一个node，只会影响fmap和minFreq！
            Node tmp = nmap.get(key);
            //在fmap中删除该节点，并有可能更新minFreq；
            fmap.get(tmp.cnt).remove(tmp);
            if (fmap.get(tmp.cnt).isEmpty()) {
                fmap.remove(tmp.cnt);
                if (minFreq == tmp.cnt) {
                    minFreq++;
                }
            }
            //更新其频率
            tmp.cnt++;
            if (fmap.containsKey(tmp.cnt)) {
                fmap.get(tmp.cnt).push(tmp);
            } else {
                DList dl = new DList();
                dl.push(tmp);
                fmap.put(tmp.cnt,dl);
            }
            return tmp.value;
        }
    }

    public void put(int key, int value) {
        if (capacity > 0) {
            if (nmap.containsKey(key)) {
                Node tmp = nmap.get(key);
                fmap.get(tmp.cnt).remove(tmp);
                if (fmap.get(tmp.cnt).isEmpty()) {
                    fmap.remove(tmp.cnt);
                    if (minFreq == tmp.cnt) {
                        minFreq++;
                    }
                }
                tmp.cnt++;
                tmp.value = value;
                if (fmap.containsKey(tmp.cnt)) {
                    fmap.get(tmp.cnt).push(tmp);
                } else {
                    DList dl = new DList();
                    dl.push(tmp);
                    fmap.put(tmp.cnt,dl);
                }
            } else {
                if (this.size==this.capacity) {
                    Node tmp = fmap.get(minFreq).pop();
                    if (fmap.get(minFreq).isEmpty()) {
                        fmap.remove(minFreq);//最后肯是1，所以这里不用管minFreq
                    }
                    nmap.remove(tmp.key);
                    this.size--;
                }
                Node tmp = new Node(1,key,value);
                this.minFreq = 1;
                if (fmap.containsKey(minFreq)) {
                    fmap.get(minFreq).push(tmp);
                } else {
                    DList dl = new DList();
                    dl.push(tmp);
                    fmap.put(tmp.cnt,dl);
                }
                //总是忘记加
                nmap.put(key,tmp);
                this.size++;
            }
        }
    }
}
