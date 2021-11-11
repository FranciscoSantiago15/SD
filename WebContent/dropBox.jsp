<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="actions.DropBoxAction" %>
    
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "stylesheet" href = "geral.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">



<title>DropMusic</title>

</head>
<body>
	code: <c:out value="${(code == null) ? code == null : code}"/><br>
	authUrl: <c:out value="${authUrl}"/><br>

	<c:choose>
		<c:when test="<%=request.getParameter(\"code\")==null%>">
			TESTING:<c:redirect url="${authUrl}"/>;
		</c:when>
		<c:otherwise>
			<c:out value="index.jsp"/>
		</c:otherwise>
	</c:choose>
</body>

</html>