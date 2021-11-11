<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="ISO-8859-1">

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<title>teste</title>
</head>
<body>

<h1>TESTE TESTE TESTE TESTE </h1>
<form  action="criarAlbum" method="post">
    <p>
        Titulo: <input type="text" name="nomeFich">
        Data Lancamento: <input type="text" name="dataLancamento">   
        <span class="remove">Remove</span>
    </p>
    <p>
        <span class="add">Add fields</span>
    </p>
    

</form>

</body>


<script>
$(".add").click(function() {
    $("form > p:first-child").clone(true).insertBefore("form > p:last-child");
    return false;
});

$(".remove").click(function() {
    $(this).parent().remove();
});
</script>

</html>