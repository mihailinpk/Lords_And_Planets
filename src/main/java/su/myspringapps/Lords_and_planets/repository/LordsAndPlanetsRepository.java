package su.myspringapps.Lords_and_planets.repository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import su.myspringapps.Lords_and_planets.entities.Lord;
import su.myspringapps.Lords_and_planets.entities.Planet;
import su.myspringapps.Lords_and_planets.tools.SessionsAndTransactions;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Класс LordsAndPlanetsRepository
 * <p/>
 * Репозиторий, взаимодействует напрямую с БД
 * <p/>
 * TODO: в последствии все взаимодействие с БД переести на HQL
 *
 * @author petr.mikhailin
 * created 23.07.2021 14:40
 */
@Repository
public class LordsAndPlanetsRepository {

    /**
     * SQL-запрос на получение всех повелителей, к которым не привязана ни одна планета
     */
    private final String SQL_QUERY_ALL_LORDS_WITHOUT_PLANETS =
            "SELECT * FROM lords WHERE NOT EXISTS(SELECT * FROM planets WHERE planets.id_lord=lords.id)";

    private final SessionsAndTransactions sessionsAndTransactions;

    @Autowired
    public LordsAndPlanetsRepository(SessionsAndTransactions sessionsAndTransactions) {
        this.sessionsAndTransactions = sessionsAndTransactions;
    }

    /**
     * Проверить наличие записи о повелителе в базе данных
     *
     * @param lordName имя проверяемого повелителя
     * @return наличие записи о повелителе
     */
    public boolean checkIsLordInDatabase(String lordName) {
        return getLordsListByName(lordName).size() > 0;
    }

    /**
     * Добавить нового повелителя в базу данных
     *
     * @param newLordName имя нового повелителя
     * @param newLordAge  возраст нового повелителя
     */
    public void addNewLord(String newLordName, int newLordAge) {
        Lord newLord = new Lord(newLordName, newLordAge);
        Session session = sessionsAndTransactions.openTransactionAndGetSession();
        session.saveOrUpdate(newLord);
        sessionsAndTransactions.commitTransactionAndCloseSession(session);
    }

    /**
     * Удалить повелителя из базы данных
     *
     * @param lordName имя удаляемого повелителя
     */
    public void deleteLord(String lordName) {
        Session session = sessionsAndTransactions.openTransactionAndGetSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<Lord> delete = criteriaBuilder.createCriteriaDelete(Lord.class);
        Root<Lord> root = delete.from(Lord.class);
        delete.where(criteriaBuilder.equal(root.get("name"), lordName));
        session.createQuery(delete).executeUpdate();
        sessionsAndTransactions.commitTransactionAndCloseSession(session);
    }

    /**
     * Проверить наличие записи о планете в базе данных
     *
     * @param planetName название проверяемой планеты
     * @return налилие записи о проверяемой планете
     */
    public boolean checkIsPlanetInDatabase(String planetName) {
        List<Planet> resultList = getPlanetsListByName(planetName);
        return resultList.size() > 0;
    }

    /**
     * Добавить новую планету в базу данных
     *
     * @param newPlanetName название новой планеты
     */
    public void addNewPlanet(String newPlanetName) {
        Planet newPlanet = new Planet(newPlanetName);
        Session session = sessionsAndTransactions.openTransactionAndGetSession();
        session.saveOrUpdate(newPlanet);
        sessionsAndTransactions.commitTransactionAndCloseSession(session);
    }

    /**
     * Удалить планету из базы данных
     *
     * @param planetName название планеты
     */
    public void deletePlanet(String planetName) {
        Session session = sessionsAndTransactions.openTransactionAndGetSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<Planet> delete = criteriaBuilder.createCriteriaDelete(Planet.class);
        Root<Planet> root = delete.from(Planet.class);
        delete.where(criteriaBuilder.equal(root.get("name"), planetName));
        session.createQuery(delete).executeUpdate();
        sessionsAndTransactions.commitTransactionAndCloseSession(session);
    }

    /**
     * Получить список повелителей, не управляющих ни одной планетой
     *
     * @return список повелителей без планет
     */
    public List<Lord> getAllLordsWithoutPlanets() {
        Session session = sessionsAndTransactions.openTransactionAndGetSession();
        List<Lord> resultList = session.createSQLQuery(SQL_QUERY_ALL_LORDS_WITHOUT_PLANETS)
                .addEntity(Lord.class).getResultList();
        sessionsAndTransactions.commitTransactionAndCloseSession(session);
        return resultList;
    }

    /**
     * Установить связь many-to-one между повелителем и планетой
     *
     * @param lord   повелитель
     * @param planet планета
     */
    public void setRelationBetweenLordAndPlanet(Lord lord, Planet planet) {
        Session session = sessionsAndTransactions.openTransactionAndGetSession();
        planet.setLord(lord);
        session.saveOrUpdate(planet);
        sessionsAndTransactions.commitTransactionAndCloseSession(session);
    }

    /**
     * Удалить связь many-to-one между повелителем и планетой
     *
     * @param planet планета
     */
    public void deleteRelationBetweenLordAndPlanet(Planet planet) {
        Session session = sessionsAndTransactions.openTransactionAndGetSession();
        planet.setLord(null);
        session.saveOrUpdate(planet);
        sessionsAndTransactions.commitTransactionAndCloseSession(session);
    }

    /**
     * Получить из БД повелителя по его имени
     *
     * @param lordName имя повелителя
     * @return искомый повелитель, либо null в случае отсутствия такового
     */
    public Lord getLordByName(String lordName) {
        List<Lord> resultList = getLordsListByName(lordName);
        return resultList.size() > 0 ? resultList.get(0) : null;
    }

    /**
     * Получить из БД планету по ее названию
     *
     * @param planetName название планеты
     * @return искомая планета, либо null в случае ее отсутствия
     */
    public Planet getPlanetByName(String planetName) {
        List<Planet> resultList = getPlanetsListByName(planetName);
        return resultList.size() > 0 ? resultList.get(0) : null;
    }

    /**
     * Получить список из заданного количества повелителей, отсортированный по возрасту оных
     *
     * @param givenNumber заданное количество
     * @return список повелителей
     */
    public List<Lord> getGivenNumberOfLordsOrderByAgeList(int givenNumber) {
        Session session = sessionsAndTransactions.openAndGetSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Lord> query = criteriaBuilder.createQuery(Lord.class);
        Root<Lord> root = query.from(Lord.class);
        query.select(root).orderBy(criteriaBuilder.asc(root.get("age")));
        List<Lord> resultList = session.createQuery(query).setMaxResults(givenNumber).getResultList();
        session.close();
        return resultList;
    }

    /**
     * Получить список повелителей, имеющих заданное имя
     *
     * @param lordName имя повелителя
     * @return список повелителей с заданным именем
     */
    private List<Lord> getLordsListByName(String lordName) {
        Session session = sessionsAndTransactions.openAndGetSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Lord> query = criteriaBuilder.createQuery(Lord.class);
        Root<Lord> root = query.from(Lord.class);
        query.select(root).where(criteriaBuilder.equal(root.get("name"), lordName));
        List<Lord> resultList = session.createQuery(query).getResultList();
        session.close();
        return resultList;
    }

    /**
     * Получить список планет, имеющих заданное название
     *
     * @param planetName название планеты
     * @return список планет с заданным названием
     */
    private List<Planet> getPlanetsListByName(String planetName) {
        Session session = sessionsAndTransactions.openAndGetSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Planet> query = criteriaBuilder.createQuery(Planet.class);
        Root<Planet> root = query.from(Planet.class);
        query.select(root).where(criteriaBuilder.equal(root.get("name"), planetName));
        List<Planet> resultList = session.createQuery(query).getResultList();
        session.close();
        return resultList;
    }

}