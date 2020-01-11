package interest;

import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
public class ForObjectReferenceTest {
    public static void main(String[] args) {
        Student student ;
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            student = new Student();
            student.setName("姜家俊");
            student.setAge(20 + i);
            list.add(student);
        }

        for (Student student1 : list) {
            System.out.println(student1.getName());
            System.out.println(student1.getAge());
        }
    }
}
