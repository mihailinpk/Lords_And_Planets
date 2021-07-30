package su.myspringapps.Lords_and_planets;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LordsAndPlanetsApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(LordsAndPlanetsApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}