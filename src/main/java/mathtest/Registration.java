package mathtest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * Servlet implementation class Registration
 */
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        RequestDispatcher dispatcher = null;
        PrintWriter writer = null;
        try {
            DatabaseConnection store = new DatabaseConnection();
            writer = response.getWriter();
            boolean isUserSaved = store.saveUser(firstName, lastName, password);
            dispatcher = request.getRequestDispatcher("register.jsp");
            if (isUserSaved) {
                request.setAttribute("status", "success");
                //writer.println("<h1>Пользователь " + firstName + " " + lastName + " успешно зарегистрирован</h1>");
            } else {
                request.setAttribute("status", "failed");
                //writer.println("<h1>Ошибка при регистрации пользователя " + firstName + " " + lastName + "</h1>");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}

