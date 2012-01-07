package servlets;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Dao;
import data.MultipleChoiceQuestion;
import data.Question;
import data.ScaleQuestion;
import data.Survey;

/**
 * Servlet implementation class RunQuestionnaire
 */
public class RunQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Pattern.matches("/(\\d+)/(\\d+)", request.getPathInfo())) {
			String[] s = Pattern.compile("/").split(request.getPathInfo());
			int surveyId = Integer.parseInt(s[1]);
			int questionNumber = Integer.parseInt(s[2])-1; //-1 because in database it is zero-based
			Survey survey = Dao.survey(surveyId);
			if(survey != null) {
				if(questionNumber >=0 && questionNumber <= survey.questions.size()) {
					Question question = survey.questions.get(questionNumber);
					if (question instanceof MultipleChoiceQuestion) {
						request.setAttribute("questionType", "option");
					} else if (question instanceof ScaleQuestion) {
						request.setAttribute("questionType", "scale");
					} else {
						request.setAttribute("questionType", "open");
					}
					
					request.setAttribute("questionNumber", questionNumber+1);
					request.setAttribute("question", question);
					request.setAttribute("survey", survey);
					request.setAttribute("hasPrev", (questionNumber > 0));
					request.setAttribute("hasNext", (questionNumber+1 < survey.questions.size()));
					RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/question.jsp");	
					rD.forward(request, response);
				}
				else
				{
					//de vraag bestaat niet
				}
			}
			else
			{
				//vragenlijst bestaat niet
			}
		}
		else if (Pattern.matches("/(\\d+)/afronden", request.getPathInfo())) {
			//afrondpagina, dus nog niet de afrondactie
		}
		else
		{
			//onbekende url pattern
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
