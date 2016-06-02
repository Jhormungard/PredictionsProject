import java.sql.Date;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;   /*Pois o JDBC está relacionado com SQLException, ao dar erro.*/

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.Long;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
/**
 *
 * @author login
 */
@WebServlet(name = "Consulta", urlPatterns = {"servlet/Consulta"})
public class Consulta extends HttpServlet {


  public class Objeto {
      private Date date;
      private double open, high, low, close;
      private int volume;
      private double adj_clo;

          public Date getDate() {
              return date;
          }
              public void setDate(Date date) {
                  this.date = date;
              }


          public double getOpen() {
              return open;
          }
              public void setOpen(double open) {
                  this.open = open;
              }

          public double getHigh() {
              return high;
          }
              public void setHigh(double high) {
                  this.high = high;
              }

          public double getLow() {
              return low;
          }
              public void setLow(double low) {
                  this.low = low;
              }

          public double getClose() {
              return close;
          }
              public void setClose(double close) {
                  this.close = close;
              }

          public int getVolume() {
              return volume;
          }
              public void setVolume(int volume) {
                  this.volume = volume;
              }

          public double getAdj_clo() {
              return adj_clo;
          }
              public void setAdj_clo(double adj_clo) {
                  this.adj_clo = adj_clo;
              }

/*
          public String json_tupla() {
              String aux = "{ ";
                aux += "date: \""+(this.getDate()).toString()+"\", \n";
                aux += "open: \""+String.valueOf(this.getOpen())+"\", \n";
                aux += "high: \""+String.valueOf(this.getHigh())+"\", \n";
                aux += "low: \""+String.valueOf(this.getLow())+"\", \n";
                aux += "close: \""+String.valueOf(this.getClose())+"\", \n";
                aux += "volume: \""+String.valueOf(this.getVolume())+"\", \n";
                aux += "adj_clo: \""+String.valueOf(this.getAdj_clo())+"\" }\n";
              return aux;

          }
*/
  }




  public class FabricaConexoes {

      public Connection conexaoBD() {
          try {
              /*Forçar busca do DriverManager*/
              Class.forName("org.postgresql.Driver");
          } catch (ClassNotFoundException e) {
              System.out.println("Class not found " + e);
          }
          try {
          /*Método para adquirir conexão com o banco de dados.*/
              return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Cotacoes", "alguem2", "1234");
          } catch( SQLException sqlExc ) {
              throw new RuntimeException( sqlExc );
          } /*finally {}*/
      }

      public void disconectarBD(Connection conexao) {
          try {
          /*Método para desconectar do BD.*/
              conexao.close();
          } catch( SQLException sqlExc ) {
              throw new RuntimeException( sqlExc );
          } /*finally {}*/
      }
  }



  public class ObjetoDAO {

      private final Connection conexao;   /*Isso aqui tem que ser Const!!! Pois não se altera após construção.*/

      /*Construtor:*/
      public ObjetoDAO() {
          this.conexao = new FabricaConexoes().conexaoBD();   /* Estabelecer conexão com Servidor do BD.*/
      }
/*
      //Método de Inserção no BD:
      public void insert(String table, Objeto obj) {
          //String já padronizada no comando SQL com recurso do PreparedStatement!
          String command = "INSERT INTO public."+
                  table+
                  " (Date,Open,High,Low,Close,"+
                  "Volume,Adj_clo) values(?,?,?,?,?,?,?)";

          try (PreparedStatement stmt = conexao.prepareStatement(command)) {
              //Atribuição dos '?' em seqüência, conforme equivalência no BD.
              stmt.setDate(1, obj.getDate());
              stmt.setDouble(2, obj.getOpen());
              stmt.setDouble(3, obj.getHigh());
              stmt.setDouble(4, obj.getLow());
              stmt.setDouble(5, obj.getClose());
              stmt.setInt(6, obj.getVolume());
              stmt.setDouble(7, obj.getAdj_clo());

              stmt.execute();         //como 'stmt' é um objeto de PreparedStatement, então o parâmetro é void
          }
          catch( SQLException sqlExc ) {      //Já que o try já faz um throw padrão, que será associado ao SQL provavelmente.
              throw new RuntimeException( sqlExc );   //'throw new' tem que ser usado, pois não é classe principal.
          }
          finally {}
      }
*/
      /*Método de Consulta no BD:*/
      public final List<Objeto> read(String table, Date date_0) {
          List<Objeto> tabela = new ArrayList<Objeto>();

          /*String com o comando para selecionar todos os atributos de uma linha*/
          /*Se eu quisesse apenas uma sequencia de termos só especificar em 'WHERE ? = ?'*/
          String command = "SELECT * FROM public."+
                  table+
                  " WHERE Date>=?"+
                  " ORDER BY Date DESC";

          try( PreparedStatement stmt = conexao.prepareStatement(command) ) {
              /*Para receber toda a linha (já que eu usei o SELECT *) terei que ter um objeto de ResultSet para receber de tal.*/

              stmt.setDate(1, date_0);

              ResultSet rs = stmt.executeQuery();     /*como 'stmt' é um objeto de PreparedStatement, então o parâmetro é void*/

              while( rs.next() )           /*Método 'next()' faz o objeto pular a linha*/
              {                            /*Método retorna booleano. Quando não pula a linha, retorna falso.*/
                  if ( rs.getDate("Date").after(date_0) )       /* Só pegar as datas que forem posteriores à inicial indicada */
                  {
                    Objeto tupla = new Objeto();

                    tupla.setDate(rs.getDate("Date"));
                    tupla.setOpen(rs.getDouble("Open"));
                    tupla.setHigh(rs.getDouble("High"));
                    tupla.setLow(rs.getDouble("Low"));
                    tupla.setClose(rs.getDouble("Close"));
                    tupla.setVolume(rs.getInt("Volume"));
                    tupla.setAdj_clo(rs.getDouble("Adj_clo"));

                    tabela.add(tupla);
                  }
              }
              /*Terminado o armazenamento das tuplas na tabela!*/
          }
          catch( SQLException sqlExc ) {
              throw new RuntimeException( sqlExc );
          }
          /*finally{}*/
          return tabela;
      }
/*
      //Método de Atualização no BD:
      public void update(String table, Objeto obj) {
          //Comando para indicar quais atributos serão atualizados (depois do SET), sob a condição do(s) atributo(s) após o WHERE.
          String command = "UPDATE public."+
                  table+
                  "SET Open=?,High=?,Low=?,Close=?,Volume=?,Adj_clo=? "+
                  "WHERE Date=?";

          try ( PreparedStatement stmt = conexao.prepareStatement(command) ) {

              stmt.setDate(1, obj.getDate());
              stmt.setDouble(2, obj.getOpen());
              stmt.setDouble(3, obj.getHigh());
              stmt.setDouble(4, obj.getLow());
              stmt.setDouble(5, obj.getClose());
              stmt.setInt(6, obj.getVolume());
              stmt.setDouble(7, obj.getAdj_clo());

              stmt.execute();
          }
          catch ( SQLException sqlExc ) {
              throw new RuntimeException( sqlExc );
          }
          //finally{}
      }
*/
/*
      //Método de Remoção no BD:
      public void delete(String table, Date date_row) {
          //'id' é o parâmetro para escolher qual será deletado do BD.
          String command = "DELETE FROM public."+
                  table+
                  " WHERE Date=?";

          try ( PreparedStatement stmt = conexao.prepareStatement(command) ) {

              stmt.setDate(1, date_row);

              stmt.execute();
          }
          catch ( SQLException sqlExc ) {
              throw new RuntimeException( sqlExc );
          }
          //finally {}
      }
*/
  }



    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            List<Objeto> pegado = new ArrayList<Objeto>();
            String table = null;
            String s_date = null;
            //String json_pegado = null;

            try{
                PrintWriter out = response.getWriter();
                table = request.getParameter("codigo_empresa");
                s_date = request.getParameter("data_inicial");
                Date date = null;

                try{
                    SimpleDateFormat aux = new SimpleDateFormat("YYYY-MM-DD");

                    java.util.Date u_date = aux.parse(s_date);
                    date = new Date(u_date.getTime());

                }catch(ParseException excp){
                    //excp.printStackTrace();
                }

                ObjetoDAO dao = new ObjetoDAO();

                // Gerada a lista da consulta!
                pegado = dao.read(table, date);
/*
                                // Gerar JSON da ArrayList "pegado"!
                                json_pegado = "{ tupla: [ ";
                                for(int k=0; k<pegado.size(); k++){
                                    json_pegado += pegado.get(k).json_tupla();
                                    json_pegado += ",\n";
                                }
                                json_pegado = json_pegado.substring(0, json_pegado.length()-2);
                                json_pegado += " ] }";

                                //out.println( json_pegado );
                                out.println("<input type=\"radio\" id=\"my_query\" name=\"query_json\" "+
                                    "value=\""+json_pegado+" checked />");
*/

                out.println("<head><title>Predictions - Consulta Resultado</title><meta charset=\"UTF-8\">"+
                		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
                  		"<link rel=\"stylesheet\" href=\"https://bootswatch.com/slate/bootstrap.min.css\">"+
                        "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\">"+"</script>"+
                  		"<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\">"+"</script>"+

                    "</head>"+
                    "<body class = \"text-center\">"+
                    	"<div class=\"alert alert-success fade in\">"+
                    		"<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
                  			"<strong>Success!</strong>"+
                		"</div>"+

                  		"<div style = \"height:5em;\">"+"</div>"+
                    	"<div class = \"container-fluid\">"+
                  				"<div style = \"height:5em;\">"+"</div>"+
                                "<div class = \"jumbotron\">");
                out.println("<input type=\"radio\" id=\"my_dat\" name=\"data_inicial\" value=\""+
                    s_date+"\" checked /><br/><br>");

              out.println("<a href=\"../paginas/data_inicial.jsp?Empresa="+
                    table+"\"><h2>> Voltar</h2></a><br><br>");
              out.println("<a href=\"../paginas/consulta.jsp?Empresa="+
                   table+"=Data="+s_date+"\"><h2>> Avancar</h2></a>");
              out.println("<br/> <br/>");


                // Gerar página:
                out.print("<br/><table class=\"table table-hover table-condensed\">"+
                	"<thead><tr><th>Date</th><th>Open</th><th>High</th><th>Low</th><th>Close</th>"+
        			"<th>Volume</th><th>Adj_clo</th></tr></thead><tbody>");

                for(int wert=0; wert<pegado.size(); wert++){
                    out.println("<tr><td>"+pegado.get(wert).getDate()+"</td><td>"+
                        pegado.get(wert).getOpen()+"</td><td>"+pegado.get(wert).getHigh()+
                        "</td><td>"+pegado.get(wert).getLow()+"</td><td>"+
                        pegado.get(wert).getClose()+"</td><td>"+pegado.get(wert).getVolume()+
                        "</td><td>"+pegado.get(wert).getAdj_clo()+"</td></tr>");
                }

                out.print("</tbody></table></div></div>");
/*
                out.println("<script type=\"text/javascript\" src=\"../js/"+
                        "guardarJSON.js\" ></script>");
                out.println("<input type=\"button\" value=\"Guardar Consulta\" onclick=\"guardarJSON()\" />");
*/
                out.close();
            }
            catch( Exception excp ){
                // Passar atributo "error", para só informar o erro quando carregar "erro.jsp"
                StringWriter errors = new StringWriter();
                excp.printStackTrace(new PrintWriter(errors));
                request.setAttribute("error", errors.toString());

                ServletContext add = this.getServletContext();
                RequestDispatcher rd = add.getRequestDispatcher("/paginas/erro.jsp");
                rd.forward(request, response);
            }
            finally{
              /*
                              // Para passar os parâmetros para a próxima página JSP, que é "consulta.jsp".
                              request.setAttribute("codigo_empresa", request.getParameter("codigo_empresa"));
                              request.setAttribute("data_inicial", request.getParameter("data_inicial"));

                              //request.setAttribute("lista_gerada", pegado);
                              request.setAttribute("lista_gerada", json_pegado);

                              ServletContext add = this.getServletContext();

                              //RequestDispatcher rd = add.getRequestDispatcher("/paginas/"+
                              //      "consulta.jsp?Empresa="+table+"=Data="+s_date);

                              RequestDispatcher rd = add.getRequestDispatcher("/paginas/consulta.jsp");
                              //if( rd != NULL ){
                                  rd.forward(request, response);
                              //}
              */
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Sem descricao ainda";
    }
    // </editor-fold>

}
