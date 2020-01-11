package kafka.learn;

import org.junit.Test;

// @RunWith(SpringRunner.class)
// @SpringBootTest
public class LearnApplicationTests {

    @Test
    public void contextLoads() {
        String st1 = new StringBuilder("hello").append("2").toString();
        System.out.println(st1 == st1.intern()); // true

        String st2 = "hello";
        System.out.println(st2 == st2.intern()); // true

        String str3 = new String("hello");
        System.out.println(str3 == str3.intern()); //false

        String str4 = new StringBuilder("ja").append("va").toString();
        System.out.println(str4 == str4); //true

    }

}
