package servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Test extends HttpServlet {

	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	    //set the MIME type of the response, "text/html"
	    res.setContentType("text/html");
		
 		PrintWriter out = res.getWriter();
 		out.println("<html><head><title>Mijn naam is...</title></head><body><h1>Andra!</h1></body></html>");
 		out.close();
 		log("Woohoo!");
	}

	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
 		log("Uhm, POST?");
	}
}
