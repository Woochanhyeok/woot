package woo.woot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import woo.woot.domain.Member;

@SpringBootApplication
public class WootApplication {

	public static void main(String[] args) {
		SpringApplication.run(WootApplication.class, args);
	}

}
