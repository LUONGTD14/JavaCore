import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class check {
    static class Car implements Comparable<Car>{
        int id, com, con, rev, price;

        public Car(int id, int com, int con, int rev, int price) {
            this.id = id;
            this.com = com;
            this.con = con;
            this.rev = rev;
            this.price = price;
        }

        @Override
        public int compareTo(Car o) {
            if(this.price == o.price) return this.id - o.id;
            return this.price - o.price;
        }
    }
    static HashMap<Integer, TreeSet<Car>> hash = new HashMap<>();
    static ArrayList<Car>[] coms = new ArrayList[10];
    static void init(){
        hash.clear();
        for(int i = 0; i < 10; i++){
            coms[i] = new ArrayList<>();
        }
    }
    static void add(){
        Car[] cars = new Car[10];
        cars[0] = new Car(1, 1, 5, 2, 100);
        cars[1] = new Car(2, 1, 7, 3, 400);
        cars[2] = new Car(3, 2, 8, 2, 100);
        cars[3] = new Car(4, 1, 5, 1, 200);
        cars[4] = new Car(5, 2, 9, 2, 300);
        cars[5] = new Car(6, 2, 5, 3, 100);
        cars[6] = new Car(7, 3, 7, 1, 200);
        cars[7] = new Car(8, 3, 5, 3, 500);
        cars[8] = new Car(9, 2, 6, 3, 100);
        cars[9] = new Car(10, 1, 4, 3, 200);
        for(int i = 0; i < 10;i++){
            coms[cars[i].com].add(cars[i]);
            if(hash.containsKey(cars[i].con)){
                hash.get(cars[i].con).add(cars[i]);
            }else{
                TreeSet<Car> tree = new TreeSet<>();
                tree.add(cars[i]);
                hash.put(cars[i].con, tree);
            }
        }
    }
    static void rent(int x){
        System.out.println("rent");
        if(hash.containsKey(x)){
            TreeSet<Car> tree = hash.get(x);
            ArrayList<Car> arr = new ArrayList<>();
            while(!tree.isEmpty()){
                Car c = tree.pollFirst();
//                coms[c.com].remove(c);
                if(c.price > 0 && c.rev > 0){
                    c.rev--;
                    if(c.rev > 0){
                        arr.add(c);
//                        coms[c.com].add(c);
                    }
                    break;
                }else if(c.price > 0){
                    arr.add(c);
                }
            }
            for(Car c : arr){
                tree.add(c);
            }
        }
        for(int c : hash.keySet()){
            System.out.println("key : " + c);
            for(Car t : hash.get(c)){
                print(t);
            }
        }
        for(int i = 1; i < 4; i++){
            System.out.println("arr : " + i);
            for(Car t : coms[i]){
                print(t);
            }
        }
    }
    static void promote(int id, int dis){
        System.out.println("promote");
        for(Car c : coms[id]){
//            if(hash.containsKey(c.con)){
//                TreeSet<Car> tree = hash.get(c.con);
//                tree.remove(c);
//            }
            c.price -= dis;
//            if(c.price > 0){
//                if(hash.containsKey(c.con)){
//                    hash.get(c.con).add(c);
//                }else{
//                    TreeSet<Car> tree = new TreeSet<>();
//                    tree.add(c);
//                    hash.put(c.con, tree);
//                }
//            }
        }
        for(int c : hash.keySet()){
            System.out.println("key : " + c);
            for(Car t : hash.get(c)){
                print(t);
            }
        }
        for(int i = 1; i < 4; i++){
            System.out.println("arr : " + i);
            for(Car t : coms[i]){
                print(t);
            }
        }
    }

    static void print(Car c){
        System.out.println(c.id + " " + c.com + " " + c.con + " " + c.rev + " " + c.price);
    }

    public static void main(String[] args) {
        init();
        add();
        rent(5);
        rent(5);
        promote(3, 450);
        rent(5);
        rent(7);
    }
}
