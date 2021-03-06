package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dao {
	static Connection con = null;
	static Statement st = null;

	static String url = "jdbc:mysql://localhost:3306/infprg03";
	static String user = "root";
	static String password = "root";

	public static void main(String[] args) {

		init();

		// System.out.println(validateUser("andra", "maarten"));

		// User user = user("andra", "andra");
		//
		// if (user!=null){
		// System.out.println(user.toString());
		// } else {
		// System.out.println("Deze combinatie van naam en wachtwoord is niet bij ons bekend...");
		// }

		// User user = newUser("klaas", "klaas");
		//
		// if (user != null) {
		// System.out.println(user.toString());
		// } else {
		// System.out
		// .println("Deze combinatie van naam en wachtwoord bestaat al!!!");
		// }

		// Survey survey = survey(2);
		// System.out.println(survey.toString());

		// ArrayList<Survey> surveys = surveys();
		// for (int i=0; i < surveys.size(); i++){
		// System.out.println(surveys.get(i).toString());
		// }

		// ArrayList<Survey> surveys = surveys(user("andra", "andra"));
		// System.out.println(surveys.size());
		// for (int i=0; i < surveys.size(); i++){
		// System.out.println(surveys.get(i).toString());
		// }

		// ArrayList<User> users = users(survey(2));
		// for (int i=0; i < users.size(); i++){
		// System.out.println(users.get(i).toString());
		// }

		// Question question = question(1, 2);
		// System.out.println(question.toString());

		// ArrayList<Question> questions = questions(1);
		// for (int i=0; i < questions.size(); i++){
		// System.out.println(questions.get(i).toString());
		// }

//		ArrayList<Survey> surveys = surveys(user(1), true);
//		System.out.println(surveys.size());

		// connectUserToSurvey(user(1), survey(3));
		// disconnectUserFromSurvey(user(1), survey(2));
		// disconnectUserFromSurvey(user(1), survey(3));

//		close();
		
//		MultipleChoiceQuestion question = new MultipleChoiceQuestion(1,1,1);
//		
//		System.out.println(question.getAverage());

	}

	/*
	 * User
	 */

	/**
	 * Fetches a User from the database, based on username and password.
	 * 
	 * @param name The name with which the User is registered
	 * @param password The password that was used during registration
	 * @return The User
	 */
	public static User user(String name, String password) {
		PreparedStatement getUser;
		User user = null;
		
		try {
			Dao.makeConnection();
			
			String qUser = "SELECT id, name, password FROM user WHERE name = ? AND password = ?";
			getUser = con.prepareStatement(qUser);
			getUser.setString(1, name);
			getUser.setString(2, password);
			
			ResultSet rs = getUser.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
			rs.close();
			getUser.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} 
		finally {
			Dao.close();
		}
		return user;
	}

	/**
	 * Fetches a User from the database, based on his/her id
	 * 
	 * @param id The unique identifier for this particular User
	 * @return The User
	 */
	public static User user(int id) {
		PreparedStatement getUser;
		User user = null;
		
		try {
			Dao.makeConnection();
			
			String qUser = "SELECT id, name, password FROM user WHERE id ?";
			getUser = con.prepareStatement(qUser);
			getUser.setInt(1, id);
			
			ResultSet rs = getUser.executeQuery();		
			while (rs.next()) {
				user = new User(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
			rs.close();
			getUser.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		return user;
	}

	/**
	 * Checks if a username already exists.
	 * 
	 * @param name The username to be checked
	 * @return If the username exist. True if it exists, False if is doesn't
	 *         exist.
	 */
	public static boolean userExists(String name) {
		boolean result = false;
		String q = "SELECT id FROM user WHERE name= ?";
		PreparedStatement stmt;
		Dao.makeConnection();
		
		try {
			stmt = con.prepareStatement(q);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			result = rs.next();
			stmt.close();	
			rs.close();
		}
		catch (SQLException e) {
			result = true;
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		finally {
			Dao.close();
		}
		return result;
	}

	/**
	 * Adds a new User to the database. The unique identifier will automatically
	 * be generated.
	 * 
	 * @param name The requested username
	 * @param password The requested password
	 * @return The new User. NULL if the user name/password already exists.
	 */
	public static User newUser(String name, String password) {

		User user = user(name, password);
		if (user != null) {
			return null;
		}

		PreparedStatement insertUser;
		String qInsterUser = "INSERT INTO user(name, password) VALUES(?, ?)";
		
		try {
			Dao.makeConnection();
			insertUser = con.prepareStatement(qInsterUser);
			insertUser.setString(1, name);
			insertUser.setString(2, password);
			insertUser.execute();
			insertUser.close();
		}
		catch(SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		finally {
			Dao.close();
		}
		
		user = user(name, password);
		return user;
	}

	/**
	 * Checks if a combination of username and password exists in the database
	 * or not.
	 * 
	 * @param name The username
	 * @param password The user's password
	 * @return TRUE if the combination exists.
	 */
	public static boolean validateUser(String name, String password) {

		boolean result = false;
		ResultSet rs = query("SELECT id FROM user WHERE name='" + name + "' AND password='" + password + "'");

		try {
			while (rs.next()) {
				result = true;
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} 
		finally {
			Dao.close();
		}
		return result;
	}

	/*
	 * Survey
	 */

	/**
	 * Fetches a Survey from the database, including its questions, based its
	 * id.
	 * 
	 * @param id The Survey's unique identifier
	 * @return The requested Survey
	 */
	public static Survey survey(int id) {
		Survey survey = null;
		ResultSet rs = query("SELECT id, title FROM survey WHERE id=" + id);

		try {
			while (rs.next()) {
				survey = new Survey(rs.getInt(1));
				survey.setTitle(rs.getString(2));
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		
		if (survey != null) {
			survey.questions = questions(survey.id);
		}

		return survey;
	}

	/**
	 * Fetches all Surveys in the database, including their questions.
	 * 
	 * @return The Surveys
	 */
	public static ArrayList<Survey> surveys() {
		ArrayList<Survey> surveys = new ArrayList<Survey>();
		Survey survey = null;
		ResultSet rs = query("SELECT survey.id, survey.title, SUM(user_survey.completed) AS completed FROM survey JOIN user_survey on survey_id = survey.id GROUP BY survey_id");

		try {
			while (rs.next()) {
				survey = new Survey(rs.getInt(1));
				survey.setTitle(rs.getString(2));
				survey.setCorrespondents(rs.getInt(3));
				surveys.add(survey);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		
		for (Survey s : surveys) {
			s.questions = questions(survey.id);
		}
		
		return surveys;
	}

	/*
	 * User / Survey relationship
	 */

	/**
	 * Registers a connection between a User and a Survey
	 * 
	 * @param user
	 * @param survey
	 */
	public static void connectUserToSurvey(User user, Survey survey) {
		ResultSet s = query("INSERT INTO user_survey(user_id, survey_id) VALUES (" + user.id + ", " + survey.id + ")");
		Dao.close();	
	}

	public static void disconnectUserFromSurvey(User user, Survey survey) {
		query("DELETE FROM user_survey " + "WHERE user_id=" + user.id + " AND survey_id=" + survey.id + " AND completed = 0");
		Dao.close();	
	}

	/**
	 * Fetches the surveys with surveyId and that is connected to a particular
	 * User
	 * 
	 * @param user
	 * @return
	 */
	public static Survey survey(User user, int surveyId) {
		Survey survey = null;
		ResultSet rs = query("SELECT survey.id, title, completed, question_pointer " + "FROM user " + "JOIN user_survey " + "ON user.id = user_survey.user_id " + "JOIN survey "
				+ "ON user_survey.survey_id = survey.id " + "WHERE user.id = " + user.id + " AND survey.id = " + surveyId);
		try {
			if (rs.next()) {
				survey = new Survey(rs.getInt(1));
				survey.setTitle(rs.getString(2));
				survey.setQuestionPointer(rs.getInt(4));
				survey.user = user;
				if (rs.getInt(3) == 1)
					survey.setCompleted();
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		
		survey.questions = questions(survey.id);
		return survey;
	}

	/**
	 * Fetches all Surveys that are connected to a particular User
	 * 
	 * @param user
	 * @return
	 */
	public static ArrayList<Survey> surveys(User user) {
		ArrayList<Survey> surveys = new ArrayList<Survey>();
		Survey survey = null;
		ResultSet rs = query("SELECT survey.id, title, completed, question_pointer " + "FROM user " + "JOIN user_survey " + "ON user.id = user_survey.user_id " + "JOIN survey "
				+ "ON user_survey.survey_id = survey.id " + "WHERE user.id = " + user.id);
		try {
			while (rs.next()) {
				survey = new Survey(rs.getInt(1));
				survey.setTitle(rs.getString(2));
				survey.setQuestionPointer(rs.getInt(4));
				if (rs.getInt(3) == 1)
					survey.setCompleted();
				surveys.add(survey);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		
		return surveys;
	}

	/**
	 * Fetches all Surveys that are NOT connected to a particular User
	 * 
	 * @param user
	 * @param other Indicates the reversal of surveys(user)
	 * @return
	 */
	public static ArrayList<Survey> surveys(User user, boolean other) {
		ArrayList<Survey> surveys = new ArrayList<Survey>();
		Survey survey = null;
		ResultSet rs = query("SELECT id, title " + "FROM survey " + "WHERE survey.id NOT IN (SELECT survey.id " + "FROM user " + "JOIN user_survey "
				+ "ON user.id = user_survey.user_id " + "JOIN survey " + "ON user_survey.survey_id = survey.id " + "WHERE user.id = " + user.id + ")");
		try {
			while (rs.next()) {
				survey = new Survey(rs.getInt(1));
				survey.setTitle(rs.getString(2));
				surveys.add(survey);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		return surveys;
	}

	public static ArrayList<Survey> surveysStats(User user) {
		
		String q = "SELECT survey.id, survey.title, SUM(user_survey.completed) AS completed FROM survey JOIN user_survey on survey_id = survey.id GROUP BY survey_id";
		Dao.close();
		
		return null;
	}
	
	/**
	 * Fetches all Users that are connected to a particular Survey
	 * 
	 * @param survey
	 * @return
	 */
	public static ArrayList<User> users(Survey survey) {
		ArrayList<User> users = new ArrayList<User>();
		User user = null;
		ResultSet rs = query("SELECT user.id, name, password " + "FROM user " + "JOIN user_survey " + "ON user.id = user_survey.user_id " + "JOIN survey "
				+ "ON user_survey.survey_id = survey.id " + "WHERE survey.id = " + survey.id);
		try {
			while (rs.next()) {
				user = new User(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				users.add(user);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}

		return users;
	}

	/*
	 * Question
	 */

	/**
	 * Fetches a Question from the database, based on the unique identifier of
	 * the question. In cases of multiple choice- or scale-questions, the
	 * appropriate subclasses will be returned.
	 * 
	 * @param id
	 * @return
	 */
	public static Question question(int id) {
		ResultSet rs = query("SELECT question.id, question.survey_id, question.text, scale.count, scale.low, scale.high, option.text " + "FROM question "
				+ "LEFT OUTER JOIN scale " + "ON question.id = scale.question_id " + "LEFT OUTER JOIN `option` " + "ON question.id = option.question_id "
				+ "WHERE question.id = " + id + " " + "ORDER BY option.id");

		try {
			if (rs.next()) {
				rs.getInt(4);
				boolean scale = (!rs.wasNull());
				rs.getString(7);
				boolean multi = (!rs.wasNull());
				if (scale) {
					ScaleQuestion question = new ScaleQuestion(rs.getInt(1), rs.getInt(2));
					question.setRange(rs.getInt(4));
					question.setLowText(rs.getString(5));
					question.setHighText(rs.getString(6));
					question.setText(rs.getString(3));
					rs.close();
					return question;
				}
				else if (multi) {
					MultipleChoiceQuestion question = new MultipleChoiceQuestion(rs.getInt(1), rs.getInt(2));
					do {
						question.addOption(rs.getString(7));
					}
					while (rs.next());
					rs.first();
					question.setText(rs.getString(3));
					rs.close();
					return question;
				}
				else {
					Question question = new Question(rs.getInt(1), rs.getInt(2));
					question.setText(rs.getString(3));
					rs.close();
					return question;
				}
			}
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		return null;
	}

	/**
	 * Fetches a Question and its answer from the database, based on the unique
	 * identifier of the question. In cases of multiple choice- or
	 * scale-questions, the appropriate subclasses will be returned. Answers are
	 * based on the userId.
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public static Question question(int id, int userId) {
		ResultSet rs = query("SELECT question.id, question.survey_id, question.text, scale.count, scale.low, scale.high, option.text " + "FROM question "
				+ "LEFT OUTER JOIN scale " + "ON question.id = scale.question_id " + "LEFT OUTER JOIN `option` " + "ON question.id = option.question_id "
				+ "WHERE question.id = " + id + " " + "ORDER BY option.id");

		Question question = null;
		boolean scale = false;
		boolean multi = false;
		
		try {
			if (rs.next()) {
				rs.getInt(4);
				scale = (!rs.wasNull());
				rs.getString(7);
				multi = (!rs.wasNull());
				if (scale) {
					question = new ScaleQuestion(rs.getInt(1), rs.getInt(2), userId);
					((ScaleQuestion) question).setRange(rs.getInt(4));
					((ScaleQuestion) question).setLowText(rs.getString(5));
					((ScaleQuestion) question).setHighText(rs.getString(6));
					((ScaleQuestion) question).setText(rs.getString(3));
				}
				else if (multi) {
					question = new MultipleChoiceQuestion(rs.getInt(1), rs.getInt(2), userId);
					do {
						((MultipleChoiceQuestion) question).addOption(rs.getString(7));
					}
					while (rs.next());
					rs.first();
					question.setText(rs.getString(3));
				}
				else {
					question = new Question(rs.getInt(1), rs.getInt(2), userId);
					question.setText(rs.getString(3));
				}
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		
		if(scale) {
			((ScaleQuestion) question).retrieveAnswer();
			((ScaleQuestion) question).setAverage();
		}
		else if (multi) {
			((MultipleChoiceQuestion) question).retrieveAnswer();
			((MultipleChoiceQuestion) question).setAverage();
		} else {
			question.retrieveAnswer();
		}
		
		return question;
	}

	/**
	 * Fetches all Questions that belong to a particular survey.
	 * 
	 * @param surveyId The unique identifier of the survey.
	 * @return
	 */
	public static ArrayList<Question> questions(int surveyId) {
		ArrayList<Question> questions = new ArrayList<Question>();
		ArrayList<Integer> qIds = new ArrayList<Integer>();
		Question question = null;
		ResultSet rs = query("SELECT id, survey_id, text FROM question WHERE survey_id=" + surveyId);

		try {
			while (rs.next()) {
				qIds.add(rs.getInt(1));
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		
		for(int i : qIds ) {
			question = question(i);
			questions.add(question);	
		}
		
		return questions;
	}

	/**
	 * Counts how often all options of one MultipleChoiseQuestion have been
	 * chosen
	 * 
	 * @param question
	 * @return The String represents the option, the Integer represents how
	 *         often a option has been chosen. Nota bene: options that have not
	 *         been chosen at all are not returned.
	 */
	public static Hashtable<String, Integer> stats(MultipleChoiceQuestion question) {
		Hashtable<String, Integer> result = new Hashtable<String, Integer>();
		ResultSet rs = query("SELECT text, count(option_id) FROM answer_option " + "JOIN `option` " + "ON OPTION.id = answer_option.option_id "
				+ "WHERE option_id IN (SELECT id FROM `option` WHERE question_id = " + question.id + ") " + "GROUP BY option_id " + "ORDER BY option_id");
		try {
			while (rs.next()) {
				result.put(rs.getString(1), rs.getInt(2));
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}

		return result;
	}

	/**
	 * Calculates the average answer to a ScaleQuestion
	 * 
	 * @param question
	 * @return
	 */
	public static double stats(ScaleQuestion question) {
		double result = 0;
		ResultSet rs = query("SELECT (SUM(VALUE)/COUNT(VALUE)) " + "FROM answer " + "JOIN answer_scale " + "ON answer.id = answer_scale.answer_id "
				+ "WHERE question_id = " + question.id);
		try {
			while (rs.next()) {
				result = rs.getDouble(1);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}
		return result;
	}

	/**
	 * Fetches the answer a user has given for a particular Question.
	 * 
	 * @param question It is assumed that a userId has been assigned to this
	 *            Question.
	 * @return
	 */
	public static String answer(Question question) {
		String result = "";
		if (question.getUserId() == -1)
			return null;
		ResultSet rs = query("SELECT text " + "FROM answer " + "WHERE question_id = " + question.id + " " + "AND user_id = " + question.getUserId());
		try {
			while (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}

		return result;
	}

	/**
	 * Fetches the answer and comment a user has given for a particular
	 * MultipleChoiceQuestion.
	 * 
	 * @param question It is assumed that a userId has been assigned to this
	 *            Question.
	 * @return An Array containing 2 Strings: [0] the answer, [1] the comment.
	 */
	public static String[] answer(MultipleChoiceQuestion question) {
		String[] result = new String[2];
		result[0] = "";
		result[1] = "";

		ResultSet rs = query("SELECT `option`.text, answer.text " + "FROM answer " + "	JOIN answer_option " + "	ON answer.id = answer_option.answer_id "
				+ "	JOIN `option` " + "	ON answer.question_id = `option`.question_id " + "WHERE answer.question_id = " + question.id + " "
				+ "AND `option`.id = answer_option.option_id " + "AND user_id = " + question.getUserId());
		try {
			while (rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}

		return result;
	}

	/**
	 * Fetches the answer and comment a user has given for a particular
	 * ScaleQuestion.
	 * 
	 * @param question It is assumed that a userId has been assigned to this
	 *            Question.
	 * @return An Array containing 2 Strings: [0] the answer, [1] the comment.
	 */
	public static String[] answer(ScaleQuestion question) {
		String[] result = new String[2];
		result[0] = "";
		result[1] = "";

		ResultSet rs = query("SELECT answer_scale.value, answer.text " + "FROM answer " + "JOIN answer_scale " + "ON answer.id = answer_scale.answer_id "
				+ "WHERE answer.question_id = " + question.id + " " + "AND user_id = " + question.getUserId());
		try {
			while (rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);
			}
			rs.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			Dao.close();
		}

		return result;
	}

	/**
	 * Stores the answer to a Question.
	 * @param question The question which contains the answer.
	 * @return True if successful, false if not.
	 */
	public static boolean storeAnswer(Question question) {
		PreparedStatement storeAnswer;
		int answerId = Dao.getAnswerId(question.getId(), question.getUserId());

		try {
			makeConnection();
			con.setAutoCommit(false);
			
			if (answerId == -1) {
				String qInsertAnswer = "INSERT INTO answer(question_id, user_id, text)" + "VALUES (?, ?, ?)";

				storeAnswer = con.prepareStatement(qInsertAnswer);
				storeAnswer.setInt(1, question.id);
				storeAnswer.setInt(2, question.getUserId());
				storeAnswer.setString(3, question.getAnswer());
				storeAnswer.execute();
			}
			else {
				String qUpdateAnswer = "UPDATE answer " + "SET question_id = ?, " + "user_id = ?, " + "text = ? " + "WHERE id = ?";

				storeAnswer = con.prepareStatement(qUpdateAnswer);
				storeAnswer.setInt(1, question.id);
				storeAnswer.setInt(2, question.getUserId());
				storeAnswer.setString(3, question.getAnswer());
				storeAnswer.setInt(4, answerId);
				storeAnswer.execute();
			}

			con.setAutoCommit(true);
			storeAnswer.close();
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
		finally {
			Dao.close();
		}

		Dao.updateQuestionPointer(question);
		return true;
	}

	/**
	 * Stores the answer to a MultipleChoiceQuestion.
	 * @param question The question which contains the answer.
	 * @return True if successful, false if not.
	 */
	public static boolean storeMultipleChoiseAnswer(Question question) {
		MultipleChoiceQuestion q = (MultipleChoiceQuestion) question;
		int optionId = -1;
		int answerId = -1;
		optionId = getOptionId(q.getId(), q.getAnswer());
		answerId = getAnswerId(q.getId(), q.getUserId());

		PreparedStatement storeAnswer;
		PreparedStatement storeAnswerOption;

		try {
			makeConnection();
			con.setAutoCommit(true);
			if (answerId == -1) {
				String qInsertAnswer = "INSERT INTO answer (question_id, user_id, text)" + "VALUES(?, ?, ?)";

				String qInsertAnswerOption = "INSERT INTO answer_option (answer_id, option_id)" + "VALUES(?, ?)";

				storeAnswer = con.prepareStatement(qInsertAnswer);
				storeAnswer.setInt(1, q.getId());
				storeAnswer.setInt(2, q.getUserId());
				storeAnswer.setString(3, q.getComment());
				storeAnswer.execute();

				answerId = Dao.getAnswerId(q.getId(), q.getUserId());

				makeConnection();
				storeAnswerOption = con.prepareStatement(qInsertAnswerOption);
				storeAnswerOption.setInt(1, answerId);
				storeAnswerOption.setInt(2, optionId);
				storeAnswerOption.execute();
			}
			else {
				String qUpdateAnswer = "UPDATE answer " + "SET text = ? " + "WHERE id = ?";

				String qUpdateAnswerOption = "UPDATE answer_option " + "SET option_id = ? " + "WHERE answer_id = ?";

				storeAnswer = con.prepareStatement(qUpdateAnswer);
				storeAnswer.setString(1, q.getComment());
				storeAnswer.setInt(2, answerId);
				storeAnswer.execute();

				storeAnswerOption = con.prepareStatement(qUpdateAnswerOption);
				storeAnswerOption.setInt(1, optionId);
				storeAnswerOption.setInt(2, answerId);
				storeAnswerOption.execute();
			}

			storeAnswer.close();
			storeAnswerOption.close();
		}
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
			return false;
		}
		finally {
			Dao.close();
		}

		Dao.updateQuestionPointer(question);
		return true;
	}

	/**
	 * Stores the answer to a ScaleQuestion.
	 * @param question The question which contains the answer.
	 * @return True if successful, false if not.
	 */
	public static boolean storeScaleAnswer(Question question) {
		ScaleQuestion q = (ScaleQuestion) question;
		int answerId = -1;
		int answer = Integer.parseInt(q.getAnswer());

		answerId = getAnswerId(q.getId(), q.getUserId());

		PreparedStatement storeAnswer;
		PreparedStatement storeAnswerScale;

		try {
			makeConnection();

			con.setAutoCommit(true);
			if (answerId == -1) {
				String qInsertAnswer = "INSERT INTO answer (question_id, user_id, text) " + "VALUES(?, ?, ?)";
				String qInsertAnswerScale = "INSERT INTO answer_scale (answer_id, value) " + "VALUES(?, ?)";

				storeAnswer = con.prepareStatement(qInsertAnswer);
				storeAnswer.setInt(1, q.getId());
				storeAnswer.setInt(2, q.getUserId());
				storeAnswer.setString(3, q.getComment());
				storeAnswer.execute();

				answerId = Dao.getAnswerId(q.getId(), q.getUserId());

				makeConnection();
				storeAnswerScale = con.prepareStatement(qInsertAnswerScale);
				storeAnswerScale.setInt(1, answerId);
				storeAnswerScale.setInt(2, answer);
				storeAnswerScale.execute();
				// store scale
			}
			else {
				String qUpdateAnswer = "UPDATE answer " + "SET text = ? " + "WHERE id = ?";
				String qUpdateAnswerScale = "UPDATE answer_scale " + "SET value = ? " + "WHERE answer_id = ?";

				storeAnswer = con.prepareStatement(qUpdateAnswer);
				storeAnswer.setString(1, q.getComment());
				storeAnswer.setInt(2, answerId);
				storeAnswer.execute();

				storeAnswerScale = con.prepareStatement(qUpdateAnswerScale);
				storeAnswerScale.setInt(1, answer);
				storeAnswerScale.setInt(2, answerId);
				storeAnswerScale.execute();
			}
			
//			con.commit();

			storeAnswer.close();
			storeAnswerScale.close();

			

		}
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
			return false;
		}
		finally {
			Dao.close();
		}
		
		Dao.updateQuestionPointer(question);
		return true;
	}

	public static boolean completeSurvey(Survey survey) {
		PreparedStatement completeSurvey;

		try {
			makeConnection();
				String qInsertAnswer = "UPDATE user_survey SET completed = 1 WHERE survey_id = ? AND user_id = ?";
				completeSurvey = con.prepareStatement(qInsertAnswer);
				completeSurvey.setInt(1, survey.getId());
				completeSurvey.setInt(2, survey.user.id);
				completeSurvey.execute();
				completeSurvey.close();
		} 
		catch(SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);;
		}
		finally {
			Dao.close();
		}
		
		return false;
	}
	
	/*
	 * Tools
	 */

	/**
	 * Communicates with the database; handles all connections
	 * 
	 * @param query A valid and complete SQL query
	 * @return
	 */
	private static ResultSet query(String query) {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		}
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
		}

		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			if (query.substring(0, 6).toUpperCase().indexOf("SELECT") >= 0) {
				rs = st.executeQuery(query);
			}
			else {
				st.executeUpdate(query);
			}
		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		
		return rs;
	}

	/**
	 * Loads the database file to set up initial values
	 */
	private static void init() {
		try {
		Dao.makeConnection();;
			con.setAutoCommit(false);
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}

		FileReader fr;
		ScriptRunner sr = new ScriptRunner(con, false, true);
		try {
			fr = new FileReader("src/data/infprg03.sql");
			sr.runScript(fr);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		Dao.close();

	}

	/**
	 * Loads the database file to set up initial values
	 */
	public static void deployDatabase(String filename) {
		try {
		Dao.makeConnection();;
			con.setAutoCommit(false);
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}

		FileReader fr;
		ScriptRunner sr = new ScriptRunner(con, false, true);
		try {
			fr = new FileReader(filename);
			sr.runScript(fr);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		Dao.close();

	}
	
	/**
	 * Some database connection housekeeping at the end of Main.
	 */
	private static void close() {
		try {
			if (st != null) {
				st.close();
				st = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}

		}
		catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
		}
	}

	/**
	 * Looks for an answer and returns it's id. If it does not find an answer it
	 * returns -1.
	 * @param questionId The question's unique identifier
	 * @param userId The user's unique identifier
	 * @return The answer's id or -1 when no answer was found.
	 */
	private static int getAnswerId(int questionId, int userId) {
		PreparedStatement getAnswerId;
		try {
			makeConnection();

			String qGetAnswerId = "SELECT id " + "FROM answer " + "WHERE question_id = ? " + "AND user_id = ?";

			getAnswerId = con.prepareStatement(qGetAnswerId);
			getAnswerId.setInt(1, questionId);
			getAnswerId.setInt(2, userId);

			ResultSet rAnswerId = getAnswerId.executeQuery();
			if (rAnswerId.next()) {
				return rAnswerId.getInt(1);
			}
			getAnswerId.close();
			rAnswerId.close();
			return -1;
		}
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
			return -1;
		}
		finally {
			Dao.close();
		}
	}

	/**
	 * Returns the option's id, which is obtained by the question's id
	 * and the value (string)
	 * @param questionId The question's unique identifier
	 * @param answer The value of the option
	 * @return The option's id or -1 when no option was found.
	 */
	private static int getOptionId(int questionId, String answer) {
		PreparedStatement getOptionId;
		try {
			makeConnection();

			String qGetOptionId = "SELECT option.id, option.text " + "FROM `option` " + "WHERE question_id = ? ";

			getOptionId = con.prepareStatement(qGetOptionId);
			getOptionId.setInt(1, questionId);

			ResultSet rOptionId = getOptionId.executeQuery();
			while (rOptionId.next()) {
				if (rOptionId.getString(2).compareTo(answer) == 0) {
					return rOptionId.getInt(1);
				}
			}
			getOptionId.close();
			rOptionId.close();
			return -1;
		}
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
			return -1;
		}
		finally {
			Dao.close();
		}
	}

	private static boolean updateQuestionPointer(Question question) {
		PreparedStatement updateQuestionPointer;
		try {
			makeConnection();

			String qUpdateQuestionPointer = "UPDATE user_survey SET question_pointer = ? WHERE survey_id = ? AND user_id = ?";

			updateQuestionPointer = con.prepareStatement(qUpdateQuestionPointer);
			updateQuestionPointer.setInt(1, question.getQuestionNumber());
			updateQuestionPointer.setInt(2, question.surveyId);
			updateQuestionPointer.setInt(3, question.getUserId());
			updateQuestionPointer.execute();
			
			updateQuestionPointer.close();
			return false;

		}
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
			return false;
		}
		finally {
			Dao.close();
		}
	}
	
	/**
	 * Register Driver and make connection with the database.
	 */
	private static void makeConnection() {
		try {
			if (con == null) {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				con = DriverManager.getConnection(url, user, password);
			}
		}
		catch (SQLException e) {
			Logger lgr = Logger.getLogger(Dao.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
	}
}

