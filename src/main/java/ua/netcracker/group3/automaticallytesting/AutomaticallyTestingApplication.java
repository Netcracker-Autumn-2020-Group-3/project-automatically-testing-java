package ua.netcracker.group3.automaticallytesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ua.netcracker.group3.automaticallytesting.dao.MyDao;

@SpringBootApplication
public class AutomaticallyTestingApplication {

    public static void main(String[] args) {
        ApplicationContext run = SpringApplication.run(AutomaticallyTestingApplication.class, args);

        MyDao dao = run.getBean(MyDao.class);

        System.out.println(dao.method());
    }

}
