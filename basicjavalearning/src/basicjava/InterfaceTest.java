package basicjava;

class Student {
    private String name;
    private Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

interface InterfaceDefault{
    default Student getStudent() {
        return new Student("张汶沣", 18);
    }
}

public class InterfaceTest implements InterfaceDefault{
    @Override
    public Student getStudent() {
        return new Student("姜家俊", 25);
    }

    public static void main(String[] args) {
        InterfaceDefault inter = new InterfaceTest();
        Student student = inter.getStudent();
        System.out.println(student.getName());

    }
}





































