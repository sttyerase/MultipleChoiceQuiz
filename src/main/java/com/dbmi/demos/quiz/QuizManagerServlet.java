package com.dbmi.demos.quiz;

import javax.servlet.*;

/**
*/


/** @author Daniel B. Moore, aka sttyerase@gmail.com <p>
 * <p>QuizManagerServlet (Multiple Choice Quiz)
 * <p>Public Licence
 * <p>Copyright (c) 1999-2000 Daniel B. Moore.  All rights reserved.
 *
 * <p>This program is free software; you can redistribute it and/or
 * modify it under the current terms of the GNU General Public License
 * as published by the Free Software Foundation (ref http://www.gnu.org).
 * <p><b>The QuizManagerServlet class provides the servlet interface for displaying XML documents of
 * type "Multiple Choice Quiz" to client browsers. This interface 
 * creates a quiz document for display in a browser from an XML document specified 
 * in the browser URL and creates forms for response to quiz questions.</b>
 **/
public class QuizManagerServlet extends javax.servlet.http.HttpServlet
{
   private String theDocument = null;  /** holds the name of the Quiz document
                                         * to be parsed. **/  
   private Quiz myQuiz = null;
   private QuestionList qList = new QuestionList(300);
   private String quizPath = null;

   /** Initiializes the QuizManagerServlet servlet.    */
   
   public void init(ServletConfig config){
      try{
         super.init(config);
         quizPath = config.getInitParameter("quizpath");
	   	if(quizPath == null) quizPath = ".";
         quizPath += "/";
         config.getServletContext().log("Init values: quizpath=" + quizPath);
      }catch (ServletException se){
         log("Servlet Exception caught: " + se);
      }
   } // INIT

   /** RETURN A COMMENT ABOUT THE SERVLET. USED BY APPLICATION SERVERS TO DISPLAY
     * A SERVLET'S FUNCTION.
     **/
	public String getServletInfo()
	{
		return "A quiz Servlet that displays multiple choice questions from an XML document.";
	}

} // CLASS
