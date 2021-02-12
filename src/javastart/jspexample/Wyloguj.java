package javastart.jspexample;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Wyloguj")
public class Wyloguj extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Wyloguj() {
		super();
		// TODO 
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/login-user.jsp";
		String user = request.getParameter("user");
		String poprawne_logowanie = "Tak";
		try {
			Database db = new Database();
			db.insertLogowania(user, false, poprawne_logowanie);
			request.setAttribute("error", "U¿ytkownik zosta³ wylogowany");
			System.out.println(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}