package su.myspringapps.Lords_and_planets.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import su.myspringapps.Lords_and_planets.entities.Lord;
import su.myspringapps.Lords_and_planets.enums.DatabaseAccessResult;
import su.myspringapps.Lords_and_planets.repository.LordsAndPlanetsRepository;

import java.util.List;

@SpringBootTest
@ContextConfiguration
class LordsServiceTest {

    private final String testLordName1 = "Вася123";
    private final String testLordName2 = "Коля123";
    private final String testLordName3 = "Миша123";
    private final String testLordName4 = "Паша123";
    private final String testLordName5 = "Дима123";
    private final int testLordAge1 = 1;

    LordsService service;
    LordsAndPlanetsRepository repository;

    @Autowired
    public LordsServiceTest(LordsService service, LordsAndPlanetsRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        if (repository.checkIsLordInDatabase(testLordName1))    {
            repository.deleteLord(testLordName1);
        }
        repository.addNewLord(testLordName1, testLordAge1);
        if (repository.checkIsLordInDatabase(testLordName2))    {
            repository.deleteLord(testLordName2);
        }
        int testLordAge2 = 2;
        repository.addNewLord(testLordName2, testLordAge2);
        if (repository.checkIsLordInDatabase(testLordName3))    {
            repository.deleteLord(testLordName3);
        }
        int testLordAge3 = 3;
        repository.addNewLord(testLordName3, testLordAge3);
        if (repository.checkIsLordInDatabase(testLordName4))    {
            repository.deleteLord(testLordName4);
        }
        int testLordAge4 = 4;
        repository.addNewLord(testLordName4, testLordAge4);
    }

    @AfterEach
    void tearDown() {
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
        if (repository.checkIsLordInDatabase(testLordName5))    {
            repository.deleteLord(testLordName5);
        }
    }

    @Test
    void addNewLord() {
        Assert.assertEquals(DatabaseAccessResult.SUCCESSFULLY, service.addNewLord(testLordName5, testLordAge1));
    }

    @Test
    void deleteLord() {
        service.addNewLord(testLordName5, testLordAge1);
        Assert.assertEquals(DatabaseAccessResult.SUCCESSFULLY, service.deleteLord(testLordName5));
    }

    @Test
    void getBumLordList() {
        List<Lord> bumLordsList = service.getBumLordList();
        Lord lord1 = repository.getLordByName(testLordName1);
        Lord lord2 = repository.getLordByName(testLordName2);
        Lord lord3 = repository.getLordByName(testLordName3);
        Lord lord4 = repository.getLordByName(testLordName4);
        Assert.assertTrue(bumLordsList.contains(lord1));
        Assert.assertTrue(bumLordsList.contains(lord2));
        Assert.assertTrue(bumLordsList.contains(lord3));
        Assert.assertTrue(bumLordsList.contains(lord4));
    }

    @Test
    void getYoungestLords() {
        List<Lord> youngestLords = service.getYoungestLords(4);
        Lord lord1 = repository.getLordByName(testLordName1);
        Lord lord2 = repository.getLordByName(testLordName2);
        Lord lord3 = repository.getLordByName(testLordName3);
        Lord lord4 = repository.getLordByName(testLordName4);
        Assert.assertTrue(youngestLords.contains(lord1));
        Assert.assertTrue(youngestLords.contains(lord2));
        Assert.assertTrue(youngestLords.contains(lord3));
        Assert.assertTrue(youngestLords.contains(lord4));
    }

}