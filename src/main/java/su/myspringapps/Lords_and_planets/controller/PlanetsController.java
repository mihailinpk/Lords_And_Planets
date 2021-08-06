package su.myspringapps.Lords_and_planets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.myspringapps.Lords_and_planets.enums.DatabaseAccessResult;
import su.myspringapps.Lords_and_planets.service.PlanetsService;

/**
 * Класс PlanetsController
 * <p/>
 * Контроллер для работы с планетами
 * <p/>
 *
 * @author petr.mikhailin
 * created 23.07.2021 14:39
 */
@RestController
public class PlanetsController {

    private final PlanetsService planetsService;

    @Autowired
    public PlanetsController(PlanetsService planetsService) {
        this.planetsService = planetsService;
    }

    /**
     * Обработка POST-запроса "/planets/add" (добавить новую планету)
     *
     * @param name название новой планеты
     * @return Http-статус плюс сообщение о результате операции
     */
    @PostMapping("/planets/add")
    public ResponseEntity<String> addNewPlanet(
            @RequestParam("name") String name
    ) {
        DatabaseAccessResult result;
        try {
            result = planetsService.addNewPlanet(name);
            switch (result.ordinal()) {
                case 0 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.OK);
                }
                case 1, 2, 5 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            result = DatabaseAccessResult.OTHER_ERROR;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            result = DatabaseAccessResult.EXCEPTION;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обработка POST-запроса "/planets/delete" (уничтожить планету)
     *
     * @param name название планеты на уничтожение
     * @return Http-статус плюс сообщение о результате операции
     */
    @PostMapping("/planets/delete")
    public ResponseEntity<String> deletePlanet(
            @RequestParam("name") String name
    ) {
        DatabaseAccessResult result;
        try {
            result = planetsService.deletePlanet(name);
            switch (result.ordinal()) {
                case 0 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.OK);
                }
                case 1, 4, 6 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

                }
            }
            result = DatabaseAccessResult.OTHER_ERROR;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            result = DatabaseAccessResult.EXCEPTION;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обработка POST-запроса "/planets/appoint" (забрать у повелителя планету)
     *
     * @param lordName   имя повелителя
     * @param planetName название планеты
     * @return Http-статус плюс сообщение о результате операции
     */
    @PostMapping("/planets/appoint")
    public ResponseEntity<String> appointLordToRulePlanet(
            @RequestParam("lordname") String lordName,
            @RequestParam("planetname") String planetName
    ) {
        DatabaseAccessResult result;
        try {
            result = planetsService.appointLordToRulePlanet(lordName, planetName);
            switch (result.ordinal()) {
                case 0 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.OK);
                }
                case 1, 3, 4, 7 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            result = DatabaseAccessResult.OTHER_ERROR;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            result = DatabaseAccessResult.EXCEPTION;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обработка POST-запроса "/planets/take" (забрать у повелителя планету)
     *
     * @param lordName   имя повелителя
     * @param planetName название планеты
     * @return Http-статус плюс сообщение о результате операции
     */
    @PostMapping("/planets/take")
    public ResponseEntity<String> takePlanetAwayFromLord(
            @RequestParam("lordname") String lordName,
            @RequestParam("planetname") String planetName
    ) {
        DatabaseAccessResult result;
        try {
            result = planetsService.takePlanetAwayFromLord(lordName, planetName);
            switch (result.ordinal()) {
                case 0 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.OK);
                }
                case 1, 8 -> {
                    return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            result = DatabaseAccessResult.OTHER_ERROR;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            result = DatabaseAccessResult.EXCEPTION;
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
