import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Thread2 extends Thread{
    List<Character> list = new ArrayList<>();
    @Override
    public void run() {
        Random random = new Random();
        int min = (int) 'a', max = (int) 'z', limit = max - min;
        for(int i = 0; i < 10; i++){
            int rad = random.nextInt(limit) + min;
            list.add((char) rad);
            System.out.println("t2 " + (i+1) + " :" + ((char) rad));
            try {
                Thread.sleep(2000);
                Thread.currentThread().notifyAll();
            } catch (InterruptedException e) {
                Logger.getLogger(Thread2.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
