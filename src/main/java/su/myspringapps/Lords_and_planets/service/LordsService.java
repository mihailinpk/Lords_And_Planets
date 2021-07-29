package su.myspringapps.Lords_and_planets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.myspringapps.Lords_and_planets.entities.Lord;
import su.myspringapps.Lords_and_planets.enums.DatabaseAccessResult;
import su.myspringapps.Lords_and_planets.repository.LordsAndPlanetsRepository;

import java.util.List;

/**
 * Класс LordsService
 * <p/>
 * Сервис для работы с лордами
 * <p/>
 *
 * @author petr.mikhailin
 * created 23.07.2021 14:36
 */
@Service
public class LordsService {

    private final LordsAndPlanetsRepository lordsAndPlanetsRepository;

    @Autowired
    public LordsService(LordsAndPlanetsRepository lordsAndPlanetsRepository) {
        this.lordsAndPlanetsRepository = lordsAndPlanetsRepository;
    }

    /**
     * Добавить нового повелителя
     *
     * @param newLordName имя нового повелителя
     * @param newLordAge  возраст нового повелителя
     * @return результат операции
     */
    public DatabaseAccessResult addNewLord(String newLordName, int newLordAge) {
        try {
            if (lordsAndPlanetsRepository.checkIsLordInDatabase(newLordName)) {
                return DatabaseAccessResult.ALREADY_EXIST;
            }
            lordsAndPlanetsRepository.addNewLord(newLordName, newLordAge);
            return lordsAndPlanetsRepository.checkIsLordInDatabase(newLordName) ?
                    DatabaseAccessResult.SUCCESSFULLY : DatabaseAccessResult.WAS_NOT_ADDED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return DatabaseAccessResult.ERROR;
        }
    }

    /**
     * Расстрелять провинившегося повелителя
     *
     * @param lordName имя провинившегося повелителя
     * @return результат операции
     */
    public DatabaseAccessResult deleteLord(String lordName) {
        try {
            if (!lordsAndPlanetsRepository.checkIsLordInDatabase(lordName)) {
                return DatabaseAccessResult.DO_NOT_EXIST;
            }
            lordsAndPlanetsRepository.deleteLord(lordName);
            return !lordsAndPlanetsRepository.checkIsLordInDatabase(lordName) ?
                    DatabaseAccessResult.SUCCESSFULLY : DatabaseAccessResult.WAS_NOT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return DatabaseAccessResult.ERROR;
        }
    }

    /**
     * Найти всех повелителей-бездельников, которые прохлаждаются и не управляют никакими планетами
     *
     * @return список повелителей-бездельников
     */
    public List<Lord> getBumLordList() {
        return lordsAndPlanetsRepository.getAllLordsWithoutPlanets();
    }

    /**
     * Отобрать заданное количество самых молодых повелителей
     *
     * @param numOfYoungestLords количество самых молодых повелителей
     * @return список самых молодых повелителей
     */
    public List<Lord> getYoungestLords(int numOfYoungestLords) {
        return lordsAndPlanetsRepository.getGivenNumberOfLordsOrderByAgeList(numOfYoungestLords);
    }

}
