package su.myspringapps.Lords_and_planets.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import su.myspringapps.Lords_and_planets.enums.DatabaseAccessResult;
import su.myspringapps.Lords_and_planets.repository.LordsAndPlanetsRepository;

@SpringBootTest
@ContextConfiguration
class PlanetsServiceTest {

    private final String testLordName = "Вася123";
    private final String testPlanetName = "Меркурий345";

    PlanetsService planetsService;
    LordsService lordsService;

    LordsAndPlanetsRepository repository;

    @Autowired
    public PlanetsServiceTest(PlanetsService planetsService, LordsService lordsService, LordsAndPlanetsRepository repository) {
        this.planetsService = planetsService;
        this.lordsService = lordsService;
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        if (repository.checkIsPlanetInDatabase(testPlanetName)) {
            repository.deletePlanet(testPlanetName);
        }
        repository.addNewPlanet(testPlanetName);
        if (repository.checkIsLordInDatabase(testLordName)) {
            repository.deleteLord(testLordName);
        }
        int testLordAge = 1;
        repository.addNewLord(testLordName, testLordAge);
    }

    @AfterEach
    void tearDown() {
        if (repository.checkIsPlanetInDatabase(testPlanetName)) {
            planetsService.deletePlanet(testPlanetName);
        }
        if (repository.checkIsLordInDatabase(testLordName)) {
            lordsService.deleteLord(testLordName);
        }
    }

    @Test
    void addNewPlanet() {
        if (repository.checkIsPlanetInDatabase(testPlanetName)) {
            planetsService.deletePlanet(testPlanetName);
        }
        Assert.assertEquals(DatabaseAccessResult.SUCCESSFULLY, planetsService.addNewPlanet(testPlanetName));
    }

    @Test
    void deletePlanet() {
        if (!repository.checkIsPlanetInDatabase(testPlanetName)) {
            planetsService.addNewPlanet(testPlanetName);
        }
        Assert.assertEquals(DatabaseAccessResult.SUCCESSFULLY, planetsService.deletePlanet(testPlanetName));
    }

    @Test
    void appointLordToRulePlanet() {
        if (!repository.checkIsPlanetInDatabase(testPlanetName)) {
            repository.addNewPlanet(testPlanetName);
        }
        repository.deleteRelationBetweenLordAndPlanet(repository.getPlanetByName(testPlanetName));
        Assert.assertEquals(
                DatabaseAccessResult.SUCCESSFULLY, planetsService.appointLordToRulePlanet(testLordName, testPlanetName)
        );
    }

    @Test
    void takePlanetAwayFromLord() {
        if (!repository.checkIsPlanetInDatabase(testPlanetName)) {
            repository.addNewPlanet(testPlanetName);
        }
        repository.setRelationBetweenLordAndPlanet(
                repository.getLordByName(testLordName), repository.getPlanetByName(testPlanetName)
        );
        Assert.assertEquals(
                DatabaseAccessResult.SUCCESSFULLY, planetsService.takePlanetAwayFromLord(testLordName, testPlanetName)
        );
    }
}