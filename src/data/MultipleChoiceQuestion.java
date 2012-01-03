package data;

import java.util.ArrayList;
import java.util.Hashtable;

public class MultipleChoiceQuestion extends Question {

	private String comment;
	private ArrayList<String> options;

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
	 * Pretty prints the Question, with all it's options. If an answer is
	 * present, it will be indicated in the output.
	 */
	@Override
	public String toString() {
		String result = getText() + "\n";
		for (int i = 0; i < options.size(); i++) {
			result += (char) ('A' + i) + ": " + options.get(i);
			if (options.get(i).equals(answer))
				result += " <---";
			if (i + 1 != options.size())
				result += "\n";
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
		String result = super.toString() + "\n";
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
				result += "\n";
		}
		return result;
	}
}
