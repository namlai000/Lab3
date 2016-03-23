/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sample.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Frost
 */
public class DBUtils implements Serializable {
    public static Connection makeConnection() 
            throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://fpthomework.database.windows.net:1433;database=Product;user=namlai000;password=Namlai120;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection con = DriverManager.getConnection(url);
        return con;
    }
}
