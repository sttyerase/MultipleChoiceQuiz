package com.dbmi.demos.quiz;

import org.xml.sax.*;

/**
 * @author Daniel B. Moore
 * <p>MCQuiz (Multiple Choice Quiz Servlet)
 * <p>Public Licence
 * <p>Copyright (c) 1999-2000 Daniel B. Moore.  All rights reserved.
 *
 * <p>This program is free software; you can redistribute it and/or
 * modify it under the current terms of the GNU General Public License
 * as published by the Free Software Foundation (ref http://www.gnu.org).
 * <p>The QuizBase class extends the event based XML parsing class from
 * IBM to handle parsing chores. 
 */


public class QuizBase extends org.xml.sax.HandlerBase
{
   private Quiz thisQuiz = null;
   private Question thisQ = null;
   private QuestionList qList = null;
   private java.util.Stack myStack = new java.util.Stack();
   private String charactersTempString = new String("");
   private int i = 0;

   /** null constructor constructs a quizbase to handle SAX events. */
   QuizBase(){
      super();
   } // NULL CONSTRUCTOR

   /** constructs a quizbase with a predefined QuestionList */
   QuizBase(QuestionList qList){
      super();
      this.qList = qList;
   } // CONSTRUCTOR

   /** constructs a quizbase, passing the Quiz and QuestionList handles.*/
   QuizBase(Quiz aQuiz,QuestionList qList){
      super();
      this.thisQuiz = aQuiz;
      this.qList = qList;
   } // CONSTRUCTOR

   /** Implements logic executed when a start of element is detected.
    *  Overrides startElement() in HandlerBase which does nothing.
    *  @exception org.xml.sax.SaxException
    */
   public void startElement(String name, AttributeList attrs) throws SAXException{
      if(name.equals("question")){
         thisQ = new Question();
      }else if(name.equals("questionText")){
         charactersTempString = "";
      }else if(name.equals("choiceList")){
         i = 0;
         Integer va = new Integer(attrs.getValue("validAnswer"));
         thisQ.setCorrectAnswerNumber(va.intValue()-1);
      }else if(name.equals("choice")){
      }else if(name.equals("explanation")){
      }else if(name.equals("quiz")){
         //thisQuiz.setQuizName(attrs.getValue("name"));
      }else{
         System.out.println("Nothing started");
      } // IF-THEN
   } // STARTELEMENT()

   /** Implements logic executed when and end of element is detected.
    *  Overrides endElement() in HandlerBase which does nothing.
    *  @exception org.xml.sax.SaxException
    */
   public void endElement(String name) throws SAXException{
      if(name.equals("question")){
         qList.addElement(thisQ);
      }else if(name.equals("questionText")){
         thisQ.setQuestionText(charactersTempString.replace('\n', ' '));
      }else if(name.equals("choiceList")){
         thisQ.trimChoiceToSize();
      }else if(name.equals("choice")){
         i++;
         thisQ.addChoiceElement(charactersTempString.replace('\n', ' '));
      }else if(name.equals("explanation")){
         thisQ.setExplanation(charactersTempString.replace('\n', ' '));
      }else if(name.equals("quiz")){
      }else{
         System.out.println("Nothing completed");
      } // IF-THEN
   } // ENDELEMENT()

   /** Implements logic executed when an end of document is detected.
    *  Overrides endElement() in HandlerBase which does nothing.
    *  Trim the Question List to its exact number of elements
    * @exception org.xml.sax.SAXException
    */
   public void endDocument() throws SAXException{
      qList.trimToSize();
   } // ENDDOCUMENT()

   /** Return a String of parsed characters
    * @exception org.xml.sax.SAXException
    */
   public void characters(char ch[],int start,int length) throws SAXException{
      charactersTempString = new String(ch,start,length);
   } // CHARACTERS()
}
