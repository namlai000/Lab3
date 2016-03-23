/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sample.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.utils.DBUtils;

/**
 *
 * @author Frost
 */
public class ProductDAO implements Serializable {
    
    private List<ProductDTO> list = new ArrayList<ProductDTO>();;

    public List<ProductDTO> getList() {
        return list;
    }
    
    public void loadProducts() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement sta = null;   
        ResultSet rs = null;
        String sql = "SELECT * FROM Products";
        
        try {
            con = DBUtils.makeConnection();
            sta = con.prepareStatement(sql);
            rs = sta.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String quantity = rs.getString(3);
                double price = rs.getDouble(4);
                
                ProductDTO pd = new ProductDTO(id, name, quantity, price);
                list.add(pd);
            }
        } finally {
            if (rs != null) rs.close();
            if (sta != null) sta.close();
            if (con != null) con.close();
        }
    }
    
    public ProductDTO searchProduct(int id) 
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement sta = null;   
        ResultSet rs = null;
        ProductDTO pd = null;
        String sql = "SELECT * FROM Products WHERE id=?";
        
        try {
            con = DBUtils.makeConnection();
            sta = con.prepareStatement(sql);
            sta.setInt(1, id);
            rs = sta.executeQuery();
            if (rs.next()) {
                int ids = rs.getInt(1);
                String name = rs.getString(2);
                String quantity = rs.getString(3);
                double price = rs.getDouble(4);
                
                pd = new ProductDTO(id, name, quantity, price);
            }
        } finally {
            if (rs != null) rs.close();
            if (sta != null) sta.close();
            if (con != null) con.close();
        }
        
        return pd;
    }
}
