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

@WebServlet(name = "OwnerProfileServlet", value = "/OwnerProfile")
public class OwnerProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Connection con = ConnectionManager.getNewConnection();

            HttpSession s = request.getSession(false);
            if(s == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else {
                String query = "select u.email, e.*, a.* " +
                        "from users u, user_details ud, establishments_details e, availability_details a " +
                        "where u.id=ud.id and e.owner_id=u.id and a.e_id=e.e_id and u.id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, (int) s.getAttribute("id"));
                ResultSet rs = ps.executeQuery();

                request.setAttribute("resultset", rs);

                request.getRequestDispatcher("owner-profile.jsp").forward(request, response);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
