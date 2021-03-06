/*
 * @author Daniel B. Moore <p>
 * This software copyrighted and protected under the terms of the GNU General Purpose
 * License. Terms and conditions available from http://www.gnu.org. 
 */

package com.dbmi.demos.quiz;

import org.xml.sax.SAXException;

/**
 * @author Daniel B. Moore <p>
 * This class parses the input document to obtain all of the questions and place them on
 * the list of questions. This class further responds to the QuizServlet which receives input
 * from the user/client.
 */
public class Quiz{
   /** List of questions for this Quiz. */
   private String theDocument = new String("");
   private String quizPath = new String("");
   private String quizName = new String("The Quiz");

   public Quiz(){
      super();
   } // NULL CONSTRUCTOR

   public Quiz(String quizPath,String theDocument){
      super();
      this.quizPath = quizPath;
      this.theDocument = theDocument;
      quizName = theDocument.substring(0,theDocument.lastIndexOf(".quiz"));
   } // CONSTRUCTOR(STRING)

   /** This method calls the XML parser and builds Question
    *  elements to place into the QuestionList.
    */
   public void fillQuestionList(QuestionList qList) throws QuizException{
      QuizParser myParser = new QuizParser(this, qList);
      try {
         myParser.parse(quizPath + theDocument);
      }catch(SAXException se) {
         throw new QuizException("Caught parsing error: " + se);
      }catch(java.io.IOException ioe) {
         throw new QuizException("Caught IO error in parser: " + ioe);
      } // TRY-CATCH
   } // CREATEQUESTIONLIST

   public String getDocName(){
      return theDocument;
   }

   public String getQuizName(){
      return quizName;
   }

   /** set the document name to String. **/
   public void setDocName(String theDocument){
      this.theDocument = theDocument;
   }

   /** set the quiz name to String */
   public void setQuizName(String quizName){
      this.quizName = quizName;
   }

} // CLASS
