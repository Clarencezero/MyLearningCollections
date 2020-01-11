package bianchengzhimei;

import org.junit.Test;

import static org.junit.Assert.*;

public class N1zhongguoxiangqiTest {
    @Test
    public void testByte() {
        // 1001
        System.out.println(Integer.toBinaryString(9));
        System.out.println(Integer.toBinaryString(18));
    }

    /**
     * 与: 1-1->1,其余为0, 也就是我们可以通过与11110000或者00001111保留高位或者低位
     *  1.自己与还是本身 1001 & 1001 1001
     *
     * 或
     * 非
     */
    @Test
    public void and() {
        int a = 9;
        System.out.println(a & a);
    }

}