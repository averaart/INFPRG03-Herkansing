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
 * Servlet implementation class Login
 */
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/user_login.jsp");
		rD.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = "";
		String pass = "";

		if (request.getParameter("login") != null) {
			name = request.getParameter("username").trim();
			pass = request.getParameter("pass").trim();
			User user = Dao.user(name, pass);
			if(user != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/");
			}
			else
			{
				request.setAttribute("unknown", true);
				RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/user_login.jsp");
				rD.forward(request, response);
			}
		}

	}

}
