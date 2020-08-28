import java.util.HashMap;



public class LRUCache {
    class Node {
        int key;
        int value;
        Node next;
        Node pre;
        public Node(int key, int value, Node next, Node pre) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.pre = pre;
        }
    }
    private HashMap<Integer,Node> map;
    private int capacity;
    private int size;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.map = new HashMap<>();
        this.head = new Node(-1,-1,null,null);
        this.tail = new Node(-1,-1,null,null);
        this.head.next = this.tail;
        this.tail.pre = this.head;
    }
    public void update(Node tmp) {
        tmp.pre.next = tmp.next;
        tmp.next.pre = tmp.pre;

        tmp.next = this.head.next;
        tmp.pre = this.head;
        this.head.next = tmp;
        tmp.next.pre = tmp;
    }
    public void delete() {
        this.map.remove(this.tail.pre.key);
        this.tail.pre = this.tail.pre.pre;
        this.tail.pre.next = this.tail;
    }
    public void insert(Node node) {
        node.pre = this.head;
        node.next = this.head.next;
        this.head.next = node;
        node.next.pre = node;
    }
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        } else {
            Node tmp = map.get(key);
            this.update(tmp);
            return tmp.value;
        }
    }

    public void put(int key, int value) {
        if (this.map.containsKey(key)) {
            Node tmp = map.get(key);
            tmp.value = value;
            this.update(tmp);
        } else {
            if (this.size > this.capacity) {
                delete();
                this.size--;
            }
            Node tmp = new Node(key,value,null,null);
            map.put(key,tmp);
            insert(tmp);
            this.size++;
        }
    }
}
