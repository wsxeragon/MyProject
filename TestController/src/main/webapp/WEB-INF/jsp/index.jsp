<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@page import="cn.inphase.domain.Customer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>HELLO: <%= request.getAttribute("name") %> </h1>
<h2>NO.${requestScope.customer.id}  </h2>
<h2>CODE: ${requestScope.customer.account}</h2>


${requestScope.members}

<h2>MEMBER</h2>

 <c:forEach var="m" items="${requestScope.members}">
      <h4>${m.account}</h4>   
</c:forEach>
</body>
</html>