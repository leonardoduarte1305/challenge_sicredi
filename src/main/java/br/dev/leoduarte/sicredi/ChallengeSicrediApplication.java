package br.dev.leoduarte.sicredi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ChallengeSicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeSicrediApplication.class, args);
	}

}
