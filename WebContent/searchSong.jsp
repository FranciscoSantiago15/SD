<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<html>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel = "stylesheet" href = "searchSong.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">


<title>Search Song</title>

<body>
	<h1><b>Pesquisar Musica</b></h1>
	
  	<br><br><br>
  	
  	<div style = "text-align:center;">
		<form class="modal-content animate" action="searchSong" method="post">  	
			<label for="nomeMusica"><b>Nome</b></label>
			<input type="text" placeholder="Enter name" name="nomeMusica"/>
			<br>
			<select name="tipo">
			  <option value="Album">Album</option>
			  <option value="Musica">Musica</option>
			</select>
			
			<br><br>
			<input type = "submit" value = "Submit">
		</form><br><br>
		
		<form action="voltar" method="post">
			<button type = "submit" style = "padding: 10px 24px; font-size: 15px; background-color: #00cc99; color: white; border: none; cursor: pointer; border-radius:7px;">Back to homepage</center>  
		</form>
	
	</div>
	
</body>
</html>

