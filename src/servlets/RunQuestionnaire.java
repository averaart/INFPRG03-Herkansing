package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Dao;
import data.MultipleChoiceQuestion;
import data.Question;
import data.ScaleQuestion;
import data.Survey;
import data.User;

/**
 * Servlet implementation class RunQuestionnaire
 */
public class RunQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		route(request, response, false);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		route(request, response, true);
	}

	/**
	 * Check if a user can get access to the requested page.
	 * @param request
	 * @param checkCompleted whether the survey should be tested on completion
	 * @param surveyId The survey's unique identifier
	 * @return whether access should be granted (true) or not (false)
	 */
	public boolean grantAccess(HttpServletRequest request, boolean checkCompleted, int surveyId) {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");

		/**
		 * Is the user logged in?
		 */
		if (user == null) {
			return false;
		}

		/*
		 * Is the user enrolled in the requested survey?
		 */
		ArrayList<Survey> surveys = user.surveys();
		Survey requestedSurvey = null;
		for (Survey s : surveys) {
			if (s.getId() == surveyId) {
				requestedSurvey = s;
				break;
			}
		}
		if (requestedSurvey == null) {
			return false;
		}

		/**
		 * In case of checkCompleted = true, is the survey not completed?
		 */
		if (checkCompleted) {
			if (requestedSurvey.getCompleted()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param surveyId The unique identifier of a survey
	 * @param userId The unique identifier of a user
	 * @param questionNumber the number of the question in a survey. The
	 *            questionNumber is not a unique identifier for a question.
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showQuestion(HttpServletRequest request, HttpServletResponse response, int surveyId, int userId, int questionNumber, String status) throws ServletException,
			IOException {

		Survey survey = Dao.survey(new User(userId), surveyId);
		if (questionNumber > 0 && survey.questions.size() >= questionNumber) {
			int questionId = survey.questions.get(questionNumber - 1).getId();
			Question question = Dao.question(questionId, userId);

			if (question instanceof MultipleChoiceQuestion) {
				request.setAttribute("questionType", "option");
			}
			else if (question instanceof ScaleQuestion) {
				request.setAttribute("questionType", "scale");
			}
			else {
				request.setAttribute("questionType", "open");
			}

			request.setAttribute("status", status);
			request.setAttribute("survey", survey);
			request.setAttribute("question", question);
			request.setAttribute("questionNumber", questionNumber);

			request.setAttribute("hasPrev", (questionNumber > 1));
			request.setAttribute("hasNext", (questionNumber + 0 < survey.questions.size()));

			RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/question.jsp");
			rD.forward(request, response);
		}
	}
	
	public void showCompletionPage(HttpServletRequest request, HttpServletResponse response, int surveyId, int userId) throws ServletException, IOException {
		Survey survey = Dao.survey(new User(userId), surveyId);
		ArrayList<Question> questions = survey.questions;
		ArrayList<Question> questionsAnswers = new ArrayList<Question>();
		boolean allowComplete = true;
		
		for(Question q : questions) {
			questionsAnswers.add(Dao.question(q.id, userId));
		}
		
		for(Question q : questionsAnswers) {
			if (q.getAnswer() == null || q.getAnswer().compareTo("") == 0) {
				allowComplete = false;
				break;
			}
		}
		
		request.setAttribute("survey", survey);
		request.setAttribute("allowComplete", allowComplete);
		request.setAttribute("questions", questionsAnswers);
		
		RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/complete_survey.jsp");
		rD.forward(request, response);		
	}
	
	public void route(HttpServletRequest request, HttpServletResponse response, boolean methodIsPost) throws ServletException, IOException {
		RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp");
		String pathInfo = request.getPathInfo();
		String[] parts;
		int surveyId = -1;
		int questionNumber = -1;
		int userId = -1;
		String status = "";

		/**
		 * Matches the pathInfo
		 */
		if (Pattern.matches("/(\\d+)/(\\d+)/?", pathInfo)) {
			parts = Pattern.compile("/").split(request.getPathInfo());
			surveyId = Integer.parseInt(parts[1]);
			questionNumber = Integer.parseInt(parts[2]);
		} else if(Pattern.matches("/(\\d+)/afronden/?", pathInfo)) {
			parts = Pattern.compile("/").split(request.getPathInfo());
			surveyId = Integer.parseInt(parts[1]);
		} else {
			status = "Geen geldige url.";
			request.setAttribute("status", status);
			rD.forward(request, response);
			return;
		}

		/**
		 * Must access be granted?
		 */
		if (!grantAccess(request, methodIsPost, surveyId)) {
			status = "U bent niet inlogd, bent niet ingeschreven voor de enquete, of probeert een bewerking toe te passen op een enquete die reeds afgerond is.";
			request.setAttribute("status", status);
			rD.forward(request, response);
			return;
		}
		else {
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("user");
			userId = user.id;
		}

		/**
		 * Route
		 */
		if (Pattern.matches("/(\\d+)/(\\d+)/?", pathInfo)) {
			if(methodIsPost) {
				if(!saveAnswer(request, response, questionNumber, userId, surveyId)){
					status = "Uw antwoord kon niet worden opgeslagen.";
				} 
				else {
					status = "Uw antwoord is opgeslagen.";
				}
			}
			showQuestion(request, response, surveyId, userId, questionNumber, status);
			return;
		}
		else if (Pattern.matches("/(\\d+)/afronden/?", pathInfo)) {
			Survey survey = Dao.survey(new User(userId), surveyId);
			if(methodIsPost) {
				Dao.completeSurvey(survey);
				status = "De enquete is afgerond. Uw antwoorden kunnen niet meer aangepast worden, maar u kunt ze nog wel bekijken.";
				showQuestion(request, response, surveyId, userId, 1, status);
				return;
			} 
			else {
				if(survey.getCompleted()) {
					status = "Deze pagina is niet meer te bereiken. U heeft de enquete al afgerond.";	
				}
				else {
					showCompletionPage(request, response, surveyId, userId);
					return;
				}
			}
		}
		
		request.setAttribute("status", status);
		rD.forward(request, response);
	}
	
	public boolean saveAnswer(HttpServletRequest request, HttpServletResponse response, int questionNumber, int userId, int surveyId) {
		Survey survey = Dao.survey(new User(userId), surveyId);
		
		if (questionNumber > 0 && survey.questions.size() >= questionNumber) {
			int questionId = survey.questions.get(questionNumber - 1).getId();
			Question question = Dao.question(questionId, userId);
			question.setUserId(userId);

			
			String comment = request.getParameter("comment");
			String answer = request.getParameter("answer");
			if(answer == null) {
				return false;
			}
			answer = answer.trim();
			if(answer.compareTo("") == 0) {
				return false;
			}
			if(comment == null) {
				comment = "";
			} 
			else {
				comment = comment.trim();
			}
			
			
			boolean answerChanged = (question.getAnswer().compareTo(answer) != 0);
			if(answerChanged) {
				question.setAnswer(answer);
			}
			
			boolean commentChanged = false;
			if (question instanceof MultipleChoiceQuestion) {
				if(((MultipleChoiceQuestion) question).getComment() != null) {
					if(((MultipleChoiceQuestion) question).getComment().compareTo(comment) != 0) {
						((MultipleChoiceQuestion) question).setComment(comment);
						commentChanged = true;
					}	
				}
				else {
					((MultipleChoiceQuestion) question).setComment(comment);
					commentChanged = true;
				}
				
			}
			else if (question instanceof ScaleQuestion) {
				comment = request.getParameter("comment").trim();
				if(((ScaleQuestion) question).getComment() != null) {
					if(((ScaleQuestion) question).getComment().compareTo(comment) != 0) {
						((ScaleQuestion) question).setComment(comment);
						commentChanged = true;
					}
				}
				else {
					((ScaleQuestion) question).setComment(comment);
					commentChanged = true;
				}
			}
			
			if(answerChanged || commentChanged) {
				if (question instanceof MultipleChoiceQuestion) {
					return Dao.storeMultipleChoiseAnswer(question);
				}
				else if(question instanceof ScaleQuestion) {
					return Dao.storeScaleAnswer(question);
				} 
				else{
					return Dao.storeAnswer(question); 
				}
			}
			else {
				return false;
			}
		}
		
		return false;
	}
}

