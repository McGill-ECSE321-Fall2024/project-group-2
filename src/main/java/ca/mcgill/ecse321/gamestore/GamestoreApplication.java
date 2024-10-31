package ca.mcgill.ecse321.gamestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;



@SpringBootApplication
public class GamestoreApplication {

	public static void main(String[] args) {

		SpringApplication.run(GamestoreApplication.class, args);
	}
}