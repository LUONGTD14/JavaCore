public class ThreadLifecycleDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("Running...");
                Thread.sleep(1000); // TIMED_WAITING
                System.out.println("Thread finished!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Thread state: " + thread.getState()); // NEW
        thread.start();
        System.out.println("Thread state after start(): " + thread.getState()); // RUNNABLE

        try {
            thread.sleep(500);
            String s = new String();
            System.out.println("Thread state while running: " + thread.getState()); // TIMED_WAITING or RUNNABLE
            thread.join(); // wait thread finish
            System.out.println("Thread state after join(): " + thread.getState()); // TERMINATED
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread finished!");
    }
}

