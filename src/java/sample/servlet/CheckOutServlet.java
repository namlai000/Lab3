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
import sample.product.CustomerDAO;
import sample.product.CustomerDTO;
import sample.product.OrderDAO;
import sample.product.OrderDetailsDAO;
import sample.product.ProductDTO;

/**
 *
 * @author Frost
 */
public class CheckOutServlet extends HttpServlet {

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
            out.println("<title>Result</title>");
            out.println("</head>");
            out.println("<body>");

            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            boolean invalid = false;
            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                out.println("<h2>All fields can not be empty, please try agian</h2>");
                out.println("<a href='ProcessServlet?btAction=Continue'>Back to chek out</a>");
            } else {
                if (!email.matches("\\w+@\\w+.\\w+")) {
                    out.println("<h2>Email is invalid</h2>");
                    invalid = true;
                }

                try {
                    Integer.parseInt(phone);
                } catch (Exception e) {
                    out.println("<h2>Phone is invalid</h2>");
                    invalid = true;
                }

                if (invalid) {
                    out.println("<a href='ProcessServlet?btAction=Continue'>Back to chek out</a>");
                } else {
                    CustomerDTO cus = new CustomerDTO(name, address, email, phone);
                    List<ProductDTO> cart = (List<ProductDTO>) request.getSession().getAttribute("Cart");
                    
                    int customerID = new CustomerDAO().customerIntoStore(cus);
                    int orderID = new OrderDetailsDAO().orderDetailsStore(cart);
                    
                    boolean checked = false;
                    if (customerID > 0 && orderID > 0) {
                        checked = new OrderDAO().orderStore(customerID, orderID);
                    }

                    if (checked) {
                        out.println("<h2>Thank you for buying, see you agian!</h2>");
                        request.getSession().invalidate();
                    }
                }
            }

            out.println("<a href='Welcome.html'>Back to main page</a>");
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            log("check out servlet error: " + ex.getMessage());
        } catch (SQLException ex) {
            log("check out servlet error: " + ex.getMessage());
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
