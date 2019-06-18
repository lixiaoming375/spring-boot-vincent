package projectgoodquartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("projectgoodquartz.mapper")
public class QuartzGoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartzGoodApplication.class, args);
	}

}
