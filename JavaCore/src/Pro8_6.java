import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Pro8_6 {
    int[] d = {-1, 1};
    int[][] a, b, visit;
    int h, w;
    HashMap<Integer, Box> hash;

    void init(int mH, int mW) {
        h = mH;
        w = mW;
        a = new int[h][w];
        b = new int[h][w];
        hash = new HashMap<>();
    }

    int drop(int mId, int mLen, int mIdxA, int mIdxB, int mCol) {
        int res = h - 1;
        for (int i = 0; i < h; i++) {
            int check = 1;
            for (int j = mCol; j < mCol + mLen; j++) {
                if (a[i][j] != 0) {
                    check = 0;
                    break;
                }
            }
            if (check == 1) res = i;
            else break;
        }
        for (int j = mCol; j < mCol + mLen; j++) {
            a[res][j] = mId;
        }
        b[res][mCol + mIdxA] = 1;
        b[res][mCol + mIdxB] = 1;
        hash.put(mId, new Box(mId, res, mCol, mLen));
        return res;
    }

    int move(int mIdA, int mIdB) {
        PriorityQueue<Space> queue = new PriorityQueue<>();
        Box box = hash.get(mIdA);
        for (int j = box.col; j < box.col + box.len; j++) {
            queue.add(new Space(mIdA, box.row, j, 0));
        }
        visit = new int[h][w];
        while (!queue.isEmpty()){
            Space space = queue.poll();
            visit[space.x][space.y] = 1;
            if(space.id == mIdB){
                return space.count;
            }
            int x = space.x, y = space.y, count = space.count;
            for (int i = 0; i < 2; i++){
                int ny = y + d[i];
                if(ny >= 0 && ny < w && visit[x][ny] == 0 && a[x][ny] != 0){
                    if(a[x][y] != a[x][ny]) queue.add(new Space(a[x][ny], x, ny, count+1));
                    else queue.add(new Space(a[x][ny], x, ny, count));
                }
            }
            if(b[x][y] == 1){
                for (int i = 0; i < 2; i++){
                    int nx = x + d[i];
                    if(nx >= 0 && nx < h && visit[nx][y] == 0 && a[nx][y] != 0){
                        if(a[x][y] != a[nx][y]) queue.add(new Space(a[nx][y], nx, y, count+1));
                        else queue.add(new Space(a[nx][y], nx, y, count));
                    }
                }
            }
        }
        return -1;
    }
    class Space implements Comparable<Space> {
        int id, x, y, count;
        public Space(int id, int x, int y, int count) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.count = count;
        }
        @Override
        public int compareTo(Space o) {
            return this.count - o.count;
        }
    }
    class Box {
        int id, row, col, len;
        public Box(int id, int row, int col, int len) {
            this.id = id;
            this.row = row;
            this.col = col;
            this.len = len;
        }
    }
}
