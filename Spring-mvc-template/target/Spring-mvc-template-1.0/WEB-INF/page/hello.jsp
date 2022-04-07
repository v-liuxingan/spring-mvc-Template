<%--
  Created by IntelliJ IDEA.
  User: liuxingan3148
  Date: 2022/4/7
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ch">
<head>
  <title>Spring MVC</title>

  <spring:url value="/resources/core/css/hello.css" var="coreCss" />
  <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
  <link href="${bootstrapCss}" rel="stylesheet" />
  <link href="${coreCss}" rel="stylesheet" />
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Spring 3 MVC Project @JavaConfig</a>
    </div>
  </div>
</nav>

<div class="jumbotron">
  <div class="container">
    <h1>${title}</h1>
    <p>
      <c:if test="${not empty name}">
        Hello ${name}
      </c:if>

      <c:if test="${empty name}">
        Welcome Welcome!
      </c:if>
    </p>
    <p>
      <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
    </p>
  </div>
</div>

<div class="container">

  <div class="row">
    <div class="col-md-4">
      <h2>Heading</h2>
      <p>ABC</p>
      <p>
        <a class="btn btn-default" href="#" role="button">View details</a>
      </p>
    </div>
    <div class="col-md-4">
      <h2>Heading</h2>
      <p>ABC</p>
      <p>
        <a class="btn btn-default" href="#" role="button">View details</a>
      </p>
    </div>
    <div class="col-md-4">
      <h2>Heading</h2>
      <p>ABC</p>
      <p>
        <a class="btn btn-default" href="#" role="button">View details</a>
      </p>
    </div>
  </div>


  <hr>
  <footer>
    <p>&copy; hello there </p>
  </footer>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</body>
</html>
