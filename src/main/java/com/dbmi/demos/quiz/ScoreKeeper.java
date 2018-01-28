package com.dbmi.demos.quiz;

/*
 * @author Daniel B. Moore <p>
 */
public class ScoreKeeper
{
   private int totalAnswered;
   private int totalCorrect;
   private int totalQuestions;

   public ScoreKeeper(){
      super();
      totalAnswered = 0;
      totalCorrect = 0;
      totalQuestions = 0;
   } // Constructor

   public int getTotalAnswered(){
      return totalAnswered;
   }

   public int getTotalCorrect(){
      return totalCorrect;
   }
   
   public int getTotalQuestions(){
      return totalQuestions;
   }
   
   public void setTotalAnswered(int tot){
      totalAnswered = tot;
   }
   
   public void setTotalCorrect(int tot){
      totalCorrect = tot;
   }
   
   public void setTotalQuestions(int tot){
      totalQuestions = tot;
   }

   public void scoreQuiz(QuestionList qList){
      Question thisQ = null;
      totalAnswered = 0;
      totalCorrect = 0;
      totalQuestions = qList.size();
      java.util.Enumeration myE = qList.elements();
      while(myE.hasMoreElements()){
         thisQ = (Question)myE.nextElement();
         if(thisQ.getAnswered())totalAnswered++;
         if(thisQ.getAnsweredCorrectly())totalCorrect++;
      }
   }

   public float getPercentCorrect(){
      return((float)totalCorrect/(float)totalAnswered);
   }
   
   public void reInitialize(){
      this.setTotalAnswered(0);
      this.setTotalCorrect(0);
   }

} // CLASS
