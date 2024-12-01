import java.util.PriorityQueue;

public class Pro19_4 {
    class Node implements Comparable<Node> {
        int id, dis;

        public Node(int id, int dis) {
            this.id = id;
            this.dis = dis;
        }

        @Override
        public int compareTo(Node o) {
            return this.dis - o.dis;
        }
    }

    class Group {
        int[][] network;

        Group() {
            this.network = new int[37][37];
        }
    }

    Group[] groups;
    int n;
    int[][] map;
    int[] visit = new int[307], distan = new int[307];
    PriorityQueue<Node> queue = new PriorityQueue<>();

    public void init(int N, int K, int mNodeA[], int mNodeB[], int mTime[]) {
        this.n = N;
        groups = new Group[n + 7];
        map = new int[4][307];
        for (int i = 0; i < K; i++) {
            if (mNodeA[i] > 100 && mNodeB[i] / 100 == mNodeA[i] / 100) {
                groups[mNodeA[i] / 100].network[mNodeA[i] % 100][mNodeB[i] % 100] = mTime[i];
                groups[mNodeA[i] / 100].network[mNodeB[i] % 100][mNodeA[i] % 100] = mTime[i];
            } else {
                int row = mNodeA[i] / 100 * 10 + mNodeA[i] % 100;
                int col = mNodeB[i] / 100 * 10 + mNodeB[i] % 100;
                map[row][col] = mTime[i];
                map[col][row] = mTime[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            callBFS(i);
        }
    }

    public void addLine(int mNodeA, int mNodeB, int mTime) {
        if (mNodeA > 100 && mNodeB / 100 == mNodeA / 100) {
            groups[mNodeA / 100].network[mNodeA % 100][mNodeB % 100] = mTime;
            groups[mNodeA / 100].network[mNodeB % 100][mNodeA % 100] = mTime;
            callBFS(mNodeA / 100);
        } else {
            int row = mNodeA / 100 * 10 + mNodeA % 100;
            int col = mNodeB / 100 * 10 + mNodeB % 100;
            map[row][col] = mTime;
            map[col][row] = mTime;
        }
    }

    public void removeLine(int mNodeA, int mNodeB) {
        if (mNodeA > 100 && mNodeB / 100 == mNodeA / 100) {
            groups[mNodeA / 100].network[mNodeA % 100][mNodeB % 100] = 0;
            groups[mNodeA / 100].network[mNodeB % 100][mNodeA % 100] = 0;
            callBFS(mNodeA / 100);
        } else {
            int row = mNodeA / 100 * 10 + mNodeA % 100;
            int col = mNodeB / 100 * 10 + mNodeB % 100;
            map[row][col] = 0;
            map[col][row] = 0;
        }
    }
    public int checkTime(int start, int end){
        return BFSMap(start, end, n*10+7);
    }

    private int BFSGroup(int id, int source, int target) {
        for (int i = 1; i <= 30; i++) {
            distan[i] = 1000000;
            visit[i] = 0;
        }
        distan[source] = 0;
        queue.clear();
        queue.add(new Node(source, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            visit[node.id] = 1;
            for (int v = 1; v <= n; v++) {
                if (visit[v] == 0 && groups[id].network[node.id][v] != 0 && distan[node.id] != 1000000
                        && distan[node.id] + groups[id].network[node.id][v] < distan[v]) {
                    distan[v] = distan[node.id] + groups[id].network[node.id][v];
                    queue.add(new Node(v, distan[v]));
                }
            }
        }
        return distan[target];
    }
    private int BFSMap(int source, int target, int n) {
        for (int i = 1; i <= n; i++) {
            distan[i] = 1000000;
            visit[i] = 0;
        }
        distan[source] = 0;
        queue.clear();
        queue.add(new Node(source, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            visit[node.id] = 1;
            for (int v = 1; v <= n; v++) {
                if (visit[v] == 0 && map[node.id][v] != 0 && distan[node.id] != 1000000
                        && distan[node.id] + map[node.id][v] < distan[v]) {
                    distan[v] = distan[node.id] + map[node.id][v];
                    queue.add(new Node(v, distan[v]));
                }
            }
        }
        return distan[target];
    }

    private void callBFS(int id) {
        int x = BFSGroup(id, 1, 2);
        map[id * 10 + 1][id * 10 + 2] = x;
        map[id * 10 + 2][id * 10 + 1] = x;
        x = BFSGroup(id, 1, 3);
        map[id * 10 + 1][id * 10 + 3] = x;
        map[id * 10 + 3][id * 10 + 1] = x;
        x = BFSGroup(id, 2, 3);
        map[id * 10 + 2][id * 10 + 3] = x;
        map[id * 10 + 3][id * 10 + 2] = x;
    }
}
