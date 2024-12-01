import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Thread1 extends Thread{
    List<Integer> list = new ArrayList<>();
    @Override
    public void run() {
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            int rad = random.nextInt(100);
            list.add(rad);
            System.out.println("t1 " + (i+1) + " :"  + rad);
            try {
                Thread.sleep(1000);
                Thread.currentThread().notifyAll();
            } catch (InterruptedException e) {
                Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
