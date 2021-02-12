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


@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HelloServlet() {
		super();
		// TODO 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/hello-world.jsp";
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String poprawne_logowanie = "Tak";
		
		if (user == null || user.length() == 0) {
			url = "/login-user.jsp";
			request.setAttribute("error", "Nazwa u¿ytkownika nie mo¿e byæ pusta");
		}
		else {
			try {
				Database db = new Database();
				Boolean isHasloWygasniete = db.HasloWygasniete(user);
				String fullname = db.lookupFullname(user);
				String user_pass = db.lookupPassword(user);
				if (fullname == null || fullname.length() == 0) {

					url = "/login-user.jsp";
					request.setAttribute("error", "Podana nazwa u¿ytkownika nie istnieje w bazie danych");

				} 
				else {
					if (!db.BlokowanieKonta(user))
					{
						url = "/login-user.jsp";
						request.setAttribute("error",
								"Wyczerpano limit niepoprawnych logowañ. Konto zablokowane na 20 minut");
					}
					else {
						
					if (!password.contentEquals(user_pass)) {
						url = "/login-user.jsp";
						request.setAttribute("error", "Has³o nieprawid³owe");
						poprawne_logowanie = "Nie";
						db.insertLogowania(user, true, poprawne_logowanie);
					}
					else 
					{
						if (!isHasloWygasniete)
						{
							url = "/WygasniecieHasla.jsp";
							request.setAttribute("error", "Twoje has³o wygas³o musisz je zmieniæ");
						}
						else
						{
							request.setAttribute("error", "Logowanie zakoñczone sukcesem");
							db.insertLogowania(user, true, poprawne_logowanie);
							
							}
							

					request.setAttribute("user_pass", user_pass);
					request.setAttribute("fullname", fullname);
					}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}
