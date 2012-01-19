package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import data.Dao;

/**
 * Application Lifecycle Listener implementation class EnqueteServletContextListener
 *
 */
@WebListener
public class EnqueteServletContextListener implements ServletContextListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
	public void contextInitialized(ServletContextEvent sCE) {
    	Dao.deployDatabase(sCE.getServletContext().getRealPath("WEB-INF/deployment.sql"));
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
	public void contextDestroyed(ServletContextEvent arg0) {
    	
    }
	
}
