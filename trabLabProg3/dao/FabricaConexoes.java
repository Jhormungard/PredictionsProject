/*
 * Fábrica de objetos ConnectionFactory - gerar conexão com servidor.
 * Poupar ter que visualizar no escopo principal. Isso aqui é um Padrão de Pro-
 * jeto (Design Pattern).
 */
package dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;   /*Pois o JDBC está relacionado com SQLException, ao dar erro.*/
/**
 *
 * @author login
 */
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
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/Cotacoes", "alguem", "1234");
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
