/**
*/

import com.dbmsi.quiz.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/** @author Daniel B. Moore, aka sttyerase@gmail.com <p>
 * <p>MCQuiz (Multiple Choice Quiz)
 * <p>Public Licence
 * <p>Copyright (c) 1999-2000 Daniel B. Moore.  All rights reserved.
 *
 * <p>This program is free software; you can redistribute it and/or
 * modify it under the current terms of the GNU General Public License
 * as published by the Free Software Foundation (ref http://www.gnu.org).
 * <p><b>The MCQuiz class provides the servlet interface for displaying XML documents of
 * type "Multiple Choice Quiz" to client browsers. This interface 
 * creates a quiz document for display in a browser from an XML document specified 
 * in the browser URL and creates forms for response to quiz questions.</b>
 **/
public class MCQuiz extends javax.servlet.http.HttpServlet
{
   private String theDocument = null;  /** holds the name of the Quiz document
                                         * to be parsed. **/  
   private Quiz myQuiz = null;
   private QuestionList qList = new QuestionList(300);
   private QuizLayout ql = null;
   PrintWriter pw = null;
   private String quizPath = null;
   private String pathSeparator = null;

   /** Initiializes the MCQuiz servlet. The servlet exec engine calls this
    *  method.
    */
   public void init(ServletConfig config){
      String thisPlatform = new String("");
      try{
         super.init(config);
         quizPath = config.getInitParameter("quizpath");
	   	if(quizPath == null) quizPath = ".";
         thisPlatform = config.getInitParameter("platform");
         if(thisPlatform.equalsIgnoreCase("win32")) pathSeparator = "\\";
         else pathSeparator = "/";
         quizPath += pathSeparator;
         log("Init values: platform=" + thisPlatform + " quizpath=" + quizPath + ", path separator = " + pathSeparator);
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

	/** THIS METHOD IMPLEMENTS THE ENTIRE RESPONSE LOGIC FOR THE MCQUIZ SERVLET. RESPONDS TO THE
	  * REQUEST FROM THE BROWSER. IF THIS IS A NEW SESSION, THE METHOD PARSES THE QUIZ
	  * XML AND CREATES A QUIZ OBJECT.
	  **/
   public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
	{
	   int qNumber = 0;
		resp.setContentType("text/html");
	   pw = new PrintWriter(resp.getOutputStream());
	   
	   // USE A QUIZ LAYOUT TO BUILD THE OUTPUT STREAMS
   	ql = new QuizLayout(pw);
      String pval = req.getParameter("next");

		// GET SESSION INFORMATION AND START NEW OR RESUME PREVIOUS
		HttpSession thisSession = req.getSession(true);
		if(thisSession.isNew()){ // THIS IS A NEW SESSION
	   	theDocument = req.getParameter("QuizName");
	   	log("New session for: " + theDocument + " using path " + quizPath);
	   	if(theDocument == null){
	   	   log("No Document argument provided....");
	   	   pw.println("No quiz name was provided. Please notify the site administrator to update the URL that brought you here.");
	   	   return;
	   	}

	   	// CREATE THE QUIZ, PARSE THE QUIZ DOCUMENT AND UPDATE THE SESSION
	   	myQuiz = new Quiz(quizPath,theDocument);
	   	qList = new QuestionList(300);
	   	ScoreKeeper mySK = new ScoreKeeper();
	   	thisSession.putValue("Quiz",myQuiz);
	   	thisSession.putValue("QuestionList",qList);
	   	thisSession.putValue("output",pw);
	   	thisSession.putValue("ScoreKeeper",mySK);
	   	try{
	   	   myQuiz.fillQuestionList(qList);
	   	   qList.setCurrentQuestionNumber(0);
	   	}catch (org.xml.sax.SAXException saxe){
	   	   pw.println("Sorry, I have made a small error. Please ask The Creator to check the logs and repair my circuits.");
	   	   log("SAX Exception: " + saxe);
	   	}catch (java.io.IOException ioe){
	   	   pw.println("Sorry, I have made a small error. Please ask The Creator to check the Jserv logs.");
	   	   log("IO Exception: " + ioe);
	   	}
	   	log("Quiz parsed.");

	   	//PRINT THE INTRODUCTORY PAGE
	   	ql.printHeadBlock();
	      ql.layoutFirstPage(qList.size(),myQuiz.getQuizName(),resp.encodeUrl("/servlet/MCQuiz"));
	      // SAVE ME....  ql.layoutFirstPage(qList.size(),myQuiz.getQuizName(),"/servlet/MCQuiz?" + thisSession.getId());
         ql.printTrailBlock();

	   }else{   // ************ SESSION IS NOT NEW! ************

	      String pageText = new String("");
   		if(thisSession.isNew()){
	   	   pw.println("<p>New session.....get help....call the operator....</p>");
		      log("<p>New session.....get help....call the operator....</p>");
   		}

   		// RETRIEVE OBJECTS FROM THE SESSION
	      Quiz myQuiz = (Quiz)thisSession.getValue("Quiz");
	      QuestionList myList = (QuestionList)thisSession.getValue("QuestionList");
	      ScoreKeeper sk = (ScoreKeeper)thisSession.getValue("ScoreKeeper");

   	   // UNTIL THE QUESTION LIST IS COMPLETE, CONTINUE RETREIVING QUESTIONS
   	   // AND RESPONDING TO THE ANSWERS
   	   if(myList.notDone()){ // 1
   	      qNumber = myList.getCurrentQuestionNumber();
   	      pw.println("<p>Current Question Number: " + (qNumber+1));
   	      Question myQ = (Question)myList.elementAt(myList.getCurrentQuestionNumber());
            if(pval.equals("Continue")){ // 2
               ql.printHeadBlock("Quiz: Question " + (qNumber+1));
               ql.layoutQuestion(myQ);
               ql.printTrailBlock();
            }else if (pval.equals("Submit")){
               String answer = req.getParameter("answer");
               myQ.setAnswered(true);
               if(answer.equals(myQ.getCorrectAnswer()))myQ.setAnsweredCorrectly(true);
               sk.scoreQuiz(myList);
               ql.printHeadBlock("Quiz: Answer " + (qNumber+1));
               ql.layoutResponse(myQ,sk);
               ql.printTrailBlock();
               myList.setCurrentQuestionNumber(myList.getCurrentQuestionNumber()+1);
            }else{
               pageText = "<p>This type of request is invalid here....(" + pval + ")";
               pageText = "Use the back button and select another choice.";
               ql.printHeadBlock();
               ql.layoutPage(pageText);
               ql.printTrailBlock();
            } // IF-THEN 2
         }else if (pval.equals("Restart")){
            myList.setCurrentQuestionNumber(0);
            myList.reInitialize();
            sk.reInitialize();
            ql.printHeadBlock("Quiz: Question " + (qNumber+1));
            ql.layoutFirstPage(qList.size(),myQuiz.getQuizName(),resp.encodeUrl("/servlet/MCQuiz"));
            ql.printTrailBlock();
	      }else{
	         ql.printHeadBlock();
   	      ql.layoutLastPage(myQuiz);
            ql.printTrailBlock();
	      } // IF-THEN 1
	      //RESTART??
	   } // IF-SESSION
	   pw.flush();
      // for debugging: log("End of doGet() achieved....");
	} // DO GET

	/** Implements the response to POST requests from the browser client by calling doGet(). **/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
	{
	   doGet(req,resp);
	} // DO POST

} // CLASS
