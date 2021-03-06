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
    public boolean orderStore(int customerID, int orderID) 
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        String sql;
        ResultSet rs = null;
        PreparedStatement stm = null;
        boolean inserted = false;
        
        try {
            con = DBUtils.makeConnection();
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
            if (con != null) con.close();
        }
        
        return inserted;
    }
}
