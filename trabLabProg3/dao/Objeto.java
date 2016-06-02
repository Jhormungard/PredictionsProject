/*
 * Objeto que é a tupla de alguma tabela do Banco de Dados.
 * Objetivo de facilitar comandos SQL (DAO).
 */
package dao;

import java.sql.Date;

/**
 * 
 * @author login
 */
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
