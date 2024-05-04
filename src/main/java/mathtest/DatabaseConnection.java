package mathtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class DatabaseConnection {
    private static final Logger consolelog = LogManager.getLogger(DatabaseConnection.class);
    private Properties prop = new Properties();
    private StandardServiceRegistry ssr;
    private Metadata meta;
    private SessionFactory factory;

    public DatabaseConnection(){
        String pss;
        //consolelog.error("\n\n\n\nWriting to console.");
        try {
            pss = readPgpass("top_academy", "teacher");
            consolelog.info("База данных подключена.");
        }  catch (IOException ioe) {
            consolelog.info("Ошибка при считывании пароля.");
            pss = "";
        }
        prop.setProperty("hibernate.connection.password", pss);
        ssr = new StandardServiceRegistryBuilder(
        ).configure("hibernate.cfg.xml").applySettings(prop).build();
        meta = new MetadataSources(ssr).getMetadataBuilder().build();
        factory = meta.getSessionFactoryBuilder().build();
    }

    private static String readPgpass(String dbname, String username) throws IOException {
        Path filePath = Paths.get(
                System.getenv("APPDATA"),
                "postgresql",
                "pgpass.conf");
        // Open file
        String pss = "";
        String line = "";
        String[] parts;
        System.out.println(filePath.toString());
        File file = new File(filePath.toString());
        Scanner inputFile = new Scanner(file);
        // Read lines from the file until no more are left.
        while (inputFile.hasNext() && pss == "") {
            line = inputFile.nextLine();
            // hostname:port:dbname:username:password
            // 0        1    2      3        4
            System.out.println(line);
            parts = line.split(":");
            if (parts.length >= 5 && parts[2].equals(dbname) && parts[3].equals(username)) {
                pss = parts[4].trim();  // deleting end of line
                System.out.println(pss);
            }
        }
        inputFile.close();
        return pss;
    }

    public void saveResult(Question q, String answer) {
        MathResult res = new MathResult(q, answer);
        save(res);
    }

    public boolean saveUser(String fn, String ln, String p) {
        Users res = new Users(fn, ln, p);
        return save(res);
    }

    public Question getQuestion(int id) {
        return (Question) findIt(Question.class, id);
    }

    public Object findIt(Class cls, int id) {
        Session session = factory.openSession();
        Object result = session.get(cls, id);
        session.close();
        //factory.close();
        return result;
    }

    public boolean save(Object o) {
        try (Session session = factory.openSession()) {
            try {
                Transaction t = session.beginTransaction();
                session.persist(o);
                t.commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public void save(Object o) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(o);
        t.commit();
        session.close();
        //factory.close();

    }*/

   // public Users getUser(String cookies) {
   //     int id = 1;  // get from cookies
   //     return (Users) findIt(Users.class, id);
   // }
}
