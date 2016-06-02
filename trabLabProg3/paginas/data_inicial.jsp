<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Predictions - Data Inicial</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
  		<link rel="stylesheet" href="https://bootswatch.com/slate/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        
    </head>

    <body class = "text-center">
    <div class="alert alert-info fade in">
    	<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  		Informe a data inicial para a analise.
	</div>
    	<div class = "container-fluid">
            <form action="../servlet/Consulta" method="POST">
                <input type="radio" id="my_cod" name="codigo_empresa" value="" checked /><br/>
                    <label class = "control-label col-sm-2" for = "my_dat">Data Inicial:</label>
                    <div class="form-group col-sm-4">
                    	<input class ="form-control" type = "date" id = "my_dat"
                    	 name = "data_inicial" placeholder = "dd/mm/yyyy"/><br/>
                    </div><br/>
  				<div style = "height:5em;"></div>
                <input type="button" name="to_home" value="Voltar" onclick=" window.location = '..' " class="btn btn-default"/>
                <input id="NextPage" type="submit" name="select_date" value="Confirma" class="btn btn-default"/>
            </form>
            
  			<div style = "height:5em;"></div>
            <script type="text/javascript" src="../js/codEmpresa.js"></script>
    	</div>
    </body>
</html>
