public class Main {
    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        try {
            thread1.join();        thread2.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread1.start();
        thread2.start();
    }
}