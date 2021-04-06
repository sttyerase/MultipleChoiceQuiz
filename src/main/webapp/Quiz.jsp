<%@ page contentType="text/html; charset=UTF-8"
         import="com.dbmi.demos.quiz.*"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link REL=StyleSheet HREF="QuizSite.css" TYPE="text/css"/>
    <title>Multiple Choice Quiz Application (MCQuiz)</title>
    <%
    String thePath = config.getServletContext().getRealPath("/");
    config.getServletContext().log("PATH: " + thePath);
    String quizDir = thePath + "/quizes";
    HttpServlet myManager = (HttpServlet)config.getServletContext().getAttribute("manager");
      myManager.getInitParameter("quizdir");
    %>
  </head>
  <body>
    <h1>Welcome to the Quiz Place</h1>
  </body>
</html>