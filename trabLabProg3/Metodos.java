import java.sql.Date;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import java.util.Stack;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.google.api.services.prediction.*;
      //import for the actual use of google predictions API


/**
 *
 * @author login
 */
@WebServlet(name = "Metodos", urlPatterns = {"/servlet/Metodos"})
public class Metodos extends HttpServlet {





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

      /*Método de Consulta 2 no BD:*/
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
  }






  public class Interpolar {
	private int Explosion;//Explosion and Defuser are attributes to prevent the
	//explosion of the exponential function
	protected List<Float> InitOut;
    protected List<Float> ValConst;
    private final Boolean Trigon;

    public Interpolar(List<Float> Out) {
        this.InitOut = Out;
        this.Trigon = false;
        setVar();
        this.Explosion = Out.size()%6;
    }
    //default constructor for polynomial interpolation

    public Interpolar(List<Float> Out, Boolean Trigon) {
        this.InitOut = Out;
        this.Trigon = Trigon;
        setVar(this.Trigon);
        this.Explosion = Out.size()%6;
    }
    //constructor to chose the kind of interpolation

    private void setVar() {
        int tam = this.InitOut.size(), somador = 0;
        int extra = 0;
        Float Matrix[][] = new Float[6][6];
        this.ValConst = this.InitOut;
        for(; somador < tam; extra++){
        	for(int i=0;i<6;i++){
        		for(int j=0;j<6;j++){
        			Matrix[i][j] = new Integer((int) pow((i+1+somador),j)).floatValue();
        			//System.out.println(Matrix[i][j]);
        		}
        	}
        	for(int i=0;i<6;i++){//i is the subtracted line, also i is the first not zero line number
            	for(int j=i+1;j<6;j++){//j is the line that is updating
                	Float div=Matrix[j][i];
                	this.ValConst.set(j + somador, this.ValConst.get(j + somador - extra)*
                			Matrix[i][i]/div-this.ValConst.get(i + somador - extra));
                	//System.out.println(Matrix[j][i]);
                	//System.out.println(this.ValConst.get(j));
                	for(int k=6-1;k>=0;k--){//k is the exponent of the line value
                    	//System.out.println(Matrix[j][i]);
                    	Matrix[j][k]=Matrix[j][k]*Matrix[i][i]/div-
                    			Matrix[i][k];
                    	//System.out.println(k+" "+j);
                    	//System.out.println(Matrix[j][k]);
                	}
            	}
            	//System.out.println(this.ValConst.get(i));
        	}
        	for(int i=6-1;i>=0;i--){
            	Float soma = new Float(0);
            	for(int k=i+1;k<6;k++){
                	soma+=this.ValConst.get(k + somador - extra)*Matrix[i][k];
                //System.out.println(soma);
            	}
            	this.ValConst.set(i + somador, this.ValConst.get(i + somador - extra)/Matrix[i][i]-
            			soma/Matrix[i][i]);
            	//System.out.println(this.ValConst.get(i));
        	}
        	somador += 6;
                this.ValConst.add((float)0);
        }
        this.ValConst.remove(this.ValConst.size() - 1);
        extra--;
        somador -= 6;
    	for(int i=0;i<Explosion;i++){
    		for(int j=0;j<Explosion;j++){
    			Matrix[i][j] = new Integer((int) pow((i+1+somador),j)).floatValue();
    			//System.out.println(Matrix[i][j]);
    		}
    	}
    	for(int i=0;i<Explosion;i++){//i is the subtracted line, also i is the first not zero line number
        	for(int j=i+1;j<Explosion;j++){//j is the line that is updating
            	Float div=Matrix[j][i];
            	this.ValConst.set(j + somador, this.ValConst.get(j + somador - extra)*
            			Matrix[i][i]/div-this.ValConst.get(i + somador - extra));
            	//System.out.println(Matrix[j][i]);
            	//System.out.println(this.ValConst.get(j));
            	for(int k=Explosion-1;k>=0;k--){//k is the exponent of the line value
                	//System.out.println(Matrix[j][i]);
                	Matrix[j][k]=Matrix[j][k]*Matrix[i][i]/div-
                			Matrix[i][k];
                	//System.out.println(k+" "+j);
                	//System.out.println(Matrix[j][k]);
            	}
        	}
        	//System.out.println(this.ValConst.get(i));
    	}
    	for(int i=Explosion-1;i>=0;i--){
        	Float soma = new Float(0);
        	for(int k=i+1;k<Explosion;k++){
            	soma+=this.ValConst.get(k + somador - extra)*Matrix[i][k];
            //System.out.println(soma);
        	}
        	this.ValConst.set(i + somador, this.ValConst.get(i + somador - extra)/Matrix[i][i]-
        			soma/Matrix[i][i]);
        	//System.out.println(this.ValConst.get(i));
    	}
    }
    //function to discover and charge the polynomial constants into their List


    private void setVar(Boolean Value) {
        if (!Value){
            setVar();
            return;
        }
        int tam = this.InitOut.size();
        //System.out.println(tam);
        Float Matrix[][] = new Float[tam][tam];
        this.ValConst = this.InitOut;
        for(int i=0;i<tam;i++){
            for(int j=0;j<tam;j++){
                Matrix[i][j] = new Float(cos((i+1)*j))+new Float(sin((i+1)*j));
                //System.out.println(Matrix[i][j]);
            }
        }
        for(int i=0;i<tam;i++){//i is the subtracted line, also i is the first not zero line number
            for(int j=i+1;j<tam;j++){//j is the line that is updating
                Float div=Matrix[j][i];
                this.ValConst.set(j, this.ValConst.get(j)*
                        Matrix[i][i]/div-this.ValConst.get(i));
                //System.out.println(Matrix[j][i]);
                //System.out.println(this.ValConst.get(j));
                for(int k=tam-1;k>=0;k--){//k is the exponent of the line value
                    //System.out.println(Matrix[j][i]);
                    Matrix[j][k]=Matrix[j][k]*Matrix[i][i]/div-
                            Matrix[i][k];
                    //System.out.println(k+" "+j);
                    //System.out.println(Matrix[j][k]);
                }
            }
            //System.out.println(this.ValConst.get(i));
        }
        for(int i=tam-1;i>=0;i--){
            Float soma = new Float(0);
            for(int k=i+1;k<tam;k++){
                soma+=this.ValConst.get(k)*Matrix[i][k];
            //System.out.println(soma);
            }
            this.ValConst.set(i, this.ValConst.get(i)/Matrix[i][i]-
                    soma/Matrix[i][i]);
            //System.out.println(this.ValConst.get(i));
        }
    }
    //same as the previous but with a check for the interpolation method

    public Float Estimate(int In) {
        Float Output = new Float(0);
        Float ControlOutput = new Float(1);
        int Iterator = 0;
        if (this.Trigon){
            for(int i=0;i<this.InitOut.size();i++){
                Output+=this.ValConst.get(i)*(new Float(cos(In*i))
                		+ new Float(sin(In*i)));
            }
            return Output;
        }
        for(;Iterator < this.ValConst.size();){
        	for(int i=0;i<6;i++){
        		Output+=this.ValConst.get(i)*((float)pow(In,i));
        	}
        	ControlOutput *= Output;
        	Output = (float) 0;
        	Iterator += 6;
    	}
        Iterator -= 6;
    	for(int i=0;i<this.Explosion;i++){
    		Output+=this.ValConst.get(i)*((float)pow(In,i));
    	}
        return ControlOutput*Output;
    }
    //function to estimate the value of the function

    public void reSet(List<Float> In) {
        InitOut = In;
        setVar(Trigon);
    }
    //function to reuse and object for other function interpolations

    public List<Float> getVarContainer() {
        List<Float> Output = new ArrayList<>();
        if(!Trigon){
            for(int i=0;i<this.ValConst.size();i++){
                Output.add(this.ValConst.get(i));
            }
        }
        return Output;
    }//function to return the set of constants

    public Float varAt(int i) {
        return this.ValConst.get(i);
    }
    //function to return the const in a certain position
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            List<Objeto> pegado = new ArrayList<Objeto>();
            List<Float[]> InitOut = new ArrayList<>();
            List<Float> Init = new ArrayList<>();
            List<Interpolar> ResultIn = new ArrayList<>();
            Float[] Information = new Float[6];
            //String json_pegado = "";

            try (PrintWriter out = response.getWriter()) {
                String table = request.getParameter("codigo_empresa");
                String s_date = request.getParameter("data_inicial");
                int n_meth = new Integer( request.getParameter("metodo") );
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

                for(int i=0;i<pegado.size();i++){
                	Information[0]=new Float(pegado.get(i).getOpen());
                	Information[1]=new Float(pegado.get(i).getClose());
                	Information[2]=new Float(pegado.get(i).getLow());
                	Information[3]=new Float(pegado.get(i).getHigh());
                	Information[4]=new Float(pegado.get(i).getAdj_clo());
                	Information[5]=new Float(pegado.get(i).getVolume());
                	InitOut.add(Information);
                }
                for(int i=0;i<6;i++){
                	for(int j=0;j<InitOut.size();j++){
                		Init.add(InitOut.get(j)[i]);
                	}
                        if(n_meth == 1){
                            ResultIn.add(new Interpolar(Init,true));
                        }else if(n_meth == 2){
                            ResultIn.add(new Interpolar(Init));
                        }
                }
                out.println("<head><title>Predictions</title><meta charset=\"UTF-8\">"+
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
                        	      "<strong >Data Inicial: "+s_date+"</strong>"+
                                "<br><br>"+"Valores de: "+table+"<br><br>"+
                  				"<div style = \"height:5em;\">"+"</div>"+
                                "<div class = \"jumbotron\">" );

                out.println("<input type=\"radio\" id=\"my_cod\" name=\"codigo_empresa\" value=\""+
                    table+"\" checked />");
                out.println("<input type=\"radio\" id=\"my_dat\" name=\"data_inicial\" value=\""+
                    s_date+"\" checked />");

                // Gerar página:
                out.print("<br/><table class=\"table table-hover table-condensed\">");
                out.println("<thead><tr><th>Open</th><th>High</th><th>Low</th><th>Close</th>"+
        					"<th>Volume</th><th>Adj_clo</th>");

                out.println("<th>"+(new java.util.Date())+"</th></tr></thead><tbody><tr>");

                for(int wert=0; wert<6; wert++){
                    out.println("<th>"+ResultIn.get(wert).Estimate(pegado.size()-1)+"</th>");
                }

                out.print("</tr></tbody></table><br><br>");

                out.println("<a href=\"../paginas/data_inicial.jsp?Empresa="+
                        table+"\"><h2>Ir para Data Inicial</h2></a><br><br>");
                out.println("<a href=\"../\"><h2>Ir para HOME</h2></a>");
                out.println("<br/> <br/></div></div>");

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
        return "Short description";
    }// </editor-fold>

}
