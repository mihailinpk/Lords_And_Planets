package su.myspringapps.Lords_and_planets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.myspringapps.Lords_and_planets.entities.Lord;
import su.myspringapps.Lords_and_planets.enums.DatabaseAccessResult;
import su.myspringapps.Lords_and_planets.service.LordsService;

import java.util.List;

/**
 * Класс LordsController
 * <p/>
 * Контроллер для работы с повелителями
 * <p/>
 *
 * @author petr.mikhailin
 * created 23.07.2021 14:39
 */
@RestController
public class LordsController {

    private final LordsService lordsService;

    @Autowired
    public LordsController(LordsService lordsService) {
        this.lordsService = lordsService;
    }

    /**
     * Обработка POST-запроса "/lords/add" (добавить нового повелителя)
     *
     * @param name имя нового повелителя
     * @param age  возраст нового повелителя
     * @return Http-статус плюс сообщение о результате операции
     */
    @PostMapping("/lords/add")
    public ResponseEntity<String> addNewLord(
            @RequestParam("name") String name,
            @RequestParam("age") int age
    ) {
        DatabaseAccessResult result;
        try {
            result = lordsService.addNewLord(name, age);
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
     * Обработка POST-запроса "/lords/delete" (расстрелять провинившегося повелителя)
     *
     * @param name имя провинившегося повелителя
     * @return Http-статус плюс сообщение о результате операции
     */
    @PostMapping("/lords/delete")
    public ResponseEntity<String> deleteLord(
            @RequestParam("name") String name
    ) {
        DatabaseAccessResult result;
        try {
            result = lordsService.deleteLord(name);
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
     * Обработка GET-запроса "/lords/getbumlords" (найти всех повелителей-бездельников, не управляющих никакими планетами)
     *
     * @return Http-статус плюс список повелителей-бездельников
     */
    @GetMapping("/lords/getbumlords")
    public ResponseEntity<List<Lord>> getBumLords() {
        try {
            List<Lord> bumLordsList = lordsService.getBumLordList();
            return new ResponseEntity<>(bumLordsList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обработка GET-запроса "/lords/getyoungestlords" (отобрать заданное количество самых молодых повелителей)
     *
     * @param num запрашиваемое количество самых молодых повелителей
     * @return Http-статус плюс список самых молодых повелителей
     */
    @GetMapping("/lords/getyoungestlords")
    public ResponseEntity<List<Lord>> getYoungestLords(
            @RequestParam int num
    ) {
        try {
            List<Lord> youngestLordsList = lordsService.getYoungestLords(num);
            return new ResponseEntity<>(youngestLordsList, HttpStatus.OK);
        } catch (Exception ex)  {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}