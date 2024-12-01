class Aa{
    Aa(){
        System.out.println("A");
    }
    void hello(){
        System.out.println("A hello");
    }
    void invoke(){
        hello();
    }
}
class B extends Aa{
    B(){
        System.out.println("B");
    }
    void hello(){
        System.out.println("B hello");
    }
}
class C extends B{
    C(){
        super();
        System.out.println("C");
    }
}
public class a {
    public static void main(String[] args) {
        Aa a = new C();
        a.invoke();
    }
}
