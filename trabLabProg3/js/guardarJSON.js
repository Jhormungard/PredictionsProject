/* Documento a ser executado em:
    - Consulta.java (servlet);
    */

function guardarJSON() {
    var cod = document.getElementById("my_cod").value;
    var date0 = document.getElementById("my_dat").value;
    var text = document.getElementById("my_query").value;

    window.location = '../paginas/consulta.jsp?Empresa='+cod+'=Data='+date0+
        '=JSON='+text;
}
