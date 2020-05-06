package life.myheart.community;

import life.myheart.community.utils.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("life.myheart.community.mapper")
@EnableScheduling
public class SensitiverFilterTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void test(){

String s = "跳楼我想死";
        String question1 = sensitiveFilter.filter(s);
        System.out.println("con"+question1);
    }
}
