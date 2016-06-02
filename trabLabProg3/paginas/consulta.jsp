<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Predictions - Consulta</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
  		<link rel="stylesheet" href="https://bootswatch.com/slate/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script type="text/javascript">
              function back(){
                  window.location = 'data_inicial.jsp?Empresa='+Parametros[1];
              }
              function _toCont(_buttonName){
                  window.location = 'metodo.jsp?Empresa='+Parametros[1]+'=Data='+Parametros[3]+'='+_buttonName;
              }
        </script>
    </head>

    <body class = "text-center">

  		<div style = "height:5em;"></div>
    	<div class = "container-fluid">
        	<form method = "POST" action = "../servlet/Metodos" role = "form">
            	<input type="radio" id="my_cod" name="codigo_empresa" value="" checked />
            	<input type="radio" id="my_dat" name="data_inicial" value="" checked /><br/>
            	<!-- <input type="radio" id="my_query" name="json_pegado" value="" checked /> <br/> -->            	
				<div class="alert alert-info fade in">
    				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  					Selecione um dos métodos abaixo para Predição.
				</div>
				<br/>
        <label class = "control-label col-sm-4" for="Select_Method">Qual o método a ser usado?(selecione um)</label>
              <br/>
				<div class = "form-group col-sm-4">
            		<select class = "form-control" id = "Select_Method" name = "metodo">
                		<option value="1">Interpolação de Fourier</option>
                		<option value="2">Interpolação Polinomial</option>
                		<option value="3">API Predictions</option>
            		</select> <br>
            	</div>

  				<div style = "height:5em;"></div>
            	<input type="submit" value="Confirma" class="btn btn-default"/>
        	</form>
        	<br/>
  			<div style = "height:5em;"></div>
        	<!--<p id="lis_ger"><% out.print(request.getAttribute("lista_gerada"));%></p>
        	<table id="tabela_gerada" border="1" cellpadding="5" cellspacing="5"></table>

        	<script type="text/javascript" src="../js/JSONconsulta.js"></script>
        	<input type="button" value="Gerar Tabela" onclick="consulta()"/>-->

        	<script type="text/javascript" src="../js/codEmpData.js"></script>
    	</div>
    </body>
</html>
