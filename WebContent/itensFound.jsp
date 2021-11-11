<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "stylesheet" href = "itensfound.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">


<head>
	<title>Itens Found</title>
</head>
<body>
	<h1>Itens Encontrados</h1>
    <br><br><br>
    
	<p><b>Resultados da Pesquisa: </b><br><br> ${session.outString}</p>

	<br><br>
	
	<form action="voltar" method="post">
		<button type = "submit" style = "padding: 10px 24px; font-size: 15px; background-color: #00cc99; color: white; border: none; cursor: pointer; border-radius:7px;">Back to homepage</center>  
	</form>
</body>
</html>