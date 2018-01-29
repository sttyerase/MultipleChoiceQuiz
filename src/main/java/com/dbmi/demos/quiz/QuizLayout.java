package com.dbmi.demos.quiz;

/**
 * @author Daniel B. Moore <p>
 * <p>QuizManagerServlet (Multiple Choice Quiz Servlet)
 * <p>Public Licence
 * <p>Copyright (c) 1999-2000 Daniel B. Moore.  All rights reserved.
 *
 * <p>This program is free software; you can redistribute it and/or
 * modify it under the current terms of the GNU General Public License
 * as published by the Free Software Foundation.
 * <p>All HTML generation occurs in this class.
 */
public class QuizLayout
{

   /**
    * The output print stream.
    */
   private java.io.PrintWriter pw = null;

   /** The Null Constructor */
   public QuizLayout(){
      super();
   }

   /** Construct a QuizLayout with a PrintWriter for output */
   public QuizLayout(java.io.PrintWriter pw){
      super();
      this.pw = pw;
   }

   /** Print the header for this style of HTML document. This style uses
    *  a table element to format the outer margins of the output.
    */
	public void printHeadBlock(){
   		pw.println("<HTML>");
   		pw.println("<HEAD><TITLE>Untitled</TITLE></HEAD>");
	   	pw.println("<BODY>");
	   	pw.println("<table" + attrib("width","95%"));
	   	pw.println(attrib("align","center") + "><tr><td>");
	}

   /** Print the header for this style of HTML document with a title. This style uses
    *  a table element to format the outer margins of the output.
    */
	public void printHeadBlock(String aTitle){
   		pw.println("<HTML>");
   		pw.println("<HEAD><TITLE>" + aTitle + "</TITLE></HEAD>");
	   	pw.println("<BODY>");
	   	pw.println("<table" + attrib("width","95%"));
	   	pw.println(attrib("align","center") + "><tr><td>");
	}

	/** Print the closing block for the HTML document that uses a table for formating
	 *  margins.
	 */
	public void printTrailBlock(){
	   pw.println("</td></tr></table>");
      pw.println("</BODY>");
		pw.println("</HTML>");
	}

	/** Lay out the first page of output for the QuizManagerServlet servlet
	 */
	public void layoutFirstPage(int numQs,String quizName,String url){
	   	String pageText = new String("");
	   	pageText += "<h1" + attrib("align","center");
	   	pageText += ">Welcome to the Quiz Place</h1>";
	      pageText += "<p" + attrib("align","center")+ ">";
	      pageText += "<b>The " + quizName;
         pageText += " quiz has " + numQs + " questions.</b>";
	      pageText += "<p>&nbsp;";
	      pageText += "<table" + attrib("width","90%") + attrib("border","0");
	      pageText +=  attrib("align","center")+ ">";
	      pageText += "<tr><td" + attrib("align","center") + ">";
	      pageText += "<FORM" + attrib("action",url);
	      pageText += attrib("METHOD","POST");
	      pageText += ">";
	      pageText += "<INPUT" + attrib("type","submit") + attrib("name","next");
	      pageText += attrib("value","Continue") + ">";
	      pageText += "<INPUT" + attrib("type","hidden") + attrib("name","goto");
	      pageText += attrib("value","/servlet/QuizManagerServlet") + ">";
	      pageText += "</tr></td>";
	      pageText += "</table>";
	      layoutPage(pageText);
	}

	/** Lay out the last page of the QuizManagerServlet output. This page adds a restart button
	 *  to allow reinitialization of a new quiz session.
	 */
	public void layoutLastPage(Quiz aQuiz){
	   String pageText = new String("");
   	pageText = "<html><head><title>The End. Thank you.</title></head>\n";
   	pageText += "<body>\n";
   	pageText += "<h1" + attrib("align","center");
   	pageText += ">Thank you for playing the " + aQuiz.getQuizName() + " Quiz";
   	pageText += "</h1>\n";
	   pageText += "<FORM" + attrib("action","/servlet/QuizManagerServlet");
	   pageText += attrib("METHOD","GET");
	   pageText += ">\n";
	   pageText += "<INPUT" + attrib("type","submit") + attrib("name","next");
	   pageText += attrib("value","Restart") + ">\n";
   	pageText += "</body></html>";
   	layoutPage(pageText);
	}

	/** Format the output to display a question on the browser page.
	 */
	public void layoutQuestion(Question myQ){
	   pw.println("<table" + attrib("width","70%") + attrib("border","1") + attrib("bgcolor","#AAEEFF") + attrib("align","center") + ">");
	   pw.println("<tr" + attrib("valign","middle") + "><td>");
	   pw.println("<b>" + myQ.getQuestionText()+ "</b>");
	   pw.println("</td></tr>");
	   pw.println("<tr valign=\"middle\"><td>");
	   pw.println("<table" + attrib("width","70%") + attrib("border","1") + attrib("bgcolor","#AAEEFF") + attrib("align","center") + ">");
	   pw.println("<FORM" + attrib("action","/servlet/QuizManagerServlet/") + attrib("METHOD","POST") + ">");
	   java.util.Enumeration choiceEnum = myQ.choiceElements();
	   while(choiceEnum.hasMoreElements()){
	      String myElement = new String(choiceEnum.nextElement().toString());
	      pw.println("<tr " + attrib("valign","middle")+ "><td>");
	   	pw.println("<INPUT" + attrib("type","radio") + attrib("name","answer"));
	   	pw.println(attrib("value",myElement));
	   	pw.println(">" + myElement);
	      pw.println("</td></tr>");
	   } // WHILE
	   pw.println("<tr><td" + attrib("align","center")+ ">");
	   pw.println("<INPUT" + attrib("type","submit") + attrib("name","next") + attrib("value","Submit"));
	   pw.println("</td></tr>");
	   pw.println("</FORM>");
	   pw.println("</table>");
	   pw.println("</td></tr>");
	   pw.println("</table>");
	}

	/** Format the output to display the results of a response to a question, including
	 *  the correct answer and a running score.
	 */
	public void layoutResponse(Question aQ,ScoreKeeper sk){
	   pw.println("<table" + attrib("width","70%") + attrib("border","1") + attrib("bgcolor","#FFFFBB") + attrib("align","center") + ">");
	   pw.println("<tr" + attrib("valign","middle") + "><td>");
	   pw.println("<b>The answer is:</b> " + aQ.getCorrectAnswer());
	   pw.println("</td></tr>\n");
	   pw.println("<tr" + attrib("valign","middle") + "><td>");
	   pw.println("<p>" + aQ.getExplanation());
	   pw.println("</td></tr>\n");
	   emptyRow();
	   pw.println("</table>");
	   skipline();
	   pw.println("<table" + attrib("width","70%") + attrib("border","1") + attrib("bgcolor","#FFFFBB") + attrib("align","center") + ">");
	   pw.println("<tr" + attrib("colspan","4"));
	   pw.println("<th><b>SCORING</b></th>");
	   pw.println("</tr>");
	   pw.println("<tr>");
	   pw.println("<th>Total Correct</th>");
	   pw.println("<th>Total Answered</th>");
	   pw.println("<th>Percent</th>");
	   pw.println("</tr>\n");
	   pw.println("<tr>");
	   pw.println("<td" + attrib("align","center") + ">" + sk.getTotalCorrect() + "</td>");
	   pw.println("<td" + attrib("align","center") + ">" + sk.getTotalAnswered() + "</td>");
	   pw.println("<td" + attrib("align","center") + ">" + (sk.getPercentCorrect()* 100) + " %</td>");
	   pw.println("</tr>\n");
	   pw.println("</table>");
	   skipline();
	   pw.println("<table" + attrib("width","70%") + attrib("border","0") + attrib("bgcolor","#FFFFFF") + attrib("align","center") + ">");
	   pw.println("<tr><td" + attrib("align","center")+ ">");
	   pw.println("<FORM" + attrib("action","/servlet/QuizManagerServlet/") + attrib("METHOD","POST") + ">");
	   pw.println("<INPUT" + attrib("type","submit") + attrib("name","next") + attrib("value","Continue"));
	   pw.println("<INPUT" + attrib("type","submit") + attrib("name","next") + attrib("value","Restart"));
	   pw.println("</FORM>");
	   pw.println("</td></tr>\n");
	   pw.println("</table>");
	}

	/** Pass formatted text to the print stream. */
	public void layoutPage(String text){
	   pw.println(text);
	}

	/** Format an attribute-value pair for passing to the print stream.*/
	public static String attrib(String attr, String value){
	   return " " + attr + "=\"" + value + "\"";
	}

	/** skip an HTML line */
	public void skipline(){
	   pw.println("<p>&nbsp;</p>");
	}

	/** Add an empty row in a table */
	public void emptyRow(){
	   pw.println("<tr><td>&nbsp;</td></tr>\n");
	}

	/** Return the address of the output print stream. */
	public java.io.PrintWriter getPrintWriter(){
	   return pw;
	}

	/** Set a new output print stream for printing.  */
	public void setPrintWriter(java.io.PrintWriter pw){
	   pw = pw;
	}

} // CLASS
