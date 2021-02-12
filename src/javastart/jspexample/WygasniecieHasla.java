package javastart.jspexample;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/WygasniecieHasla")
public class WygasniecieHasla extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WygasniecieHasla() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/WygasniecieHasla.jsp";
		String user = request.getParameter("user");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		if (!password1.contentEquals(password2)) {
			url = "/WygasniecieHasla.jsp";
			request.setAttribute("error", "Wprowadzone has³a nie s¹ identyczne, popraw je");
		} else {
			try {
				Database db = new Database();

				Boolean NewPassword = db.SprawdzHistorieHasel(user, password1);
				Boolean CanNoweHaslo = db.NoweHaslo(user);

				if (!CanNoweHaslo) {
					url = "/WygasniecieHasla.jsp";
					request.setAttribute("error", "Has³o by³o niedawno zmieniane, musisz odczekaæ jeden dzieñ");
				} 
				else {

					if (!WalidacjaHasla(password1)) {
						url = "/WygasniecieHasla.jsp";
						request.setAttribute("error", "Haslo musi zawieraæ wiêcej ni¿ 8 znaków /n "
								+ " ,ma³¹ oraz du¿¹ litere, cyfre i znak spcejalny");
					} 
					else {
						if (!NewPassword) {
							url = "/WygasniecieHasla.jsp";
							request.setAttribute("error", "Takie has³o ju¿ by³o u¿ywane, ustaw nowe");
						} 
						else {
							db.updatePassword(user, password1);
							db.insertPasswordHistory(user, password1);
							db.insertLogowania(user, true, "Tak");
							url = "/hello-world.jsp";
							request.setAttribute("error", "Has³o zmienione ");
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	public static boolean WalidacjaHasla(String password) {
		String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%]).{8,10}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}