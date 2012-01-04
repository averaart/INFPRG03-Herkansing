package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Dao;
import data.Survey;

/**
 * Servlet implementation class PlaceholderLogInOutServlet
 */
@WebServlet("/PlaceholderLogInOutServlet")
public class PlaceholderLogInOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ref = request.getHeader("Referer");
		HttpSession session = request.getSession(true);
		
		if (session.getAttribute("user") == null){
			session.setAttribute("user", Dao.user(1));
		} else {
			session.removeAttribute("user");
		}
		
		response.sendRedirect(ref);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
