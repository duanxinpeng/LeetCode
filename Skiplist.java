class SkiplistNode {
    int value;
    SkiplistNode[] levels;

    public SkiplistNode(int value, int level) {
        this.value = value;
        this.levels = new SkiplistNode[level];
    }
}

public class Skiplist {
    int level;
    int length;
    final int MAXLEVEL = 32;
    SkiplistNode header;

    public Skiplist() {
        this.length = 0;
        this.level = 1;
        this.header = new SkiplistNode(-1, MAXLEVEL);
    }

    public boolean search(int target) {
        SkiplistNode p = this.header;
        for (int i = this.level - 1; i >= 0; i--) {
            while (p.levels[i] != null && p.levels[i].value <= target)
                p = p.levels[i];
            if (p.value == target)
                return true;
        }
        return false;
    }

    public void add(int num) {
        //记录插入位置的前一个节点
        //这个数组的长度必须是 MAXLEVEL 因为插入一个节点之后，level 可能会比当前的 level 大；
        SkiplistNode[] update = new SkiplistNode[MAXLEVEL];
        SkiplistNode p = this.header;
        for (int i = this.level - 1; i >= 0; i--) {
            while (p.levels[i] != null && p.levels[i].value < num)
                p = p.levels[i];
            update[i] = p;
        }
        int level = randomLevel();
        if (level > this.level) {
            for (int i = this.level; i < level; i++) {
                update[i] = this.header;
            }
            this.level = level;
        }
        SkiplistNode x = new SkiplistNode(num, level);
        // level 和 this.level 的区别； 这里必须是 level 不能是 this.level!!!!!
        for (int i = 0; i < level; i++) {
            x.levels[i] = update[i].levels[i];
            update[i].levels[i] = x;
        }
        this.length++;
    }

    public boolean erase(int num) {
        SkiplistNode[] update = new SkiplistNode[this.level];
        SkiplistNode p = this.header;
        for (int i = this.level - 1; i >= 0; i--) {
            while (p.levels[i] != null && p.levels[i].value < num)
                p = p.levels[i];
            update[i] = p;
        }
        // 需要被删除的节点！
        p = p.levels[0];
        // 各种细节啊！！
        if (p != null && p.value == num) {
            for (int i = 0; i < this.level; i++) {
                // update[i].levels[i] 可能为 null！！！！！！ 所以不能用 update[i].levels[i].value == p.value 来判断！
                if (update[i].levels[i] == p) {
                    update[i].levels[i] = update[i].levels[i].levels[i];
                }
            }
            while (this.level > 1 && this.header.levels[this.level - 1] == null)
                this.level--;
            this.length--;
            return true;
        } else {
            return false;
        }
    }

    public int randomLevel() {
        int level = 1;
        //[0.0, 1.0)
        while (Math.random() < 0.25) {
            level++;
            if (level == MAXLEVEL)
                break;
        }
        return level;
    }
}
