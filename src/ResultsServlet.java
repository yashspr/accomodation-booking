import helpers.ConnectionManager;
import helpers.DateParsing;
import models.EstablishmentsDetails;
import models.PriceDetails;
import models.SearchParametersContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "ResultsServlet", value="/Results")
public class ResultsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            Connection con = ConnectionManager.getNewConnection();

            String filter = request.getParameter("filter");

            if(filter.equals("false")) {

                String location = request.getParameter("quick-location");
                int noofrooms = Integer.parseInt(request.getParameter("quick-rooms"));
                Date checkin = DateParsing.getDateFromForm(request.getParameter("quick-check-in"));
                Date checkout = DateParsing.getDateFromForm(request.getParameter("quick-check-out"));
                int noofdays = DateParsing.getDaysFromDates(checkin, checkout);

                SearchParametersContainer spc = new SearchParametersContainer();
                spc.setLocation(location);
                spc.setCheckIn(checkin);
                spc.setCheckOut(checkout);
                spc.setNoofrooms(noofrooms);
                spc.setBudget(4000);

                HttpSession s = request.getSession(false);
                if(s != null) {
                    s.setAttribute("search_parameters", spc);
                }

                request.setAttribute("search_parameters", spc);

                String hotel_query = "select e.*, p.price_per_day " +
                        "from establishments_details e, price_details p, availability_details a " +
                        "where e.address like ? and a.available_rooms>=? and e.e_id=p.e_id and a.e_id=e.e_id";

                PreparedStatement ps = con.prepareStatement(hotel_query);
                ps.setString(1, "%" + location + "%");
                ps.setInt(2, noofrooms);
                ResultSet edrs = ps.executeQuery();
                EstablishmentsDetails ed = new EstablishmentsDetails(edrs);

                if(ed.getLength() == 0) {
                    request.setAttribute("found", false);
                } else {
                    request.setAttribute("found", true);
                    request.setAttribute("hotel_data", edrs);
                }
            }
            else {

                String location = request.getParameter("location");
                String type = request.getParameter("type");
                int noofrooms = Integer.parseInt(request.getParameter("rooms"));
                int budget = Integer.parseInt(request.getParameter("budget"));
                Date checkin = DateParsing.getDateFromForm(request.getParameter("check-in"));
                Date checkout = DateParsing.getDateFromForm(request.getParameter("check-out"));
                int noofdays = DateParsing.getDaysFromDates(checkin, checkout);
                int price_per_night = budget/(noofrooms*noofdays);

                SearchParametersContainer spc = new SearchParametersContainer();
                spc.setLocation(location);
                spc.setCheckIn(checkin);
                spc.setCheckOut(checkout);
                spc.setNoofrooms(noofrooms);
                spc.setBudget(budget);
                request.setAttribute("search_parameters", spc);

                HttpSession s = request.getSession(false);
                if(s != null) {
                    s.setAttribute("search_parameters", spc);
                }

                String hotel_query = "select e.*, p.price_per_day " +
                        "from establishments_details e, price_details p, availability_details a " +
                        "where e.address like ? and e.service_type=? and a.available_rooms>=? and p.price_per_day<=? " +
                        "and e.e_id=a.e_id and e.e_id=p.e_id";

                PreparedStatement ps = con.prepareStatement(hotel_query);
                ps.setString(1, "%" + location + "%");
                ps.setString(2, type);
                ps.setInt(3, noofrooms);
                ps.setInt(4, price_per_night);

                ResultSet rs = ps.executeQuery();
                EstablishmentsDetails ed = new EstablishmentsDetails(rs);

                if(ed.getLength() == 0) {
                    request.setAttribute("found", false);
                } else {
                    request.setAttribute("found", true);
                    request.setAttribute("hotel_data", rs);
                }

            }

            request.getRequestDispatcher("results.jsp").forward(request, response);

        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
