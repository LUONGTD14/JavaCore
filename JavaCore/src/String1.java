public class String1 {
    public static void main(String[] args) {
        System.out.println("\1234");
        System.out.println("\01234");
        String str = "\u1041";  // Ký tự 'A'
        System.out.println("Ký tự: " + str);  // In ra: Ký tự: A
//
//        String tab = "\u0009";      // Mã ASCII của tab (9)
//
//        System.out.println("Column 1" + tab + "Column 2");
////        String s = "Hello";
////        System.out.println("before " + s.hashCode());
////        s += " Java";
////        System.out.println("after " + s.hashCode());
////
////        StringBuilder sb = new StringBuilder("Hello");
////        System.out.println("before " + sb.hashCode());
////        sb.append(" Java");
////        System.out.println("after " + sb.hashCode());
//
//        int iterations = 10000;
//
//        // Test with String
//        long startTime = System.nanoTime();
//        String str = "Hello";
//        for (int i = 0; i < iterations; i++) {
//            str += " World";
//        }
//        long endTime = System.nanoTime();
//        System.out.println("Time taken with String: " + (endTime - startTime) / 1_000 + " us"); //microsecond
//
//        // Test with StringBuilder
//        startTime = System.nanoTime();
//        StringBuilder sb = new StringBuilder("Hello");
//        for (int i = 0; i < iterations; i++) {
//            sb.append(" World");
//        }
//        endTime = System.nanoTime();
//        System.out.println("Time taken with StringBuilder: " + (endTime - startTime) / 1_000 + " us");
//
//        // Test with StringBuffer
//        startTime = System.nanoTime();
//        StringBuffer sbf = new StringBuffer("Hello");
//        for (int i = 0; i < iterations; i++) {
//            sbf.append(" World");
//        }
//        endTime = System.nanoTime();
//        System.out.println("Time taken with StringBuffer: " + (endTime - startTime) / 1_000 + " us");
    }
}
