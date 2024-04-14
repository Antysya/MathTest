package mathtest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Servlet implementation class Check
 */
public class Check extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Check() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры из запроса
        String user_answer = request.getParameter("answer");
        int questionId = Integer.parseInt(request.getParameter("questionId"));

        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                // Успешно подключено к базе данных
                StoreData store = new StoreData();
                Question q = store.getQuestion(questionId);
                PrintWriter writer = response.getWriter();
                store.saveResult(q, user_answer);
                writer.println("<h1>Your answer is " + user_answer + "; right is " + q.getAnswer() + "</h1>");
                writer.close();
                // Не забудьте закрыть соединение после использования
                connection.close();
            } else {
                // Не удалось подключиться к базе данных
                PrintWriter writer = response.getWriter();
                writer.println("<h1>Failed to connect to the database.</h1>");
                writer.close();
            }
        } catch (SQLException e) {
            // Ошибка при подключении к базе данных
            PrintWriter writer = response.getWriter();
            writer.println("<h1>Error connecting to the database: " + e.getMessage() + "</h1>");
            writer.close();
        }
    }
}

