<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Predictions - ERRO!</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<link rel="stylesheet" href="https://bootswatch.com/slate/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>       
  </head>
  <body class = "text-center">
  	<div style = "height:5em;"></div>
  	<div class = "alert alert-warning fade in">
  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		<div class = "container-fluid" >
    		<span >ERRO!</span> <br/>
    		<% out.println( request.getAttribute("error") ); %>
  		</div>
  	</div>
	<input type="button" name="to_home" value="Voltar" onclick=" window.location = '..' " class="btn btn-default"/>
  	<input type="button" value="Página Inicial" onclick=" window.location = '/Predicoes/home.jsp' " class="btn btn-default"/>
  </body>
</html>
