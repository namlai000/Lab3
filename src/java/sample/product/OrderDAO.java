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
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author Frost
 */
public class OrderDAO {
    public boolean order(CustomerDTO customer, List<ProductDTO> cart) 
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        int customerID;
        int orderID;
        String sql;
        ResultSet rs = null;
        PreparedStatement stm = null;
        Statement stm2 = null;
        boolean inserted = false;
        
        try {
            con = DBUtils.makeConnection();
            sql = "SELECT MAX(id) FROM OrderDetails";
            stm2 = con.createStatement();
            rs = stm2.executeQuery(sql);
            if (rs.next()) {
                orderID = rs.getInt(1) + 1;
            } else {
                orderID = 1;
            }
            rs.close();
            
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
            stm.executeUpdate();
            
            for (ProductDTO p: cart) {
                sql = "INSERT INTO OrderDetails VALUES(?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderID);
                stm.setString(2, p.getName());
                stm.setString(3, p.getQuantityPerUnit());
                stm.setDouble(4, p.getPrice());
                stm.setInt(5, p.getQuantity());
                stm.setDouble(6, p.getTotal());
                stm.executeUpdate();
            }
            
            sql = "INSERT INTO Orders VALUES(?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, orderID);
            stm.setInt(2, customerID);
            SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");
            String date = df.format(new Date());
            stm.setString(3, date);
            stm.executeUpdate();
            
            inserted = true;
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (stm2 != null) stm2.close();
            if (con != null) con.close();
        }
        
        return inserted;
    }
}
