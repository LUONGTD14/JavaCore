package spoj;

import java.util.Scanner;

public class Busqueda_Binaria {
    static class SegmentTree {
        private int[] tree;
        private int[] lazy;
        private int n;

        public SegmentTree(int size) {
            n = size;
            tree = new int[4 * n];
            lazy = new int[4 * n];
        }

        public void build(int[] arr) {
            build(0, n - 1, 0, arr);
        }

        private void build(int start, int end, int node, int[] arr) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(start, mid, 2 * node + 1, arr);
                build(mid + 1, end, 2 * node + 2, arr);
                tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
            }
        }

        public void update(int idx, int value) {
            update(0, n - 1, idx, value, 0);
        }

        private void update(int start, int end, int idx, int value, int node) {
            if (start == end) {
                tree[node] = value;
            } else {
                int mid = (start + end) / 2;
                if (idx <= mid) {
                    update(start, mid, idx, value, 2 * node + 1);
                } else {
                    update(mid + 1, end, idx, value, 2 * node + 2);
                }
                tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
            }
        }

        public int query(int L, int R, int C) {
            return query(0, n - 1, L, R, 0, C);
        }

        private int query(int start, int end, int L, int R, int node, int C) {
            if (start > R || end < L) {
                return 0;
            }
            if (start >= L && end <= R) {
                return tree[node] >= C ? 1 : 0;
            }
            int mid = (start + end) / 2;
            return query(start, mid, L, R, 2 * node + 1, C) + query(mid + 1, end, L, R, 2 * node + 2, C);
        }
    }

    public static void main(String[] args) {
    	System.out.println("hello");
    	try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] X = new int[N];
        for (int i = 0; i < N; i++) {
            X[i] = sc.nextInt();
        }

        SegmentTree segTree = new SegmentTree(N);
        segTree.build(X);

        int Q = sc.nextInt();
        for (int i = 0; i < Q; i++) {
            int type = sc.nextInt();
            if (type == 0) {
                int a = sc.nextInt() - 1;
                int b = sc.nextInt() - 1;
                int c = sc.nextInt();
                System.out.println(segTree.query(a, b, c));
            } else if (type == 1) {
                int a = sc.nextInt() - 1;
                int b = sc.nextInt();
                segTree.update(a, b);
            }
        }
        sc.close();
    }
}
