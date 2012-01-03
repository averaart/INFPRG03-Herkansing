package data;

import java.util.ArrayList;

/**
 * 
 * Representative of a user
 * 
 * @author averaart
 *
 */

public class User {
	
	public final int id;
	private String name;
	private String password;
	
	
	
	/*
	 * User
	 */
	
	/**
	 * Constructor method.
	 * Requires the id of the particular user, because it is a FINAL value
	 *  
	 * @return a User
	 */
	public User(int id){
		this.id = id;
	}
	
	/**
	 * Gets name
	 * @return name as String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Set the user name
	 * @param name
	 * @return TRUE if succeeded
	 */
	public boolean setName(String name){
		this.name = name;
		return true;
	}
	
	/**
	 * Gets the user's password
	 * @return the password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Sets the user's password
	 * @param password
	 * @return TRUE if succeeded
	 */
	public boolean setPassword(String password){
		this.password = password;
		return true;
	}

	
	
	/*
	 * Surveys
	 */ 
	
	/**
	 * Get a list of all surveys that are connected to this User
	 * @return An ArrayList containing all available Surveys
	 */
	public ArrayList<Survey> surveys(){
		ArrayList<Survey> surveys = Dao.surveys(this);
		return surveys;
	}
	
	/**
	 * Add a Survey to this User
	 * @param survey
	 * @return A new list of all surveys connected to this user.
	 */
	public ArrayList<Survey> addSurvey(Survey survey){
		Dao.connectUserToSurvey(this, survey);
		return surveys();
	}
	
	
	
	/*
	 * Tools
	 */
	
	/**
	 * @return A String with user info.
	 */
	public String toString(){
		String result = "";
		
		result += "Naam: "+getName()+"\n";
		result += "Wachtwoord: "+getPassword();
		
		return result;
	}
}
