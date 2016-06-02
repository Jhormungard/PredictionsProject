/* Função que só será executada uma vez! */
function consulta(){
    var l_g = document.getElementById("my_query").value;
    var aux = JSON.parse(l_g);
    var x;
    var inner = "";

    /* Impressão do cabeçalho (colunas da tabela) */
    inner += "<tr>\n<th>Date</th>\n<th>Open</th>\n<th>High</th>\n<th>Low</th>"
          +"\n<th>Close</th>\n<th>Volume</th>\n<th>Adj_clo</th>\n</tr>\n";

    /* for loop para objetos */
    for(x in aux.tupla){
        inner += "<tr>\n";
        inner += "<td>"+aux.tupla[x].date+"</td>\n";
        inner += "<td>"+aux.tupla[x].open+"</td>\n";
        inner += "<td>"+aux.tupla[x].high+"</td>\n";
        inner += "<td>"+aux.tupla[x].low+"</td>\n";
        inner += "<td>"+aux.tupla[x].close+"</td>\n";
        inner += "<td>"+aux.tupla[x].volume+"</td>\n";
        inner += "<td>"+aux.tupla[x].adj_clo+"</td>\n";
        inner += "</tr>\n";
    }

    document.getElementById("tabela_gerada").innerHTML = inner;
}
