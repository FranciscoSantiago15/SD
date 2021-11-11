<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel = "stylesheet" href = "index.css">
<link rel="icon" href="https://images.vexels.com/media/users/3/139263/isolated/preview/41eddb1e9e45cd41343ccf24e61f4690-hexagon-shape-silhouette-by-vexels.png">


<title>DropMusic</title>

</head>

<body>
<h2>DropMusic</h2>

<br><br><p><b>Let the music drop</b></p>
<br><br><br>

<!------- DropBox LOGIN ------->
 <form action="dropBox" method="post" style="border: 0px;">
  	<center>
  		<button type="submit" style=" width: auto; font-size: 20px;"><b>Login DropBox</b></button>
  	</center>
 </form>


<!------- LOGIN ------->
<center><button onclick="document.getElementById('id01').style.display='block'" style="width:auto; font-size: 20px;"><b>Login</b></button></center>

<div id="id01" class="modal">
  <form class="modal-content animate" action="login" method="post">
  	
    <div class="container">
      <p><b>LOGIN</b></p>
      <label for="username"><b>Username</b></label>
      <input type="text" placeholder="Enter Username" name="username"/>

      <label for="password"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="password"/>
        
      <button type="submit">Login</button>
    </div>
    
    <div class="container" style="background-color:#f1f1f1">
    	<button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
    </div>

  </form>
</div>

<!------- REGISTER ------->
<center><button onclick="document.getElementById('id02').style.display='block'" style="width:auto; font-size: 20px;"><b>Register</b></button></center>

<div id = "id02" class="modal">
  
  <form class="modal-content animate" action="register" method="post">
    <div class="container">
      <p><b>REGISTER</b></p>
      <label for="username"><b>Username</b></label>
      <input type="text" placeholder="Enter Username" name="username"/>

      <label for="password"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="password"/>
        
      <button type="submit">Registar</button>
    </div>

	<div class="container" style="background-color:#f1f1f1">
    	<button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn">Cancel</button>
    </div>	
  </form>
</div>

</body>
<div>
      <br><br><br><br>
      <center><input type = "button" style = "font-size: 22px; background-color: #19334d; color: white; border: none; cursor: pointer; border-radius:10px;" value=" Exit " onclick = "window.close()"></center>  
</div>

<script>
// Get the modal
var modal = document.getElementById('id01');
var modal2 = document.getElementById('id02');
var modal3 = document.getElementById('id03');


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
</script>

</html>
