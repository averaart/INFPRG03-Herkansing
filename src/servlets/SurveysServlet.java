package servlets;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import data.*;

public class SurveysServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6084354713108721181L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(true);		
		User user = (User)session.getAttribute("user");
		
		if (session.getAttribute("user") == null){
			ArrayList<Survey> surveys = Dao.surveys();
			request.setAttribute("surveys", surveys);
			request.setAttribute("title", "Alle enquetes");
			RequestDispatcher rd = request.getRequestDispatcher("surveys.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			ArrayList<Survey> surveys = Dao.surveys(user);
			request.setAttribute("surveys", surveys);
			request.setAttribute("title", "Alle enquetes van "+user.getName());
			RequestDispatcher rd = request.getRequestDispatcher("surveys.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		this.doGet(request, response);
	}

}