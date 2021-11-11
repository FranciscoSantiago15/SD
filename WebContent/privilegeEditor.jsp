<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="projsd.RMIInterface" %>
<%@page import="java.rmi.registry.LocateRegistry" %>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel = "stylesheet" href = "searchSong.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">


<title>Give Privilege</title>

</head>
<body>

<h1>Escolha utilizador para dar privilégio:</h1>
<br><br>

<div style = "text-align:center;">
	<select name="nomeParaEditor" form ="givePr"> 	
	<%	RMIInterface server = (RMIInterface) LocateRegistry.getRegistry(7000).lookup("DropMusic");
		ArrayList<String> aux = server.getAllUsersNoPrivilege();
		for(int i = 0; i < aux.size(); i++) { %>
	<option value = <%=aux.get(i)%>> <%=aux.get(i)%> </option>
	<%	}%>
	</select>
	
	
<s:form action="givePrivilege" method="post" id="givePr">
	<center><input type="submit"></center>
</s:form>
</div>

</body>

</html>