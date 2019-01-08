/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.BD;

/**
 *
 * @author CASA
 */
public class Cancelar extends HttpServlet {

    private Connection con;
    private Statement set;
    String cad;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        con = BD.getConexion();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Obtener la sesión
        HttpSession s = req.getSession(true);

        String codigo = (String) req.getParameter("canCod");
        String email= (String) s.getAttribute("email"); 

        try {
            boolean valido = false;
            PreparedStatement ps = con.prepareStatement("select * from reserva where CodReserva=? and ClienteEmail=?");
            ps.setString(1, codigo);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            valido = rs.next();
            if (valido && rs.getString("Estado").equals("PENDIENTE")) {
                set = con.createStatement();
                set.executeUpdate("UPDATE reserva SET Estado='CANCELADO' where CodReserva=" + codigo);
                               
                set.close();
                
                req.getRequestDispatcher("indexCliente.jsp").forward(req, res);
            }
        } catch (SQLException ex) {
            System.out.println("No es posible realizar el cambio de estado." + ex);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            doPost(req, res);
        } catch (IOException | ServletException e) {
        }
    }

}

