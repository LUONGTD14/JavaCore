import java.io.*;
import java.util.Objects;

public class JavaLang {
    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Override phương thức hashCode() dựa trên name và age
        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) return true;
//            if (obj == null || getClass() != obj.getClass()) return false;
//            Person person = (Person) obj;
//            return age == person.age && Objects.equals(name, person.name);
//        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
        public static void main(String[] args) {
//            Person person1 = new Person("Alice", 25);
//            Person person2 = new Person("Alice", 25);
//            Person person3 = new Person("Bob", 30);
//
//            // In ra hashCode của các đối tượng
//            System.out.println("HashCode of person1: " + person1.hashCode());
//            System.out.println("HashCode of person2: " + person2.hashCode());
//            System.out.println("HashCode of person3: " + person3.hashCode());
//
//            // So sánh các đối tượng
//            System.out.println("person1.equals(person2): " + person1.equals(person2)); // true
//            System.out.println("person1.equals(person3): " + person1.equals(person3)); // false
//
//            Integer a = new Integer("3");
//            Integer b = new Integer("3");
//            System.out.println(a.equals(b));

//            try {
//                Process process = Runtime.getRuntime().exec("ping www.google.com");
//                process.waitFor(); // Đợi lệnh thực thi xong
//
//                // Đọc kết quả đầu ra của lệnh
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            String notepadPlusPlusPath = "C:\\Program Files\\Notepad++\\notepad++.exe";
            // Đường dẫn tới tệp văn bản sẽ được ghi
            String filePath = "D:\\myfile.txt"; // Đảm bảo thư mục này tồn tại

            // Tạo tệp văn bản và ghi nội dung
            try {
                // Tạo đối tượng File
                File file = new File(filePath);

                // Nếu tệp không tồn tại, tạo mới
                if (file.createNewFile()) {
                    System.out.println("Tệp đã được tạo: " + file.getName());
                } else {
                    System.out.println("Tệp đã tồn tại: " + file.getName());
                }

                // Ghi nội dung vào tệp
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("hôm nay là buổi đầu tiên học java.");
                    writer.newLine(); // Thêm dòng mới
                    writer.write("tôi thích java");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Mở Notepad++ với tệp đã tạo
            try {
                Process process = Runtime.getRuntime().exec(new String[]{notepadPlusPlusPath, filePath});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
