<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "stylesheet" href = "gerir.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">


<title>Gestor</title>
</head>
<body>
<h1>Gerir, Criar, Apagar</h1>
<br><br>

<!------- CRIAR ALBUM------->
<center><button onclick="document.getElementById('id01').style.display='block'" style="width:auto; font-size: 20px;"><b>Criar Album</b></button></center>

<div id="id01" class="modal">
  <form class="modal-content animate" action="criarAlbum " method="post">
  	
    <div class="container">
      <p><b>Novo Album</b></p>
      <label for="nomeFich"><b>Titulo:</b></label>
      <input type="text" placeholder="Enter Name" name="nomeFich"/>
      
      <label for="listaMusicas"><b>Lista de Musicas:</b></label>
      <input type="text" placeholder="Enter Musics ex.[musica 1, musica 2, ...]" name="listaMusicas"/>

      <label for="dataLancamento"><b>Data Lançamento:</b></label>
      <input type="text" placeholder="Enter Date" name="dataLancamento"/>
      
      <label for="generos"><b>Generos:</b></label>
      <input type="text" placeholder="Enter Generos ex.[pop, rock, ...]" name="generos"/>
      
      <label for="concertos"><b>Concertos:</b></label>
      <input type="text" placeholder="Enter Shows ex.[10-10-2019, 25-02-2019, ...]" name="concertos"/>
        
      <button type="submit">Criar</button>
    </div>
    
    <div class="container" style="background-color:#f1f1f1">
    	<button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
    </div>

  </form>
</div>


<!------- CRIAR MUSICA------->
<center><button onclick="document.getElementById('id02').style.display='block'" style="width:auto; font-size: 20px;"><b>Criar Musica</b></button></center>

<div id="id02" class="modal">
  <form class="modal-content animate" action="criarMusica" method="post">
  	
    <div class="container">
      <p><b>Musica</b></p>
      <label for="nomeFich"><b>Nome:</b></label>
      <input type="text" placeholder="Enter Name" name="nomeFich"/>

      <label for="listaArtistas"><b>Lista de Artistas:</b></label>
      <input type="text" placeholder="Enter Artists ex.[artista 1, artista 2, ...]" name="listaArtistas"/>
      
      <label for="listaGrMusicais"><b>Lista de Gr Musicais:</b></label>
      <input type="text" placeholder="Enter Artists ex.[grupo M 1, grupo M 2, ...]" name="listaGrMusicais"/>
      
      <label for="listaAlbuns"><b>Lista de Albuns:</b></label>
      <input type="text" placeholder="Enter Artists ex.[Album 1, Album 2, ...]" name="listaAlbuns"/>
      
      <label for="historia"><b>Historia:</b></label>
      <input type="text" placeholder="Enter history" name="historia"/>
        
      <button type="submit">Criar</button>
    </div>
    
    <div class="container" style="background-color:#f1f1f1">
    	<button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn">Cancel</button>
    </div>

  </form>
</div>


<!------- EDITAR ------->
<center><button onclick="document.getElementById('id03').style.display='block'" style="width:auto; font-size: 20px;"><b>Editar</b></button></center>

<div id = "id03" class="modal">
  
	<form class="modal-content animate" action="editar" method="post">
    	<div class="container">
      		<p><b>EDITAR</b></p>
      		<label for="nomeProcura"><b>Original</b></label>
     		<input type="text" placeholder="Enter Name for Original File" name="nomeProcura"/>
     		
     		<select name="tipo">
		  		<option value="Album_Titulo">Album - Titulo</option>
				<option value="Album_data">Album - DataLancamento</option>
				<option value="Album_Generos">Album - Generos</option>
				<option value="Album_Concertos">Album - Concertos</option>
				<option value="Musica_Nome">Musica - nome</option>
				<option value="Muscia_Artistas">Musica - Artistas</option>
				<option value="Musica_GrMusicais">Musica - Grp Musicais</option>
				<option value="Musica_Historia">Musica - Historia</option>
			</select> 
      
     		<label for="info"><b>Info</b></label>
      		<input type="text" placeholder="Enter Info" name="info"/>      
              
      		<button type="submit">Editar</button>
    </div>

	<div class="container" style="background-color:#f1f1f1">
    	<button type="button" onclick="document.getElementById('id03').style.display='none'" class="cancelbtn">Cancel</button>
    </div>	
  </form>
</div>


<!------- APAGAR ------->
<center><button onclick="document.getElementById('id04').style.display='block'" style="width:auto; font-size: 20px;"><b>Apagar</b></button></center>

<div id = "id04" class="modal">
  
  <form class="modal-content animate" action="apagar" method="post">
    <div class="container">
      <p><b>APAGAR</b></p>
      <label for="nomeFich"><b>Nome</b></label>
      <input type="text" placeholder="Enter Username" name="nomeFich"/>
      
		<select name="tipo">
			<option value="Album">Album</option>
			<option value="Musica">Musica</option>
		</select>
        
      <button type="submit">Apagar</button>
    </div>

	<div class="container" style="background-color:#f1f1f1">
    	<button type="button" onclick="document.getElementById('id04').style.display='none'" class="cancelbtn">Cancel</button>
    </div>	
  </form>
</div>

</body>
<br><br><br>
<form id="b1" action="voltar" method="post">
	<center><button type = "submit" style = "font-size: 15px; background-color: #00cc99; color: white; border: none; cursor: pointer; border-radius:7px;">Back to homepage</button></center>  
</form>

<script>
	// Get the modal
	var modal = document.getElementById('id01');
	var modal2 = document.getElementById('id02');
	var modal3 = document.getElementById('id03');
	var modal4 = document.getElementById('id04');
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
	
	window.onclick = function(event) {
	    if (event.target == modal2) {
	        modal2.style.display = "none";
	    }
	}
	
	window.onclick = function(event) {
	    if (event.target == modal3) {
	        modal3.style.display = "none";
	    }
	}
	
	window.onclick = function(event) {
	    if (event.target == modal4) {
	        modal4.style.display = "none";
	    }
	}
</script>

</html>


</body>
</html>