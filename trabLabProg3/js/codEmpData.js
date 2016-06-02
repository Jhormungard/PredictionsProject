/* Documento a ser executado em:
    - Início de consulta.jsp;
    */

var parametros = window.location.search.split("?");
/* Armazenar vetor de substrings da URL. As substrings vieram da partição da URL por "?" */

var Parametros = parametros[1].split("=");
/* Pega-se a segunda substring, faz-se outra partição (só que agora com "="),
armazena-se outro vetor de substrings. Note que, agora, parametros[1] é o "codigo_empresa" */

var my_cod = document.getElementById("my_cod");
my_cod.setAttribute('value', Parametros[1]);

var my_dat = document.getElementById("my_dat");
my_dat.setAttribute('value', Parametros[3]);

var my_query = document.getElementById("my_query");
my_dat2.setAttribute('value', Parametros[5]);
