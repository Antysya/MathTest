package mathtest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class StoreData implements AutoCloseable {
    private final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    private final Metadata METADATA = new MetadataSources(REGISTRY).getMetadataBuilder().build();
    private final SessionFactory SESSION_FACTORY = METADATA.getSessionFactoryBuilder().build();
    private final Session SESSION = SESSION_FACTORY.openSession();
    private final Transaction TRANSACTION = SESSION.beginTransaction();

    public void saveQuestion(Question q) {
        save(q);
    }

    public void saveResult(Question q, String answer) {
        MathResult res = new MathResult (q, answer);
        save(res);
    }

    public Question getQuestion(int id) {
        return (Question)SESSION.get(Question.class, id);
    }

    public void save(Object o) {
        SESSION.persist(o);
        TRANSACTION.commit();
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        SESSION_FACTORY.close();
        SESSION.close();
    }
}