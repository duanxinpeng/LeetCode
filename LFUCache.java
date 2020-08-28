import java.util.HashMap;
import java.util.TreeSet;

class Node implements Comparable {
    int cnt;
    int time;
    int key, value;

    @Override
    public int compareTo(Object o) {
        Node tmp = (Node)o;
        if (this.cnt==((Node) o).cnt && this.time == ((Node) o).time) {
            return 0;
        } else {
            if (this.cnt < ((Node) o).cnt) {
                return -1;
            } else {
                if (this.cnt == ((Node) o).cnt && this.time < ((Node) o).time) {
                    return -1;
                }
            }
        }
        return 1;
    }

    public Node(int cnt, int time, int key, int value) {
        this.cnt = cnt;
        this.time = time;
        this.key = key;
        this.value = value;
    }
}
public class LFUCache {
    private int capacity,time;
    private int size;
    private HashMap<Integer,Node> map;
    private TreeSet<Node> set;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.time = 0;
        this.size = 0;
        this.map = new HashMap<>();
        this.set = new TreeSet<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        } else {
            Node tmp = map.get(key);
            set.remove(tmp);
            tmp.cnt++;
            tmp.time = ++time;
            set.add(tmp);
            return tmp.value;
        }
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node tmp = map.get(key);
            set.remove(tmp);
            tmp.cnt++;
            tmp.time = ++time;
            tmp.value = value;
            set.add(tmp);
        } else {
            if (this.size == this.capacity) {
                Node first = set.first();
                set.remove(first);
                map.remove(first.key);
                this.size--;
            }
            Node tmp = new Node(1,++time,key,value);
            map.put(key,tmp);
            set.add(tmp);
            this.size++;
        }
    }
}
