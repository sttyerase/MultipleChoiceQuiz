package com.dbmi.demos.quiz;

import java.util.Vector;

/*
 * @author Daniel B. Moore <p>
 * <p>A class the models the components of a quiz question, containing the
 * question, the possible answers and an identifier of the correct answer. </p>
 */
public class Question
{
   /** The text of the question */
   private String questionText;
   /** A list of possible choices. */
   private Vector<String> choiceList = new Vector<>(100);
   /** The index of the correct answer*/
   private int theAnswer = 0;
   /** The explanation of the correct answer */
   private String explanation = null;
   /** Is this question answered? */
   private boolean answered;
   /** Was it answered correctly? */
   private boolean answeredCorrectly = false;

   public Question(){
      super();
      this.answered = false;
      questionText = "";
   }

   /**
    *  @return a string with the text of the question.
    */
   public String getQuestionText(){
      return questionText;
   } // GETQUESTIONTEXT()

   public Vector<String> getChoiceList(){
      return choiceList;
   }

   public String getChoiceAt(int i){
      return choiceList.elementAt(i);
   }

   /*
    * @return the String of the correct answer.
    */
   public String getCorrectAnswer(){
      return choiceList.elementAt(theAnswer);
   }

   public boolean getAnswered(){
      return answered;
   }

   public boolean getAnsweredCorrectly(){
      return answeredCorrectly;
   }

   public int getCorrectAnswerNumber(){
      return theAnswer;
   }

   public String getExplanation(){
      return explanation;
   }

   public String getChoiceElementAt(int i){
      return choiceList.elementAt(i);
   }

   public void addChoiceElement(String aChoice){
      choiceList.addElement(aChoice);
   }

   public void insertChoiceElementAt(String aChoice,int i){
      choiceList.insertElementAt(aChoice,i);
   }

   public void setQuestionText(String text){
      this.questionText = text;
   }

   public void setAnswered(boolean torf){
      answered = torf;
   }

   public void setAnsweredCorrectly(boolean yesno){
      answeredCorrectly = yesno;
   }

   public void setExplanation(String expl){
      explanation = expl;
   }

   public void setCorrectAnswerNumber(int aNum){
      this.theAnswer = aNum;
   }

   public void trimChoiceToSize(){
      choiceList.trimToSize();
   }

   public java.util.Enumeration<String> choiceElements(){
      return choiceList.elements();
   }

}
