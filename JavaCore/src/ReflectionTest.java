import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    static class Animal {
        public Animal(String name) {
            System.out.println("Animal created: " + name);
        }
    }
    public static void main(String[] args) {
        //Class
        Class<?> clazz = String.class;

        // Lấy tất cả phương thức của lớp String
        Method[] methods = clazz.getDeclaredMethods();

        // In tên các phương thức
        for(Method method : methods) {
            System.out.println("Phương thức: " + method.getName());
        }

        // Gọi phương thức cụ thể
        String str = "Hello, Reflection!";

        int length = 0;
        try {
            Method lengthMethod = clazz.getMethod("length");
            length = (int) lengthMethod.invoke(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Độ dài chuỗi: " + length);


        Class<?> clazz1 = Animal.class;

        // Lấy constructor với tham số String
        Constructor<?> constructor = null;
        try {
            constructor = clazz1.getConstructor(String.class);
            Animal animal = (Animal) constructor.newInstance("Lion");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Tạo một đối tượng mới

    }

}
