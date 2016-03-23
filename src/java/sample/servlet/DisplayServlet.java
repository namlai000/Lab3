/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.product.ProductDAO;
import sample.product.ProductDTO;

/**
 *
 * @author Frost
 */
public class DisplayServlet extends HttpServlet {

    private ProductDAO dao;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            dao = new ProductDAO();
            dao.loadProducts();
            List<ProductDTO> list = dao.getList();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>All Products</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h4>Product List</h4>");

            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>No</th>");
            out.println("<th>Product ID</th>");
            out.println("<th>Product Name</th>");
            out.println("<th>Quantity Per Unit</th>");
            out.println("<th>Price(USD)</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            int count = 0;
            for (ProductDTO pd: list) {
                out.println("<tr>");
                out.println("<td>"
                        + ++count
                        + "</td>");
                out.println("<td>"
                        + pd.getID()
                        + "</td>");
                out.println("<td>"
                        + "<a href='ProcessServlet?btAction=AddToCart&pk="
                        + pd.getID()
                        + "'>"
                        + pd.getName()
                        + "</a>"
                        + "</td>");
                out.println("<td>"
                        + pd.getQuantityPerUnit()
                        + "</td>");
                out.println("<td>"
                        + pd.getPrice()
                        + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
       
            out.println("</br>" + "Total: " + count + " products in the store</br>");
            
            out.println("<a href='ProcessServlet?btAction=ViewCart'>View Your Cart</a");
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            log("display products servlet error: " + ex.getMessage());
        } catch (SQLException ex) {
            log("dispaly products servlet error: " + ex.getMessage());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
