package su.myspringapps.Lords_and_planets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import su.myspringapps.Lords_and_planets.service.LordsService;
import su.myspringapps.Lords_and_planets.service.PlanetsService;

@SpringBootApplication
public class LordsAndPlanetsApplication implements ApplicationRunner {

    private final LordsService lordsService;
    private final PlanetsService planetsService;

    @Autowired
    public LordsAndPlanetsApplication(LordsService lordsService, PlanetsService planetsService) {
        this.lordsService = lordsService;
        this.planetsService = planetsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LordsAndPlanetsApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        lordsService.addNewLord("Витя", 759);
        planetsService.addNewPlanet("Плутон");
        System.out.println(planetsService.appointLordToRulePlanet("Витя", "Плутон"));
    }

}