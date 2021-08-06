package su.myspringapps.Lords_and_planets.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс DefaultController
 * <p/>
 * Контроллер для отображения страницы по-умолчанию
 * <p/>
 *
 * @author Михайлин Петр
 * created 06.08.2021 10:56
 */
@RestController
public class DefaultController {

    @GetMapping("/")
    public String getIndex()    {
        return "Here will be stay index page";
    }

}
