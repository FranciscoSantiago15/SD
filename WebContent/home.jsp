<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel = "stylesheet" href = "geral.css">
	<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">
	<script src="ws2.js"></script>
    
	<title>DropMusic</title>

</head>
<body>
	<div id="mySidenav" class="vertical-menu">
	<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		<a href="#" class="active"><b>HOME</b></a>
		<a href="gerir.jsp"><b>1. Gerir artistas, álbuns e músicas</b></a>
		<a href="searchSong.jsp"><b>2. Pesquisar músicas</b></a>
		<a href="searchDetails.jsp"><b>3. Consultar detalhes sobre álbuns e artistas</b></a>
		<a href="writeReview.jsp"><b>4. Escrever crítica a álbum</b></a>
		<a href="privilegeEditor.jsp"><b>5. Dar privilégios de editor a um utilizador</b></a>
		<a href="#"><b>6. Transferir músicas para o servidor</b></a>
		<a href="dropBoxList"><b>7. Partilhar músicas</b></a>
		<a href="#"><b>8. Transferir músicas do servidor</b></a>
	</div>
	
	<div id="main">
		<h1><b>Bem-Vindo ao DropMusic</b></h1>
		<br>
  		<span style="font-size:30px; cursor:pointer; padding: 5px 5px;" onclick="openNav()">&#9776; Open Menu</span>
  		<br><br>
  		<p><b>Notificações</b></p>
  		<div class = "vertical-box">
		    <div id="container"><div id="history"></div></div> 
		</div>
		
		
		<form action="exit" method="post">
			<button type = "submit" style = "padding: 10px 24px; font-size: 15px; background-color: #00cc99; color: white; border: none; cursor: pointer; border-radius:7px;">LogOut</center>  
		</form>
	</div>
	
	
	<script>
		function openNav() {
		  document.getElementById("mySidenav").style.width = "250px";
		  document.getElementById("main").style.marginLeft = "250px";
		  document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
		}
		
		function closeNav() {
		  document.getElementById("mySidenav").style.width = "0";
		  document.getElementById("main").style.marginLeft= "0";
		  document.body.style.backgroundColor = "white";
		}
		
		function closeWin() {
		    window.close();
		}
	</script>
	

</body>

</html>