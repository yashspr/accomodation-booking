import helpers.ConnectionManager;
import models.BookingDetailsContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "BookingServlet", value="/Book")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            Connection con = ConnectionManager.getNewConnection();

            HttpSession s = request.getSession(false);
            if(s == null) {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else {
                BookingDetailsContainer bdc = (BookingDetailsContainer) s.getAttribute("booking_details");

                String query = "insert into booking_details(u_id, e_id, start_date, end_date, no_of_rooms, total_price, price_status) " +
                        "values(?,?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, bdc.getU_id());
                ps.setInt(2, bdc.getE_id());
                ps.setDate(3, bdc.getStartdate());
                ps.setDate(4, bdc.getEnddate());
                ps.setInt(5, bdc.getNoofrooms());
                ps.setInt(6, bdc.getTotalprice());
                ps.setBoolean(7, false);

                int result = ps.executeUpdate();

                if(result == 0) {
                    System.out.println("Booking Unsuccessful");

                } else {
                    System.out.println("Booked successfully");

                    String query2 = "update availability_details " +
                            "set available_rooms=available_rooms-? " +
                            "where e_id=?";
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps2.setInt(1, bdc.getNoofrooms());
                    ps2.setInt(2, bdc.getE_id());

                    int result2 = ps2.executeUpdate();

                    if(result2 == 0) {
                        System.out.println("Update available rooms unsuccessful");
                    } else {
                        System.out.println("Updated available rooms successfully");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
