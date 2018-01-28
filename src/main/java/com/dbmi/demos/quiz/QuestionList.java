package com.dbmi.demos.quiz;

import java.util.*;

/*
 * @author Daniel B. Moore <p>
 */
public class QuestionList extends java.util.Vector
{
   private int currentQuestionNumber = 0;
   
   public QuestionList(){
      super();
   } // CONSTRUCTOR
   
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
      if(this.currentQuestionNumber < this.size())return true;
         else return false;
   }
   
   public void reInitialize(){
      Question aQ = null;
      Enumeration e = this.elements();
      while(e.hasMoreElements()){
         aQ=(Question)e.nextElement();
         aQ.setAnswered(false);
         aQ.setAnsweredCorrectly(false);
      }
         
   }
   
} // CLASS
