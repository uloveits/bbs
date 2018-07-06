package src.main.utweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UTApplication {

	public static void main(String[] args) {
		SpringApplication.run(UTApplication.class, args);
	}
}