import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Graph {
    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    // Leecode-133 克隆图
    HashMap<Node,Node> map = new HashMap<>();
    public Node cloneGraph(Node node) {
        if (node == null)
            return null;
        if (map.containsKey(node))
            return map.get(node);
        Node res = new Node(node.val);
        map.put(node,res);
        for (Node n : node.neighbors) {
            res.neighbors.add(cloneGraph(n));
        }
        return res;
    }
}
