package su.myspringapps.Lords_and_planets.tools;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

/**
 * Класс SessionsAndTransactions
 * <p/>
 * Запуск/останов сессий и транзакций
 * <p/>
 *
 * @author petr.mikhailin
 * created 23.07.2021 15:35
 */
@Component
public class SessionsAndTransactions {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    /**
     * Открыть сессию и получить на нее ссылку
     *
     * @return открытая сессия
     */
    public Session openAndGetSession() {
        return sessionFactory.openSession();
    }

    /**
     * Открыть транзакцию и возвратить ссылку на сессию, в рамках которой транзакция и была открыта
     *
     * @return открытая сессия в рамках которой была открыта транзакция
     */
    public Session openTransactionAndGetSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    /**
     * Завершить транзакцию и закрыть сессию
     *
     * @param session открытая сессия с открытой транзакцией
     */
    public void commitTransactionAndCloseSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }

}