package kr.supergate.shoppingfolder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingfolderApplicationTests {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSqlSession() throws Exception {
        System.out.println(sqlSessionTemplate.toString());
    }

}
