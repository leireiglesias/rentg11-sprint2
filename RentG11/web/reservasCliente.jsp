<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page import="java.io.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.*"%>
<%@page import="utils.*"%>

<html>
    <head>
        <title>Reservas</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/estilo.css">
    </head>
    <body>

        <!-- Cabecera -->
        <header>
            <div class="inner">
                    <a href="indexCliente.jsp">Volver</a>
            </div>
        </header>
        
        <p></p>
        <h1>Reservas realizadas</h1>
        <table border=1>
            <tr><td><b>Identificador</b></td><td><b>Matrícula</b></td><td><b>Email</b></td>
                <td><b>Recogida Teorica</b></td><td><b>Entrega Teorica</b></td><td><b>Entrega Real</b></td><td><b>Recogida Real</b>
                </td><td><b>Estado</b></td></tr>
                <%!
                    private Connection con;
                    private Statement set;
                    private ResultSet rs;

                    public void jspInit() {
                        con = BD.getConexion();
                    }

                    ;                                      
                %>  
                <%
                    try {
                        String f = request.getParameter("fechaBusq");
                        String matricula, email, estado;
                        int codReserva;
                        Date ETeorica, EReal, RTeorica, RReal;
                        set = con.createStatement();
                        String e = (String) session.getAttribute("email");
                        rs = set.executeQuery("SELECT * FROM reserva where DATE(RecogidaTeorica) < '"+f+"' and ClienteEmail= '" + e +"'");
                        while (rs.next()) {
                            codReserva = rs.getInt("CodReserva");
                            matricula = rs.getString("CocheMatricula");
                            email = rs.getString("ClienteEmail");
                            RTeorica = rs.getDate("RecogidaTeorica");
                            ETeorica = rs.getDate("EntregaTeorica");
                            EReal = rs.getDate("EntregaReal");
                            RReal = rs.getDate("RecogidaReal");
                            estado = rs.getString("Estado");
                %>                      
            <tr><td><%=codReserva%></td>
                <td><%=matricula%></td>
                <td><%=email%></td>
                <td><%=RTeorica%></td>
                <td><%=ETeorica%></td>
                <td><%=EReal%></td>
                <td><%=RReal%></td>
                <td><%=estado%></td></tr>
                <%
                        }
                        rs.close();
                        set.close();
                        //con.close();
                    } catch (Exception ex) {
                        System.out.println("Error en acceso al mostrar reservas" + ex);
                    }
                %>
        </table>

        <!-- Pie de página -->
        <footer id="footer">
            <div class="inner">
                <section class="seccionpie">
                    <address>Vitoria, País Vasco</address>
                    <small>&copy; Todos los Derechos Reservados 2019</small>
                </section>
            </div>
        </footer>
    </body>
</html>