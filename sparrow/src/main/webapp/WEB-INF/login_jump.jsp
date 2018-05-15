<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>sparrow</title>
    <!--[if !IE]> -->
	<script src="component/ace/components/jquery/dist/jquery.js"></script>
	<!-- <![endif]-->
	
	<!--[if IE]>
	<script src="component/ace/components/jquery.1x/dist/jquery.js"></script>
	<![endif]-->
    
    <script type="text/javascript">
    $().ready(function() {
    	top.location.href="<%=basePath%>logingo";
    });
    </script>
  </head>
  
  <body></body>
</html>
