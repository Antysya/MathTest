package mathtest;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


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
        // TODO Auto-generated method stub
        StoreData store = new StoreData();
        String user_answer = request.getParameter("answer");
        Question q = store.getQuestion(
                Integer.parseInt(request.getParameter("questionId")));
        PrintWriter writer = response.getWriter();
        store.saveResult(q, user_answer);
        writer.println("<h1>Your answer is " + user_answer + "; right is " + q.getAnswer() + "</h1>");
        writer.close();
    }
}
