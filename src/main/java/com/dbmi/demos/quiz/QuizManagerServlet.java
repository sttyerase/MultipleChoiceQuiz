package com.dbmi.demos.quiz;

import javax.servlet.*;

/** @author Daniel B. Moore, aka sttyerase@gmail.com <p>
 * <p>QuizManagerServlet (Multiple Choice Quiz)
 **/
public class QuizManagerServlet extends javax.servlet.http.HttpServlet
{
   private String theDocument = null;  /** holds the name of the Quiz document
                                         * to be parsed. **/
   private Quiz myQuiz = null;
   private QuestionList qList = new QuestionList(300);
   private String quizPath = null;

   /** Initializes the QuizManagerServlet servlet.    */
   
   public void init(ServletConfig config){
      try{
         super.init(config);
         quizPath = config.getInitParameter("quizpath");
         config.getServletContext().setAttribute("manager", this);
         config.getServletContext().log("Quiz servlet initialized.");
	   	if(quizPath == null) quizPath = ".";
         quizPath += "/";
         config.getServletContext().log("Init values: quizpath=" + quizPath);
      }catch (ServletException se){
         log("Servlet Exception caught: " + se);
      } // TRY-CATCH
   } // INIT

   /** RETURN A COMMENT ABOUT THE SERVLET. USED BY APPLICATION SERVERS TO DISPLAY
     * A SERVLET'S FUNCTION.
     **/
	public String getServletInfo()
	{
		return "A quiz Servlet that displays multiple choice questions from an XML document.";
	} // GETSERVLETINFO()

} // CLASS
