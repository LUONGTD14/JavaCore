import java.util.TreeSet;

public class Pro124 {
    class Mineral implements Comparable<Mineral>{
        int mine, type, cost, content;
        public Mineral(int mine, int type, int cost, int content) {
            this.mine = mine;
            this.type = type;
            this.cost = cost;
            this.content = content;
        }
        @Override
        public int compareTo(Mineral o) {
            if(this.content == o.content){
                return this.cost - o.cost; //cost giam dan
            }else{
                return o.content - this.content;//content tang dan
            }
        }
    }
    TreeSet<Mineral>[][] map = new TreeSet[2][3];
    int min, max, ship;
    void init(int mShipFeee){
        this.ship = mShipFeee;
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                map[i][j] = new TreeSet<>();
            }
        }
    }
    int gather(int mMineId, int mType, int mCost, int mContent){
        if(min > mContent) min = mContent;
        if(max < mContent) max = mContent;
        map[mMineId][mType].add(new Mineral(mMineId, mType, mCost, mContent));
        return map[mMineId][mType].size();
    }
    Solution.Resolution mix(int mBudget){
        Solution.Resolution res = new Solution.Resolution();
        Mineral[] ans = new Mineral[3];
        int left = min, right = max;
        while(left <= right){
            int mid = (left + right)/2;
            Mineral[][] tmp = new Mineral[3][3];
            int[] price = new int[3];
            Mineral mineral = new Mineral(0, 0, 0, mid);
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 3; j++){
                    tmp[i][j] = map[i][j].ceiling(mineral);
                }
            }
            mineral.cost = 400007;
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 3; j++){
                    if(tmp[i][j] == null){
                        tmp[i][j] = mineral;
                    }
                }
            }
            for(int j = 0; j < 3; j++){
                tmp[2][j] = tmp[0][j].cost < tmp[1][j].cost ? tmp[0][j] : tmp[1][j];
                price[0] += tmp[0][j].cost;
                price[1] += tmp[1][j].cost;
                price[2] += tmp[2][j].cost;
            }
            price[0] += ship;
            price[1] += ship;
            price[2] += ship * 2;
            int minCost = Math.min(price[0], Math.min(price[1], price[2]));
            if(minCost <= mBudget){
                for(int i = 0; i < 3; i++){
                    if(price[i] == minCost){
                        ans = tmp[i].clone();
                    }
                }
                res.mContent = mid;
                res.mCost = minCost;
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        if(res.mCost != 0){
            for(int i = 0; i < 3; i++){
                map[ans[i].mine][ans[i].type].remove(ans[i]);
            }
        }
        return res;
    }
}
