/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.BD;

/**
 *
 * @author USUARIO
 */
public class Reserva extends HttpServlet{
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

        String coche = (String) req.getParameter("lCoches");
        String fechaI = (String) req.getParameter("fechaI");
        String horaI = (String) req.getParameter("horaI");
        String fechaF = (String) req.getParameter("fechaF");
        String horaF = (String) req.getParameter("horaF");
        String email= (String) s.getAttribute("email"); 
        String recogida = fechaI+" "+horaI;
        String entrega = fechaF+" "+horaF;
        try {
            set = con.createStatement();
            String insertar = "INSERT INTO reserva(CodReserva, CocheMatricula,ClienteEmail,RecogidaTeorica,EntregaTeorica, Estado) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(insertar);
            ps.setString(1, null);
            ps.setString(2, coche);
            ps.setString(3, email);
            ps.setString(4, recogida);
            ps.setString(5, entrega);
            ps.setString(6, "PENDIENTE");
            ps.executeUpdate();           
            
            req.getRequestDispatcher("indexCliente.jsp").forward(req, res);

            set.close();
        } catch (SQLException ex2) {
            System.out.println("No inserta el usuario en Cliente." + ex2);
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
