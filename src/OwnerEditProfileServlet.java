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
import java.sql.SQLException;

@WebServlet(name = "OwnerEditProfileServlet", value = "/EditOwnerProfile")
public class OwnerEditProfileServlet extends HttpServlet {
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
                    String name = request.getParameter("name");
                    String address = request.getParameter("address");
                    String phone = request.getParameter("phone");
                    int total_rooms = Integer.parseInt(request.getParameter("total_rooms"));
                    int price = Integer.parseInt(request.getParameter("price"));
                    String type = request.getParameter("type");
                    int available_rooms = Integer.parseInt(request.getParameter("available_rooms"));

                    String query = "update establishments_details e, availability_details a, price_details p " +
                            "set e.e_name=?, e.address=?, e.phone=?, a.total_rooms=?, p.price_per_day=?, e.service_type=?, a.available_rooms=? " +
                            "where a.e_id=e.e_id and e.owner_id=?";

                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, name);
                    ps.setString(2, address);
                    ps.setString(3, phone);
                    ps.setInt(4, total_rooms);
                    ps.setInt(5, price);
                    ps.setString(6, type);
                    ps.setInt(7, available_rooms);
                    ps.setInt(8, (int) s.getAttribute("id"));
                    int result = ps.executeUpdate();

                    /*String query2 = "update availability_details a, establishments_details e " +
                            "set a.total_rooms=? " +
                            "where a.e_id=e.e_id and e.owner_id=?";
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps.setInt(1, total_rooms);
                    ps.setInt(2, (int)s.getAttribute("id"));
                    int result2 = ps2.executeUpdate();*/

                    if(result == 0) {
                        System.out.println("Edit profile unsuccessful");
                    } else {
                        System.out.println("Rows updated: " + result);
                        System.out.println("Edit Profile Successful");
                    }

                    request.getRequestDispatcher("/OwnerProfile").forward(request, response);
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