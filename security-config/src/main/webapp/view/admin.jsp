<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css"/>
<script src="../resources/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container">
<div>
<h1>Hello Welcome from Admin Page</h1>
<a href="/" class="btn btn-primary">Home Page</a>

<sf:form method="post" class="d-inline-block" action="/logout" >
<button type="submit" class="btn btn-danger">Logout</button>
</sf:form>
</div>
</div>

</body>
</html>