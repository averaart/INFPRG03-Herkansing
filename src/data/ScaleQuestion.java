package data;


public class ScaleQuestion extends Question {

	private int range;
	private String comment;
	private String lowText;
	private String highText;
	private Double average;
	
	/**
	 * Basic constructor for a ScaleQuestion
	 * @param id The unique identifier for this Question
	 * @param surveyId The unique identifier of the Survey this Question belongs to
	 */
	public ScaleQuestion(int id, int surveyId) {
		super(id, surveyId);
	}

	
	public ScaleQuestion(int id, int surveyId, int userId) {
		super(id, surveyId, userId);
		this.userId = userId;
		String[] answers = Dao.answer(this);
		this.answer = answers[0];
		this.comment = answers[1];
	}
	
	/**
	 * Gets the number of steps for this ScaleQuestion
	 * @return
	 */
	public int getRange(){
		return range;
	}
	
	/**
	 * Sets the number of steps for this ScaleQuestion
	 * @param range The number of steps
	 * @return TRUE if setting was succesful
	 */
	public boolean setRange(int range){
		this.range = range;
		return true;
	}
	
	/**
	 * Sets the text for the low end of the scale
	 * @param text The low end of the scale
	 * @return TRUE if setting was successful
	 */
	public boolean setLowText(String text){
		this.lowText = text;
		return true;
	}
	
	/**
	 * Gets the low end of the scale
	 * @return
	 */
	public String getLowText(){
		return lowText;
	}
	
	/**
	 * Sets the text for the high end of the scale
	 * @param text The high end of the scale
	 * @return TRUE if setting was successful
	 */
	public boolean setHighText(String text){
		this.highText = text;
		return true;
	}
	
	/**
	 * Gets the high end of the scale
	 * @return
	 */
	public String getHighText(){
		return highText;
	}
	
	/**
	 * Sets the comment
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Gets the comment
	 * @return the comment
	 */
	public String getComment() {
		return this.comment;
	}
	
	public boolean setAverage(){
		this.average = Dao.stats(this);
		return true;
	}
	
	public double getAverage(){
		return this.average;
	}

	@Override
	/**
	 * Pretty prints a scale question
	 */
	public String toString(){
		String result = super.toString()+"<br>"+
						getLowText()+" - ";
		for (int i=1; i <= range; i++){
			result += i+" ";
		}
		result += "- "+getHighText();
		
		return result;
	}
	
	/**
	 * Pretty prints a scale question
	 * @param getStats If TRUE, adds the average choice to the string.
	 */
	@Override
	public String toString(boolean getStats){
		double average = Dao.stats(this);
		String result = super.toString()+"<br>"+
						getLowText()+" - ";
		for (int i=1; i <= range; i++){
			result += i+" ";
		}
		result += "- "+getHighText();
		result += "<br>"+average;
		return result;
	}
}
