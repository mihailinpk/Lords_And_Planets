package su.myspringapps.Lords_and_planets.enums;

/**
 * Перечисление DatabaseAccessResult
 * <p/>
 * Результаты обращения к базе данных
 * <p/>
 *
 * @author petr.mikhailin
 * created 28.07.2021 9:47
 */
public enum DatabaseAccessResult {
    /**
     * Успешно
     */
    SUCCESSFULLY,
    /**
     * Ошибка
     */
    ERROR,
    /**
     * Запись уже существует
     */
    ALREADY_EXIST,
    /**
     * Связь уже существует
     */
    RELATION_EXIST,
    /**
     * Такой записи нет
     */
    DO_NOT_EXIST,
    /**
     * Запись не была добавлена
     */
    WAS_NOT_ADDED,
    /**
     * Запись не была удалена
     */
    WAS_NOT_DELETED,
    /**
     * Связь не была добавлена
     */
    RELATION_WAS_NOT_ADDED,
    /**
     * Связь не была удалена
     */
    RELATION_WAS_NOT_DELETED;

    @Override
    public String toString() {
        return String.valueOf(this.ordinal());
    }
}
