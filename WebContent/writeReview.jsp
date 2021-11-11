<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="projsd.RMIInterface" %>
<%@page import="java.rmi.registry.LocateRegistry" %>
<%@page import="java.util.ArrayList" %>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "stylesheet" href = "writereview.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">



<title>Critica</title>
</head>


<body>

<h1><b>Inserir Crítica</b></h1>



<div style = "text-align:center;">
		<div class="row">
			<div class="col-25">
				<label for="Album"><b>Nome do álbum</b></label>
			</div>
			<div class="col-75">
				<select name="Album" form = "wrR">		
				<% RMIInterface server = (RMIInterface) LocateRegistry.getRegistry(7000).lookup("DropMusic");
				   ArrayList<String> aux = server.getAlbuns();
				   for (int i = 0; i < aux.size(); i++){ %>
					<option value=<%=i%>> <%=aux.get(i)%> </option> 
				<%} %>
				</select>
			</div>
		</div>

	<form class = "modal-content animate" action = "writeReview" method = "post" id="wrR">

		<div class="slidecontainer">
			<br>
 			<p><b>Pontuacao:</b></p>
 			<br>
			<input type="range" style = position: center; text-aling:center; min="0" max="500" value="250" class="slider" id="myRange" oninput="myFunction(this.value)" name = "pontuacao">
			<br>
			<span> <b>Value: <p id="demo" style = "color:#DC143C;"></p></b></span>
			
			
			<script>
			function myFunction(val) {
				val = val/100;
			    document.getElementById("demo").innerHTML = val; 
			}
			</script>
		</div>
  
		<div class="row">
			<div class="col-25">
				<label for="critica"><b>Critica:</b></label>
		    </div>
			<div class="col-75">
				<textarea id = "critica" name="critica" placeholder = "Enter the review" style = "height:200px"></textarea>
			</div>
		</div>
		<br>
		<div class="row">
			<input type="submit" value="Submit">
		</div>
	</form>
	
	<form action="voltar" method="post">
		<button type = "submit" style = "padding: 10px 24px; font-size: 15px; background-color: #00cc99; color: white; border: none; cursor: pointer; border-radius:7px;">Back to homepage</center>  
	</form>
	
</div>

</body>
</html>