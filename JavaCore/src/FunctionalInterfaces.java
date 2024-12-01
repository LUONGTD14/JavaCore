import java.util.function.*;

public class FunctionalInterfaces {
    public static void main(String[] args) {
        Function<String, Integer> stringLength = str -> str.length();
        Integer length = stringLength.apply("Hello, World!");
        System.out.println("Length: " + length); // In ra: Length: 13
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4)); // In ra: Is 4 even? true
        System.out.println("Is 5 even? " + isEven.test(5)); // In ra: Is 5 even? false
        Consumer<String> print = str -> System.out.println(str);
        print.accept("Hello, World!"); // In ra: Hello, World!
        Supplier<String> stringSupplier = () -> "Hello, World!";
        System.out.println(stringSupplier.get()); // In ra: Hello, World!
    }
}
