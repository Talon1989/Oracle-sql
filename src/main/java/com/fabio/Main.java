package com.fabio;

import com.fabio.entities.Book;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.*;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main (String[] args) {
        log.info("test");
        em(); sql();
    }

    private static void em () {
        EntityManager em = Persistence.createEntityManagerFactory("Books")
                .createEntityManager();
        em.getTransaction().begin();
        Book book1 = em.find(Book.class, 1L);
        log.info(book1);
    }

    private static void sql () {
        try(Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE"
                ,"talon", "talon"
        )){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from BOOK");
            printResultSet(rs);
        }catch (SQLException e){ e.printStackTrace(); }
    }
    private static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData meta = resultSet.getMetaData();
        int columns = meta.getColumnCount();
        while(resultSet.next()){
            for(int i=1; i<=columns; i++){
                System.out.print(meta.getColumnName(i)+":"+resultSet.getString(i)+"  ;  ");
            }
            System.out.println();
        }
    }


}
