import helpers.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "OwnerBookingsServlet", value = "/OwnerBookings")
public class OwnerBookingsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            Connection con = ConnectionManager.getNewConnection();

            HttpSession s = request.getSession(false);
            if(s == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else {

                String query = "select b.*, u.email, ud.* " +
                        "from booking_details b, user_details ud, users u, establishments_details e " +
                        "where b.u_id=u.id and b.u_id=ud.id and b.e_id=e.e_id and e.owner_id=? " +
                        "order by b.end_date desc";

                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, (int) s.getAttribute("id"));
                ResultSet rs = ps.executeQuery();

                request.setAttribute("resultset", rs);
                request.getRequestDispatcher("owner-bookings.jsp").forward(request, response);
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
