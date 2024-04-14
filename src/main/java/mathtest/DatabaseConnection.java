package mathtest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String jdbcUrl  = "jdbc:postgresql://localhost:5432/";
        String dbname = "top_academy";
        String username = "teacher";
        String password = getPassword(dbname, username);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl + dbname, username, password);
        } catch (SQLException sqle) {
            System.out.println("Ошибка подключения к базе данных: " + sqle.getMessage());
        }
        return connection;
    }

    private static String getPassword(String dbname, String username) {
        String pss;
        try {
            pss = readPgpass(dbname, username);
        } catch (IOException ioe) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Не удается прочитать файл pgpass. Пожалуйста, укажите пароль для пользователя db top_academy teacher:");
            pss = keyboard.nextLine();
            keyboard.close();
        }
        return pss;
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
}
