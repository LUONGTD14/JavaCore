import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Predicate;

// Lớp CustomObserver để lắng nghe thông báo và in kết quả
class CustomObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            System.out.println((String) arg);
        }
    }
}

// Lớp RandomNumbersList (Observable)
class RandomNumbersList extends Observable {
    private ArrayList<Integer> numbers;

    public RandomNumbersList() {
        numbers = new ArrayList<>();
    }

    // Phương thức để tạo 10 số ngẫu nhiên từ 0 đến 30 và lưu vào danh sách
    public void generateRandomNumbers() {
        for (int i = 0; i < 10; i++) {
            int randomNumber = (int) (Math.random() * 31);  // Random từ 0 đến 30
            numbers.add(randomNumber);
        }
    }

    // Phương thức xử lý danh sách
    public void processList() {
        ListIterator<Integer> iterator = numbers.listIterator();

        while (iterator.hasNext()) {
            int current = iterator.next();

            if (iterator.hasPrevious() && iterator.hasNext()) {
                int previous = iterator.previous();
                iterator.next(); // Trở lại phần tử hiện tại
                int next = iterator.next();

                double sqrtPrev = StrictMath.sqrt(previous);
                double sqrtNext = StrictMath.sqrt(next);

                // Predicate kiểm tra độ lệch căn bậc hai <= 0.5
                Predicate<Double> isWithinRange = diff -> diff <= 0.5;

                double difference = Math.abs(sqrtPrev - sqrtNext);
                if (isWithinRange.test(difference)) {
                    // In ra hai phần tử nếu độ lệch nhỏ hơn hoặc bằng 0.5
                    setChanged();
                    notifyObservers("Hai phần tử gần nhau: " + previous + " và " + next);
                } else {
                    // Nếu độ lệch lớn hơn 0.5, thực hiện lệnh ping Google
                    try {
                        String pingResult = pingGoogle();
                        setChanged();
                        notifyObservers("Ping Google - Thời gian trung bình của 4 gói tin: " + pingResult);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Trở lại phần tử hiện tại sau khi xử lý
                iterator.previous();
            }
        }
    }

    // Phương thức thực hiện ping Google và lấy thời gian trung bình
    private String pingGoogle() throws Exception {
        Process process = Runtime.getRuntime().exec("ping -n 4 www.google.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String avgTime = null;

        // Duyệt qua các dòng kết quả ping
        while ((line = reader.readLine()) != null) {
            if (line.contains("Average")) {
                // Tách chuỗi để lấy thời gian trung bình
                String[] parts = line.split(",");
                avgTime = parts[2];  // Giá trị thời gian trung bình
            }
        }

        process.waitFor();
        return avgTime != null ? avgTime : "Không xác định";
    }
}

public class StudentManagement
{
    public static void main(String[] args) {
        // Tạo RandomNumbersList và CustomObserver
        RandomNumbersList randomList = new RandomNumbersList();
        CustomObserver observer = new CustomObserver();

        // Đăng ký Observer
        randomList.addObserver(observer);

        // Sinh số ngẫu nhiên và xử lý danh sách
        randomList.generateRandomNumbers();
        randomList.processList();
    }
}
