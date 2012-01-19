package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Dao;
import data.User;

/**
 * Servlet implementation class UserRegistration
 */
public class UserRegistration extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/user_registration.jsp");
		rD.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = "";
		String pass = "";
		String passCompare = "";
		RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/user_registration.jsp");

		if (request.getParameter("registrate") != null) {
			name = request.getParameter("username").trim();
			pass = request.getParameter("pass").trim();
			passCompare = request.getParameter("passCompare").trim();

			if (name.length() > 0 && pass.length() > 0 && pass.equals(passCompare)) {
				if (!Dao.userExists(name)) {
					User user = Dao.newUser(name, pass);
					HttpSession session = request.getSession(true);
					session.setAttribute("user", user);
					rD = request.getRequestDispatcher("/WEB-INF/pages/user_registration_succes.jsp");
				} else {
					request.setAttribute("userExists", true);
				}

			} else {
				request.setAttribute("invalidData", true);
			}
		}
		rD.forward(request, response);
	}
}
