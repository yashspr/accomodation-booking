import helpers.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "UserProfileServlet", value = "/UserProfile")
public class UserProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Connection con = ConnectionManager.getNewConnection();

            HttpSession s = request.getSession(false);
            if(s == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else {
                String query = "select u.email, ud.* " +
                        "from users u, user_details ud " +
                        "where u.id=ud.id and u.id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, (int) s.getAttribute("id"));
                ResultSet rs = ps.executeQuery();

                request.setAttribute("resultset", rs);

                String query2 = "select b.*, e.*" +
                        "from booking_details b, establishments_details e " +
                        "where e.e_id=b.e_id and b.u_id=? " +
                        "order by b.end_date desc";
                PreparedStatement ps2 = con.prepareStatement(query2);
                ps2.setInt(1, (int)s.getAttribute("id"));
                ResultSet rs2 = ps2.executeQuery();
                request.setAttribute("resultset2", rs2);

                request.getRequestDispatcher("user-profile.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
