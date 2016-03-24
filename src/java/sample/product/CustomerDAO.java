/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sample.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import sample.utils.DBUtils;

/**
 *
 * @author Frost
 */
public class CustomerDAO {
    public int customerIntoStore(CustomerDTO customer) 
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        int customerID;
        String sql;
        ResultSet rs = null;
        PreparedStatement stm = null;
        Statement stm2 = null;
        
        try {
            con = DBUtils.makeConnection();
            sql = "SELECT MAX(id) FROM Customer";
            stm2 = con.createStatement();
            rs = stm2.executeQuery(sql);
            if (rs.next()) {
                customerID = rs.getInt(1) + 1;
            } else {
                customerID = 1;
            }
            rs.close();
            
            sql = "INSERT INTO Customer VALUES(?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, customerID);
            stm.setString(2, customer.getName());
            stm.setString(3, customer.getAddress());
            stm.setString(4, customer.getEmail());
            stm.setString(5, customer.getPhone());
            int row = stm.executeUpdate();
            
            if (row == 0) {
                customerID = -1;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (stm2 != null) stm2.close();
            if (con != null) con.close();
        }
        
        return customerID;
    }
}
