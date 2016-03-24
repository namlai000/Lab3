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
public class OrderDetailsDAO {
    public int orderDetailsStore(List<ProductDTO> cart) 
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        int orderID;
        String sql;
        ResultSet rs = null;
        PreparedStatement stm = null;
        Statement stm2 = null;
        
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
            
            for (ProductDTO p: cart) {
                sql = "INSERT INTO OrderDetails VALUES(?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderID);
                stm.setString(2, p.getName());
                stm.setString(3, p.getQuantityPerUnit());
                stm.setDouble(4, p.getPrice());
                stm.setInt(5, p.getQuantity());
                stm.setDouble(6, p.getTotal());
                int row = stm.executeUpdate();
                
                if (row == 0) {
                    orderID = -1;
                    return orderID;
                }
            }
            
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (stm2 != null) stm2.close();
            if (con != null) con.close();
        }
        
        return orderID;
    }
}
