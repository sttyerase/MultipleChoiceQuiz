package com.dbmi.demos.quiz;

import java.util.*;

/*
 * @author Daniel B. Moore <p>
 */
public class QuestionList extends java.util.Vector<Question>
{
   private static final long serialVersionUID = 387094158321L;
   private int currentQuestionNumber = 0;
   
   public QuestionList(int num){
      super(num);
   } // CONSTRUCTOR
   
   public int getCurrentQuestionNumber(){
      return currentQuestionNumber;
   }
   
   public void setCurrentQuestionNumber(int number){
      this.currentQuestionNumber = number;
   }
   
   public boolean notDone(){
      return this.currentQuestionNumber < this.size();
   }
   
   public void reInitialize(){
      Question aQ;
      Enumeration<Question> e = this.elements();
      while(e.hasMoreElements()){
         aQ = e.nextElement();
         aQ.setAnswered(false);
         aQ.setAnsweredCorrectly(false);
      }
         
   }
   
} // CLASS
