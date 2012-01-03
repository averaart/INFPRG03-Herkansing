package servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import data.*;

public class Test extends HttpServlet {

	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	    //set the MIME type of the response, "text/html"
	    res.setContentType("text/html");
		
	    Survey survey = Dao.survey(1);
	    
 		PrintWriter out = res.getWriter();
 		out.println("<html><head><title>Mijn naam is...</title></head><body><h1>Andra!</h1><p>"+survey.toString()+"</p></body></html>");
 		out.close();
 		log("Woohoo!");
	}

	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
 		log("Uhm, POST?");
	}
}
