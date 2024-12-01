import java.util.Arrays;
import java.util.Stack;

public class Array {
    public static void main(String[] args) {
        Stack<Integer> st = new Stack<Integer>();
        st.push(5);
        st.push(3);
        st.push(7);
        st.sort((o1, o2) -> {
            return o1 - o2;
        });
        while (!st.empty()){
            System.out.print(st.pop() + " ");
        }
        System.out.println();

        int[] intArray = {5, 2, 8, 3, 1};
        Arrays.sort(intArray); // Quicksort
        System.out.println("Sorted int array: " + Arrays.toString(intArray));

        String[] stringArray = {"banana", "apple", "orange"};
        Arrays.sort(stringArray, (o1, o2) -> {
            return o2.compareTo(o1);
        }); // Timsort = merge sort+insertion sort
        System.out.println("Sorted string array: " + Arrays.toString(stringArray));

    }
}
