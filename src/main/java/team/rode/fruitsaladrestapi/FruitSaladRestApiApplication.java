package team.rode.fruitsaladrestapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FruitSaladRestApiApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });

        SpringApplication.run(FruitSaladRestApiApplication.class, args);
    }

}
