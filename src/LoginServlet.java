import helpers.ConnectionManager;
import models.UserDetails;
import models.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "LoginServlet", value = "/login.do")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Connection con = ConnectionManager.getNewConnection();

            if(request.getSession(false) != null) {
                request.getSession().invalidate();
            }

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            /*String login_query = "select * from users where email=\"" + email + "\" and password = \"" + password + "\";";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(login_query);
            Users user = new Users(rs);*/

            String login_query = "{call login(?,?,?,?)}";
            CallableStatement cs = con.prepareCall(login_query);

            cs.setString(1, email);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.BOOLEAN);
            cs.registerOutParameter(4, Types.INTEGER);

            cs.execute();

            boolean found = cs.getBoolean(3);
            int id = cs.getInt(4);

            String user_details_query = "select * from user_details where id=?";
            PreparedStatement ps = con.prepareStatement(user_details_query);

            if(request.getSession(false) != null) {
                request.getSession().invalidate();
            }

            if(found) {
                HttpSession s = request.getSession();
                request.setAttribute("login_success", true);

                ps.setInt(1, id);
                ResultSet user_details_rs = ps.executeQuery();
                UserDetails userDetails = new UserDetails(user_details_rs);
                if(userDetails.getLength() == 1) {
                    userDetails.getResultSet().next();
                    s.setAttribute("type", userDetails.getUserType());
                }

                s.setAttribute("id", id);
                System.out.println("user-id" + (int)s.getAttribute("id"));
                System.out.println("user_type" + (String)s.getAttribute("type"));
                System.out.println("Login found");
                response.sendRedirect("index.jsp");

            } else {
                request.setAttribute("login_success", false);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                System.out.println("Login not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().println("Invalid Request");
    }
}
