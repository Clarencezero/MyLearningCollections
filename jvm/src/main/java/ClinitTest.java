class Parent {
    static int age = 1;
    static {
        age = 0;
        System.out.println("parent's age is : " + age);
    }

    public void sayAge() {
        System.out.println(age);
    }

}

class Son extends Parent {
    static {
        age = 2;
        System.out.println("Son's age is : " + age);
    }

    public void sayAge() {
        System.out.println(age);
    }
}

public class ClinitTest {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Son son = new Son();
        parent.sayAge();
        son.sayAge();
    }
}
