package hibernateclasses;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

/**
 * Created by Home on 02.03.15.
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // метод создает сессию на основе конфигурации, указанной в файле hibernate.cfg.xml
            return new AnnotationConfiguration().configure(
                    new File("src\\main\\resources\\hibernate.cfg.xml")).buildSessionFactory();

        } catch (Throwable ex) {
            // Записываем исключение
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        // закрываем текущую сессию
        getSessionFactory().getCurrentSession().close();
    }
}
