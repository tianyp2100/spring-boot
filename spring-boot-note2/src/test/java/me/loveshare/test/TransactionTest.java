package me.loveshare.test;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.SpringBootNote2Application;
import me.loveshare.note2.data.entity.dbo.T1;
import me.loveshare.note2.data.entity.dbo.T2;
import me.loveshare.note2.data.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Tony on 2017/8/1.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootNote2Application.class)
public class TransactionTest {

    @Autowired
    private TestService testService;

    @Test
    public void testTransactionOK() {
        T2 t2 = new T2();
        t2.setAge((byte) 25);
        t2.setStar((byte) 10);
        T1 t1 = new T1();
        t1.setNames("卡卡");
        t1.setPhone("15212345678");
        log.info("\n***Execute resul:" + (testService.save(t1, t2) ? "successful." : "failure."));
    }

    @Test
    public void testTransactionNotOK() {
        T2 t2 = new T2();
        t2.setAge((byte) 15);
        t2.setStar((byte) 5);
        T1 t1 = new T1();
        t1.setNames("卡卡");
        t1.setPhone("15212345678111111111"); //varchar(11)
        log.info("\n***Execute resul:" + (testService.save(t1, t2) ? "successful." : "failure."));  //Caused by: com.mysql.jdbc.MysqlDataTruncation: Data truncation: Data too long for column 'phone' at row 1
    }
}
