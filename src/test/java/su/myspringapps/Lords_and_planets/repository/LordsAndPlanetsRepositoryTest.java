package su.myspringapps.Lords_and_planets.repository;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import su.myspringapps.Lords_and_planets.entities.Lord;
import su.myspringapps.Lords_and_planets.entities.Planet;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
@ContextConfiguration
class LordsAndPlanetsRepositoryTest {

    private final String testLordName1 = "Вася123";
    private final String testLordName2 = "Коля123";
    private final String testLordName3 = "Миша123";
    private final String testLordName4 = "Паша123";
    private final int testLordAge1 = 1;
    private final int testLordAge2 = 2;
    private final int testLordAge3 = 3;
    private final int testLordAge4 = 4;
    private final String testPlanetName = "Меркурий345";

    LordsAndPlanetsRepository repository;

    @Autowired
    LordsAndPlanetsRepositoryTest(LordsAndPlanetsRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setUp() {
        if (repository.checkIsLordInDatabase(testLordName1))    {
            repository.deleteLord(testLordName1);
        }
        repository.addNewLord(testLordName1, testLordAge1);
        if (repository.checkIsLordInDatabase(testLordName2))    {
            repository.deleteLord(testLordName2);
        }
        repository.addNewLord(testLordName2, testLordAge2);
        if (repository.checkIsLordInDatabase(testLordName3))    {
            repository.deleteLord(testLordName3);
        }
        repository.addNewLord(testLordName3, testLordAge3);
        if (repository.checkIsLordInDatabase(testLordName4))    {
            repository.deleteLord(testLordName4);
        }
        repository.addNewLord(testLordName4, testLordAge4);
        if (repository.checkIsPlanetInDatabase(testPlanetName))   {
            repository.deletePlanet(testPlanetName);
        }
        repository.addNewPlanet(testPlanetName);
    }

    @AfterEach
    public void tearDown()    {
        if (repository.checkIsLordInDatabase(testLordName1))    {
            repository.deleteLord(testLordName1);
        }
        if (repository.checkIsLordInDatabase(testLordName2))    {
            repository.deleteLord(testLordName2);
        }
        if (repository.checkIsLordInDatabase(testLordName3))    {
            repository.deleteLord(testLordName3);
        }
        if (repository.checkIsLordInDatabase(testLordName4))    {
            repository.deleteLord(testLordName4);
        }
        if (repository.checkIsPlanetInDatabase(testPlanetName)) {
            repository.deletePlanet(testPlanetName);
        }
    }

    @Test
    void checkIsLordInDatabase() {
        Assert.assertTrue(repository.checkIsLordInDatabase(testLordName1));
    }

    @Test
    void deleteLord() {
        repository.deleteLord(testLordName1);
        Assert.assertFalse(repository.checkIsLordInDatabase(testLordName1));
    }

    @Test
    void addNewLord() {
        repository.addNewLord(testLordName1, testLordAge1);
        Assert.assertTrue(repository.checkIsLordInDatabase(testLordName1));
    }

    @Test
    void getAllLordsWithoutPlanets() {
        List<Lord> testLordWithoutPlanetsList = repository.getAllLordsWithoutPlanets();
        Assert.assertTrue(testLordWithoutPlanetsList.contains(new Lord(testLordName1, testLordAge1)));
        Assert.assertTrue(testLordWithoutPlanetsList.contains(new Lord(testLordName2, testLordAge2)));
        Assert.assertTrue(testLordWithoutPlanetsList.contains(new Lord(testLordName3, testLordAge3)));
        Assert.assertTrue(testLordWithoutPlanetsList.contains(new Lord(testLordName4, testLordAge4)));
    }

    @Test
    void checkIsPlanetInDatabase() {
        Assert.assertTrue(repository.checkIsPlanetInDatabase(testPlanetName));
    }

    @Test
    void deletePlanet() {
        repository.deletePlanet(testPlanetName);
        Assert.assertFalse(repository.checkIsPlanetInDatabase(testPlanetName));
    }

    @Test
    void addNewPlanet() {
        repository.addNewPlanet(testPlanetName);
        Assert.assertTrue(repository.checkIsPlanetInDatabase(testPlanetName));
    }

    @Test
    void setRelationBetweenLordAndPlanet() {
        Lord lord = repository.getLordByName(testLordName1);
        Planet planet = repository.getPlanetByName(testPlanetName);
        repository.setRelationBetweenLordAndPlanet(lord, planet);
        lord = repository.getLordByName(testLordName1);
        planet = repository.getPlanetByName(testPlanetName);
        Iterator<Planet> iterator = lord.getPlanets().iterator();
        Assert.assertEquals(planet.getLord(), lord);
        boolean planetIsCorrect = false;
        while (iterator.hasNext())  {
            if (planet.equals(iterator.next()))   {
                planetIsCorrect = true;
            }
        }
        repository.deleteRelationBetweenLordAndPlanet(planet);
        Assert.assertTrue(planetIsCorrect);
    }

    @Test
    void deleteRelationBetweenLordAndPlanet() {
        setRelationBetweenLordAndPlanet();
        Lord lord = repository.getLordByName(testLordName1);
        Planet planet = repository.getPlanetByName(testPlanetName);
        repository.deleteRelationBetweenLordAndPlanet(planet);
        Assert.assertNotEquals(planet.getLord(), lord);
    }

    @Test
    void getLordByName() {
        Assert.assertEquals(testLordName1, repository.getLordByName(testLordName1).getName());
    }

    @Test
    void getPlanetByName() {
        Assert.assertEquals(testPlanetName, repository.getPlanetByName(testPlanetName).getName());
    }

    @Test
    void getGivenNumberOfLordsOrderByAgeList() {
        List<Lord> bumLordsList = repository.getGivenNumberOfLordsOrderByAgeList(4);
        Lord lord1 = repository.getLordByName(testLordName1);
        Lord lord2 = repository.getLordByName(testLordName2);
        Lord lord3 = repository.getLordByName(testLordName3);
        Lord lord4 = repository.getLordByName(testLordName4);
        Assert.assertTrue(bumLordsList.contains(lord1));
        Assert.assertTrue(bumLordsList.contains(lord2));
        Assert.assertTrue(bumLordsList.contains(lord3));
        Assert.assertTrue(bumLordsList.contains(lord4));
    }

}