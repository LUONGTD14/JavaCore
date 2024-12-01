public class test {
    static int[][] a = {{11, 0, 12, 0, 13},
            {0, 14, 0, 15, 0},
            {15, 0, 14, 0, 5},
            {0, 11, 0, 12, 0},
            {15, 0, 13, 0, 14}};
    static int n = 5;

    public static void main(String[] args) {
        for(int i = 0; i < n-1; i+=2){
            for(int j = 0; j < n-1; j+=2){
                hash(i, j);
            }
        }
        for(int i = 1; i < n-2; i+=2){
            for(int j = 1; j < n-2; j+=2){
                hash(i, j);
            }
        }
        System.out.println("-----");
        int[][] b = {{1, 0, 2},
                    {0, 4, 0},
                    {5, 0, 4}};
        int[][] c = {{11, 0, 2},
                {0, 4, 0},
                {5, 0, 4}};
        hash1(b);
        hash1(c);
    }

    static void hash1(int[][] b){
        int k = 0;
        k = k * 20 + (b[0][0] < 10 ? b[0][0] + 10 : 20);
        k = k * 20 + (b[0][2] < 10 ? b[0][2] + 10 : 20);
        k = k * 20 + (b[1][1] < 10 ? b[1][1] + 10 : 20);
        k = k * 20 + (b[2][0] < 10 ? b[2][0] + 10 : 20);
        k = k * 20 + (b[2][2] < 10 ? b[2][2] + 10 : 20);
        System.out.println(k);
    }

    static void hash(int i, int j) {
        int k = 0;
        int count = 0;
        k = k * 20 + a[i][j];
        k = k * 20 + a[i][j + 2];
        k = k * 20 + a[i + 1][j + 1];
        k = k * 20 + a[i + 2][j];
        k = k * 20 + a[i + 2][j + 2];
        count += a[i][j] > 10 ? 1 : 0;
        count += a[i][j + 2] > 10 ? 1 : 0;
        count += a[i + 1][j + 1] > 10 ? 1 : 0;
        count += a[i + 2][j] > 10 ? 1 : 0;
        count += a[i + 2][j + 2] > 10 ? 1 : 0;
        System.out.println(k);
        if(count == 5){
            k = 0;
            k = k * 20 + 20;
            k = k * 20 + a[i][j + 2];
            k = k * 20 + a[i + 1][j + 1];
            k = k * 20 + a[i + 2][j];
            k = k * 20 + a[i + 2][j + 2];
            System.out.println(k);

            k = 0;
            k = k * 20 + a[i][j];
            k = k * 20 + 20;
            k = k * 20 + a[i + 1][j + 1];
            k = k * 20 + a[i + 2][j];
            k = k * 20 + a[i + 2][j + 2];
            System.out.println(k);

            k = 0;
            k = k * 20 + a[i][j];
            k = k * 20 + a[i][j + 2];
            k = k * 20 + 20;
            k = k * 20 + a[i + 2][j];
            k = k * 20 + a[i + 2][j + 2];
            System.out.println(k);

            k = 0;
            k = k * 20 + a[i][j];
            k = k * 20 + a[i][j + 2];
            k = k * 20 + a[i + 1][j + 1];
            k = k * 20 + 20;
            k = k * 20 + a[i + 2][j + 2];
            System.out.println(k);

            k = 0;
            k = k * 20 + a[i][j];
            k = k * 20 + a[i][j + 2];
            k = k * 20 + a[i + 1][j + 1];
            k = k * 20 + a[i + 2][j];
            k = k * 20 + 20;
            System.out.println(k);
        }
    }
}
