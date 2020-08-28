class TrieNode {
    private TrieNode[] links;
    private final int R = 26;
    private boolean isEnd;

    public TrieNode() {
        this.links = new TrieNode[R];
    }

    public boolean containsKey(char ch) {
        return this.links[ch - 'a'] != null;
    }

    public TrieNode get(char ch) {
        return this.links[ch - 'a'];
    }

    public void put(char ch, TrieNode node) {
        this.links[ch - 'a'] = node;
    }

    public boolean isEnd() {
        return this.isEnd;
    }

    public void setEnd() {
        this.isEnd = true;
    }
}

public class Trie {
    private TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }

    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (node.containsKey(currentChar)) {
                node = node.get(currentChar);
            } else {
                return null;
            }
        }
        return node;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }

    //LeeCode 440 字典序的第 k 小数字
    public int getCount(long prefix, long n) {
        int cnt = 0;
        long pre = prefix, next = pre+1;
        while (pre <= n) {
            cnt += (Math.min(n + 1, next) - pre);
            pre *= 10;
            next *= 10;
        }
        return cnt;
    }

    public int findKthNumber(int n, int k) {
        long prefix = 1;
        int cnt = 1;
        while (cnt < k) {
            int count = getCount(prefix, n);
            if (cnt + count > k) {
                prefix *= 10;
                cnt++;
            } else {
                prefix++;
                cnt += count;
            }
        }
        return (int)prefix;
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.findKthNumber(13,2);
    }
}
