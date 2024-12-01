import java.util.Observer;
import java.util.Observable;


public class ObservableTest {
    static class WeatherData extends Observable {
        private float temperature;

        public void setTemperature(float temperature) {
            this.temperature = temperature;
            setChanged(); // Đánh dấu là đã thay đổi
            notifyObservers(temperature); // Thông báo cho các Observer
        }

        public float getTemperature() {
            return temperature;
        }
    }

    static class WeatherDisplay implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            if (o instanceof WeatherData) {
                float temperature = (float) arg;
                System.out.println("Temperature updated: " + temperature);
            }
        }
    }

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        WeatherDisplay display = new WeatherDisplay();

        // Đăng ký Observer
        weatherData.addObserver(display);

        // Cập nhật nhiệt độ
        weatherData.setTemperature(25.0f);
        weatherData.setTemperature(30.0f);
    }

}


/*
import java.util.Observable;
import java.util.Observer;

class MessageBoard extends Observable
{
    public void changeMessage(String message)
    {
        setChanged();
        notifyObservers(message);
    }
}

class Student implements Observer
{
    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("Message board changed: " + arg);
    }
}

public class ObservableTest
{
    public static void main(String[] args)
    {
        MessageBoard board = new MessageBoard();
        Student bob = new Student();
        Student joe = new Student();
        board.addObserver(bob);
        board.addObserver(joe);
        board.changeMessage("More Homework!");
    }
}
*/