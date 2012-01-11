package data;

public class Question {

	public final int id;
	public final int surveyId;
	protected int userId = -1;
	private String text;
	protected String answer = "";
	
	/**
	 * Basic constructor for a text-only Question
	 * @param id The unique identifier for this Question
	 * @param surveyId The unique identifier of the Survey this Question belongs to
	 */
	public Question (int id, int surveyId){
		this.id = id;
		this.surveyId = surveyId;
	}
	
	/**
	 * A constructor for a text-only Question that immediately adds a user, and retrieves his/her answer. 
	 * @param id The unique identifier for this Question
	 * @param surveyId The unique identifier of the Survey this Question belongs to
	 * @param userId The unique identifier of the User
	 */
	public Question (int id, int surveyId, int userId){
		this.id = id;
		this.surveyId = surveyId;
		this.userId = userId;
		if (this.getClass().getName() == "data.Question"){
			this.answer = Dao.answer(this);
		}
	}

	/**
	 * Gets the text of the Question
	 * @return
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * Sets the text of the Question
	 * @param text The new text of the Question
	 * @return TRUE if setting was successful
	 */
	public boolean setText(String text){
		this.text = text;
		return true;
	}
	
	/**
	 * Gets the ID of the user connected to this question
	 * @return
	 */
	public int getUserId(){
		return this.userId;
	}
	
	/**
	 * Get the answer
	 * @return the answer
	 */
	public String getAnswer() {
		return this.answer;
	}
	
	/**
	 * Sets the User's answer to this question
	 * @param answer
	 */
	public void setAnswer(String answer){
//		Dao.storeAnswer(this, answer);
		this.answer = answer;
	}
	
	/**
	 * Sets the User's id
	 * @param userId The unique identifier of the User
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Get the Question's id
	 * @return question's id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Pretty print the Question, and if applicable, the answer.
	 */
	@Override
	public String toString(){
		String result = "";
		result += getText();
		if (answer != null){
			result += "<br>"+answer;
		}
		return result;
	}
	
	/**
	 * Pretty print the Question, and if applicable, the answer.
	 * @param getStats If TRUE, prints the fact that this is a text-only Question
	 * @return
	 */
	public String toString(boolean getStats){
		if (getStats){
		return getText()+"<br> - Open vraag - ";
		} else if (answer != null){
			return getText()+"<br>"+answer;
		}
		return getText();
	}
}
