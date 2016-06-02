<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Predictons - Home</title>
        <link rel="stylesheet" href="style.css" type="text/css" >
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=2.0">
  		<link rel="stylesheet" href="https://bootswatch.com/slate/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            function selectEmpresa(cod_empresa)
            {
                window.location = 'paginas/data_inicial.jsp?Empresa='+cod_empresa;
            }
        </script>
    </head>
    <body class = "text-center">
    
  		<div style = "height:5em;"></div>
    	<div class = "page-header">
        	<h1><strong>Predictions</strong></h1> <br>
        </div>
        <div class = "container-fluid">
            <form method = "GET" action = '' role = "form">
            	<label class = "control-label col-sm-4" for = "Empresa"> Qual ação deseja consultar e prever? (selecione uma)</label>
            	<div class="form-group col-sm-4">
                	<select class = "form-control" id = "Empresa" name = "Empresa" >
                    	<option value="ABEV3">Ambev</option>
                    	<option value="EMBR3">Embraer</option>
                    	<option value="PETR4">Petrobrás</option>
                    	<option value="VALE5">Vale do Rio Doce</option>
                	</select> <br/>
                	
  					<div style = "height:5em;"></div>
                	<script type="text/javascript">
                    	function selectOption()
                    	{
                        	var e = document.getElementById("Empresa");
                        	var empresa = e.options[e.selectedIndex].value;
                        	selectEmpresa(empresa);
                    	}
                	</script>
                	</div>
                <input type="button" name="theButton" value="Select" onclick="selectOption()" class="btn btn-default">
            </form>
        </div>
    </body>
</html>
