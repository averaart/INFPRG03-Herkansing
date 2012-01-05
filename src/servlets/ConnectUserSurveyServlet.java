package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.*;

/**
 * Servlet implementation class ConnectUserSurveyServlet
 */
@WebServlet("/ConnectUserSurveyServlet")
public class ConnectUserSurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ref = request.getHeader("Referer");
		HttpSession session = request.getSession(true);
		
		User user = (User)session.getAttribute("user");
		int surveyid = Integer.valueOf(request.getParameter("surveyid"));
		Survey survey = Dao.survey(surveyid);
		
		if ((user != null) && (survey != null)){
			Dao.connectUserToSurvey(user, survey);
		}
		
		response.sendRedirect(ref);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
