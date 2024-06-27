package online.dadazi.dadaziblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口
 * @author lqk
 */
@SpringBootApplication
@MapperScan("online.dadazi.dadaziblog.mapper")
public class DadaziBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(DadaziBlogApplication.class, args);
    }
}
