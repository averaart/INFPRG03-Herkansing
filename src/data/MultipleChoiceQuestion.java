package data;

import java.util.ArrayList;
import java.util.Hashtable;

public class MultipleChoiceQuestion extends Question {

	private String comment;
	private final ArrayList<String> options;
	private ArrayList<Double> average;

	/**
	 * Basic constructor for a MultipleChoiceQuestion
	 * 
	 * @param id
	 * @param surveyId
	 */
	public MultipleChoiceQuestion(int id, int surveyId) {
		super(id, surveyId);
		options = new ArrayList<String>();
	}

	/**
	 * Constructor that immediately connects a user, and adds his/her answer.
	 * 
	 * @param id
	 * @param surveyId
	 * @param userId
	 */
	public MultipleChoiceQuestion(int id, int surveyId, int userId) {
		super(id, surveyId, userId);
		options = new ArrayList<String>();
		this.userId = userId;
		this.average = new ArrayList<Double>();
	}

	public void retrieveAnswer() {
		String[] answers = Dao.answer(this);
		this.answer = answers[0];
		this.comment = answers[1];
	}
	
	/**
	 * Gets all options for this MultipleChoiceQuestion
	 * 
	 * @return
	 */
	public ArrayList<String> getOptions() {
		return options;
	}

	/**
	 * Adds an option (surprise, surprise...)
	 * 
	 * @param option
	 * @return TRUE if adding was successful
	 */
	public boolean addOption(String option) {
		options.add(option);
		return true;
	}
	
	/**
	 * Set the comment
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Get the comment
	 * @return the comment
	 */
	public String getComment() {
		return this.comment;
	}

	public boolean setAverage(){
		Hashtable<String, Integer> stats = Dao.stats(this);
		int total = 0;
		for (Integer val : stats.values()) {
			total += val;
		}
		for (int i = 0; i < options.size(); i++) {
			int val;
			if (stats.get(options.get(i)) != null) {
				val = stats.get(options.get(i));
			} else {
				val = 0;
			}
			average.add((100.0 / total) * val);
		}
		return true;
	}
	
	public ArrayList<Double> getAverage(){
		return average;
	}
	
	
	/**
	 * Pretty prints the Question, with all it's options. If an answer is
	 * present, it will be indicated in the output.
	 */
	@Override
	public String toString() {
		String result = getText() + "<br>";
		for (int i = 0; i < options.size(); i++) {
			result += (char) ('A' + i) + ": " + options.get(i);
			if (options.get(i).equals(answer))
				result += " <---";
			if (i + 1 != options.size())
				result += "<br>";
		}
		return result;
	}

	@Override
	/**
	 * Pretty prints the Question, with all it's options.
	 * If an answer is present, it will be indicated in the output.
	 * @param getStats If TRUE, each option is followed by the percentage of votes for that option. 
	 */
	public String toString(boolean getStats) {
		Hashtable<String, Integer> stats = Dao.stats(this);
		int total = 0;
		for (Integer val : stats.values()) {
			total += val;
		}
		String result = super.toString() + "<br>";
		for (int i = 0; i < options.size(); i++) {
			result += (char) ('A' + i) + ": " + options.get(i);
			int val;
			if (stats.get(options.get(i)) != null) {
				val = stats.get(options.get(i));
			} else {
				val = 0;
			}

			result += " - " + ((100.0 / total) * val) + "%";
			if (i + 1 != options.size())
				result += "<br>";
		}
		return result;
	}
}
