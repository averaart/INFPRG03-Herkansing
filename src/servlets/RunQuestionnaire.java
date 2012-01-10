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

		String pathInfo = request.getPathInfo();
		String[] parts;
		int surveyId = -1;
		int questionNumber = -1;
		int userId = -1;

		/**
		 * Matches the pathInfo with '/id/id/...'?
		 */
		if (Pattern.matches("/(\\d+)/(\\d+)/?.*", pathInfo)) {
			parts = Pattern.compile("/").split(request.getPathInfo());
			surveyId = Integer.parseInt(parts[1]);
			questionNumber = Integer.parseInt(parts[2]);

			/**
			 * Must access be granted?
			 */
			if (!grantAccess(request, false, surveyId)) {
				return; // TODO
			}
		}

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		userId = user.id;

		/**
		 * route
		 */
		if (Pattern.matches("/(\\d+)/(\\d+)/?", pathInfo)) {
			showQuestion(request, response, surveyId, userId, questionNumber);
		}
		else if (Pattern.matches("/(\\d+)/(\\d+)/afronden/?", pathInfo)) {
			// Completion page
		}
		else {
			// 404 - page not found
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// restrict
		// correct path pattern
		// User logged in
		// User enrolled in survey
		// Survey not completed

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		if (Pattern.matches("/(\\d+)/(\\d+)", request.getPathInfo()) && user != null) {

			String[] s = Pattern.compile("/").split(request.getPathInfo());
			int surveyId = Integer.parseInt(s[1]);
			int questionNumber = Integer.parseInt(s[2]) - 1;
			Survey survey = Dao.survey(surveyId);

			if (survey != null) {

				if (questionNumber >= 0 && questionNumber <= survey.questions.size()) {
					Question question = survey.questions.get(questionNumber);

					if (question instanceof MultipleChoiceQuestion) {

						String answer = request.getParameter("answer");
						String comment = request.getParameter("comment");

						question.setUserId(user.id);
						question.setAnswer(answer);
						((MultipleChoiceQuestion) question).setComment(comment);

						// ((MultipleChoiceQuestion)
						// question).setComment(comment);

					}
					else if (question instanceof ScaleQuestion) {
						// ScaleQuestion
					}
					else {
						// Open question
						request.setAttribute("questionType", "open");
					}
				}
			}
		}
	}

	/**
	 * 
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
	public void showQuestion(HttpServletRequest request, HttpServletResponse response, int surveyId, int userId, int questionNumber) throws ServletException,
			IOException {

//		Survey survey = Dao.survey(surveyId);
		Survey survey = Dao.survey(new User(userId), surveyId);
		int questionId = survey.questions.get(questionNumber - 1).getId();
		Question question = Dao.question(questionId, userId);

		if (question instanceof MultipleChoiceQuestion) {
			request.setAttribute("questionType", "option");
		}
		else if (question instanceof ScaleQuestion) {
			request.setAttribute("questionType", "scale");
			System.out.println("ANSWEr NULL?: " + question.getAnswer());
		}
		else {
			request.setAttribute("questionType", "open");
		}

		request.setAttribute("survey", survey);
		request.setAttribute("question", question);
		request.setAttribute("questionNumber", questionNumber);

		request.setAttribute("hasPrev", (questionNumber > 1));
		request.setAttribute("hasNext", (questionNumber + 0 < survey.questions.size()));

		RequestDispatcher rD = request.getRequestDispatcher("/WEB-INF/pages/question.jsp");
		rD.forward(request, response);
	}
}
