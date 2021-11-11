<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel = "stylesheet" href = "searchSong.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">


<title>Search Details</title>

<body>
	<h1><b>Pesquisar Detalhes</b></h1>
	
  	<br><br><br>
  
  	<div style = "text-align:center;">
	<form class="modal-content animate" action="searchDetails" method="post">  	
		<label for="nomeProcura"><b>Nome</b></label>
		<input type="text" placeholder="Enter name" name="nomeProcura"/>
		<br>
		<select name="tipo">
		  <option value="Album">Album</option>
		  <option value="Artista">Artista</option>
		</select>
		<br><br>
		<input type="submit" value="Submit">
	</form>
	</div>
</body>
</html>