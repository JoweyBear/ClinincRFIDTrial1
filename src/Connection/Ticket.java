/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joena
 */
public class Ticket {
    public static Connection getConn(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryqr","root","12345");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinictrial1?useSSL=false&allowPublicKeyRetrieval=true","root","1234");
//                        System.out.println("Success");
        } catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
        }
        return con;
    }
    
    public static void main(String[] args){
        System.out.println(getConn());
    }
}
