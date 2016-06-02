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
