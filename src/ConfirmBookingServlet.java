import helpers.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "ConfirmBookingServlet", value = "/ConfirmBooking")
public class ConfirmBookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Connection c = ConnectionManager.getNewConnection();

            HttpSession s = request.getSession(false);
            if(s == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

            else {
                int e_id = Integer.parseInt(request.getParameter("e_id"));
                String query = "select e.*, p.price_per_day " +
                        "from establishments_details e, price_details p " +
                        "where e.e_id=p.e_id and e.e_id=?";

                PreparedStatement ps = c.prepareStatement(query);
                ps.setInt(1, e_id);
                ResultSet rs = ps.executeQuery();

                request.setAttribute("resultset", rs);
                request.getRequestDispatcher("confirm-booking.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }
}
