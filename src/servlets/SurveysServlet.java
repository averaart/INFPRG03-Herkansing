package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Dao;
import data.Survey;
import data.User;

public class SurveysServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6084354713108721181L;

	@Override
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			ArrayList<Survey> mySurveys = Dao.surveys(user);
			ArrayList<Survey> otherSurveys = Dao.surveys(user, true);
			
			request.setAttribute("mySurveys", mySurveys);
			request.setAttribute("otherSurveys", otherSurveys);
			log(otherSurveys.toString());
			
			request.setAttribute("title", "Alle enquetes van "+user.getName());

			RequestDispatcher rd = request.getRequestDispatcher("usersurveys.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		this.doGet(request, response);
	}

}