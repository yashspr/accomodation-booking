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

@WebServlet(name = "UserEditProfileServlet", value = "/EditUserProfile")
public class UserEditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Connection con = ConnectionManager.getNewConnection();

            HttpSession s = request.getSession(false);
            if(s == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else {

                String change_password = request.getParameter("change_password");

                if(change_password.equals("true")) {

                    String new_pass = request.getParameter("new_password");
                    String pwd_change = "update users " +
                            "set password=? " +
                            "where id=?";
                    PreparedStatement ps = con.prepareStatement(pwd_change);
                    ps.setString(1, new_pass);
                    ps.setInt(2, (int) s.getAttribute("id"));
                    int result = ps.executeUpdate();

                    if(result == 1) {
                        System.out.println("Password changed successfully");
                    } else {
                        System.out.println("Password change unsuccessful");
                    }

                    request.getRequestDispatcher("/logout.do").forward(request, response);

                }

                else {
                    String fname = request.getParameter("fname");
                    String lname = request.getParameter("lname");
                    String phone = request.getParameter("phone");

                    String query = "update user_details " +
                            "set fname=?, lname=?, phone=? " +
                            "where id=?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, fname);
                    ps.setString(2, lname);
                    ps.setString(3, phone);
                    ps.setInt(4, (int) s.getAttribute("id"));
                    int result = ps.executeUpdate();

                    if(result == 0) {
                        System.out.println("Edit profile unsuccessful");
                    } else {
                        System.out.println("Edit Profile Successful");
                    }

                    request.getRequestDispatcher("/UserProfile").forward(request, response);
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
