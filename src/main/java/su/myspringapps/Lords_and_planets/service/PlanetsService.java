package su.myspringapps.Lords_and_planets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.myspringapps.Lords_and_planets.entities.Lord;
import su.myspringapps.Lords_and_planets.entities.Planet;
import su.myspringapps.Lords_and_planets.enums.DatabaseAccessResult;
import su.myspringapps.Lords_and_planets.repository.LordsAndPlanetsRepository;

/**
 * Класс PlanetsService
 * <p/>
 * Сервис для работы с планетами
 * <p/>
 *
 * @author petr.mikhailin
 * created 23.07.2021 14:36
 */
@Service
public class PlanetsService {

    private final LordsAndPlanetsRepository lordsAndPlanetsRepository;

    @Autowired
    public PlanetsService(LordsAndPlanetsRepository lordsAndPlanetsRepository) {
        this.lordsAndPlanetsRepository = lordsAndPlanetsRepository;
    }

    /**
     * Добавить новую планету
     *
     * @param newPlanetName название новой планеты
     * @return результат операции
     */
    public DatabaseAccessResult addNewPlanet(String newPlanetName) {
        try {
            if (lordsAndPlanetsRepository.checkIsPlanetInDatabase(newPlanetName)) {
                return DatabaseAccessResult.ALREADY_EXIST;
            }
            lordsAndPlanetsRepository.addNewPlanet(newPlanetName);
            return lordsAndPlanetsRepository.checkIsPlanetInDatabase(newPlanetName) ?
                    DatabaseAccessResult.SUCCESSFULLY : DatabaseAccessResult.WAS_NOT_ADDED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return DatabaseAccessResult.ERROR;
        }
    }

    /**
     * Уничтожить планету
     *
     * @param planetName название планеты на уничтожение
     * @return результат операции
     */
    public DatabaseAccessResult deletePlanet(String planetName) {
        try {
            if (!lordsAndPlanetsRepository.checkIsPlanetInDatabase(planetName)) {
                return DatabaseAccessResult.DO_NOT_EXIST;
            }
            lordsAndPlanetsRepository.deletePlanet(planetName);
            return !lordsAndPlanetsRepository.checkIsPlanetInDatabase(planetName) ?
                    DatabaseAccessResult.SUCCESSFULLY : DatabaseAccessResult.WAS_NOT_DELETED;
        } catch (Exception ex) {
            ex.printStackTrace();
            return DatabaseAccessResult.ERROR;
        }
    }

    /**
     * Назначить повелителя управлять планетой
     *
     * @param lordName   имя повелителя
     * @param planetName название планеты
     * @return результат операции
     */
    public DatabaseAccessResult appointLordToRulePlanet(String lordName, String planetName) {
        try {
            Lord lord = lordsAndPlanetsRepository.getLordByName(lordName);
            Planet planet = lordsAndPlanetsRepository.getPlanetByName(planetName);
            if (lord == null || planet == null) {
                return DatabaseAccessResult.DO_NOT_EXIST;
            }
            if (planet.getLord() != null && planet.getLord().equals(lord)) {
                return DatabaseAccessResult.RELATION_EXIST;
            }
            lordsAndPlanetsRepository.setRelationBetweenLordAndPlanet(lord, planet);
            planet = lordsAndPlanetsRepository.getPlanetByName(planetName);
            if (planet.getLord().equals(lord)) {
                return DatabaseAccessResult.SUCCESSFULLY;
            } else {
                return DatabaseAccessResult.RELATION_WAS_NOT_ADDED;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return DatabaseAccessResult.ERROR;
        }
    }

    /**
     * Забрать у повелителя планету
     *
     * @param lordName   имя повелителя
     * @param planetName название планеты
     * @return результат операции
     */
    public DatabaseAccessResult takePlanetAwayFromLord(String lordName, String planetName) {
        Lord lord = lordsAndPlanetsRepository.getLordByName(lordName);
        Planet planet = lordsAndPlanetsRepository.getPlanetByName(planetName);
        if (planet == null || planet.getLord() == null || !planet.getLord().equals(lord))
            return DatabaseAccessResult.DO_NOT_EXIST;
        lordsAndPlanetsRepository.deleteRelationBetweenLordAndPlanet(planet);
        planet = lordsAndPlanetsRepository.getPlanetByName(planetName);
        if (planet.getLord() == null) {
            return DatabaseAccessResult.SUCCESSFULLY;
        } else {
            return DatabaseAccessResult.RELATION_WAS_NOT_DELETED;
        }
    }

}
