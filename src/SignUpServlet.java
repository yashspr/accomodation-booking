import helpers.ConnectionManager;
import models.UserDetails;
import models.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "SignUpServlet", value = "/signup.do")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = ConnectionManager.getNewConnection();

            String fullname = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String type = request.getParameter("type");

            if(request.getSession(false) != null) {
                request.getSession().invalidate();
            }


            String query = "insert into users(email,password) values(?,?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            int result = ps.executeUpdate();

            if(result == 1) {
                request.setAttribute("new_user", true);

                String login_query = "select id from users where email=? and password=?;";
                PreparedStatement ps2 = con.prepareStatement(login_query);
                ps2.setString(1, email);
                ps2.setString(2, password);

                ResultSet rs = ps2.executeQuery();
                Users user = new Users(rs);
                int id = user.getId();

                String namesplit[] = fullname.split(" ");
                String fname = namesplit[0];
                String lname = namesplit[1];

                String user_details_query = "update user_details set user_type=?, fname=?, lname=? where id=?";
                PreparedStatement ps3 = con.prepareStatement(user_details_query);
                ps3.setString(1, type);
                ps3.setString(2, fname);
                ps3.setString(3, lname);
                ps3.setInt(4, id);
                int ps_result = ps3.executeUpdate();
                if(ps_result == 1) {
                    System.out.println("set user type successfully");
                } else {
                    System.out.println("wasnt able to set user type");
                }

                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {

            }


        } catch(Exception e) {
            String msg = e.getLocalizedMessage();
            if(msg.startsWith("Duplicate")) {
                request.setAttribute("reg_failed", true);
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().println("Invalid Request");
    }
}
