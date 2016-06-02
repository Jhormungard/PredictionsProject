<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Predictions</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
  		<link rel="stylesheet" href="https://bootswatch.com/slate/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        
    </head>
    <body class = "text-center">
    	<div class="alert alert-success fade in">
    		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  			<strong>Success!</strong>
		</div>
		
  		<div style = "height:5em;"></div>
    	<div class = "container-fluid">
        	<script>
            	function back()
            	{
                	window.location = 'consulta.jsp?Empresa='+Variaveis[1]+"=Data="+Variaveis[3];
            	}
        	</script>
        	<script>
            	//o split separa a string quando encontra o ?
            	var variaveis = location.search.split("?");
            	var Variaveis = variaveis[1].split("=");
        	</script>
        	<script>
            	function _calc(){
                	var e = location.indexOf();
                	write(e);
            	}
        	</script>
        	<script>
            	var Dat = Variaveis[3].split("-");
            	var _dataIn = Dat[0]+"/"+Dat[1]+"/"+Dat[2];
        	</script>
            <form>
                <strong >Data Inicial:</strong>
                <script>
                    document.write(_dataIn);
                </script><br>
                Nome:
                <script>
                    document.write(Variaveis[1]);
                </script><br>
                
  				<div style = "height:5em;"></div>                          
                <div class = "jumbotron">
                <table class="table table-hover table-condensed">
                	<thead>
                		<tr>
                			<th>Open</th>
        					<th>High</th>
        					<th>Low</th>
        					<th>Close</th>
        					<th>Volume</th>
        					<th>Adj_clo</th>
      					</tr>
    				</thead>
    				<tbody>
      					<tr>
        					<td id="open" onLoad="_calc()"></td>
        					<td id="high" onLoad="_calc()"></td>
        					<td id="low" onLoad="_calc()"></td>
        					<td id="close" onLoad="_calc()"></td>
        					<td id="volume" onLoad="_calc()"></td>
        					<td id="adj_clo" onLoad="_calc()"></td>
      					</tr>
    				</tbody>
  				</table>
  				
  				<div style = "height:5em;"></div>
  				</div>
                <input type="button" name="theButton" value="Voltar" onclick="back()" class="btn btn-default"/>
            </form>
        </div>
    </body>
</html>
