/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.product.ProductDTO;

/**
 *
 * @author Frost
 */
public class ViewCartServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Your Cart</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>View of your shopping cart</h2>");
            
            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>No</th>");
            out.println("<th>Product ID</th>");
            out.println("<th>Product Name</th>");
            out.println("<th>Quantity Per Unit</th>");
            out.println("<th>Price(USD)</th>");
            out.println("<th>Quantity</th>");
            out.println("<th>Total</th>");
            out.println("<th>Delete</th>");
            out.println("<th>Update</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            boolean exist = false;
            int count = 0;
            List<ProductDTO> cart = (List<ProductDTO>) request.getSession().getAttribute("Cart");
            double sum = 0;
            if (cart != null && !cart.isEmpty()) {
                exist = true;
                out.println("<form action='ProcessServlet' id='delete'></form>");
                
                for (ProductDTO pd: cart) {
                    out.println("<form action='ProcessServlet'>");
                    out.println("<input type='hidden' form='delete' name='pk' "
                            + "value='" + pd.getID() + "'/>");
                    
                    out.println("<tr>");
                    out.println("<td>"
                            + ++count
                            + "</td>");
                    out.println("<td>"
                            + pd.getID()
                            + "<input type='hidden' name='pk' value='" 
                            + pd.getID()
                            + "'/>"
                            + "</td>");
                    out.println("<td>"
                            + pd.getName()
                            + "</td>");
                    out.println("<td>"
                            + pd.getQuantityPerUnit()
                            + "</td>");
                    out.println("<td>"
                            + pd.getPrice()
                            + "</td>");
                    out.println("<td>"
                            + "<input type='text' name='quantity' value='"
                            + pd.getQuantity()
                            + "'/>"
                            + "</td>");
                    out.println("<td>"
                            + pd.getTotal()
                            + "</td>");    
                    out.println("<td><input type='checkbox' form='delete' name='chkProduct' value='"
                            + pd.getID()
                            + "'/></td>");
                    out.println("<td><input type='submit' value='Update' name='btAction'/></td>");
                    out.println("</tr>");
                    out.println("</form>");
                    
                    sum += pd.getTotal();
                }
                
                out.println("<tr>");
                out.println("<td colspan='8'></td>");
                out.println("<td><input type='submit' form='delete' value='Delete' name='btAction'/></td>"); 
                out.println("<td></td>"); 
                out.println("</tr>");
            } else {
                out.println("You dont have any cart </br>");
            }
            
            out.println("</tbody>");
            out.println("</table>");
            
            out.println("Total: " + count + " products selected! - Payment in USD: " + sum + "</br>");
            
            if (exist) {
                out.println("<form action='ProcessServlet'>");
                out.println("<td><input type='submit' value='Continue' name='btAction'/></td>");
                out.println("</form>");
            }
            
            out.println("<a href='ProcessServlet?btAction=Display'>Back to display all products</a");
            out.println("</body>");
            out.println("</html>");
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
