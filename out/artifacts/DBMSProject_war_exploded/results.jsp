<%@ page import="models.EstablishmentsDetails" %>
<%@ page import="java.sql.*" %>
<%@ page import="models.PriceDetails" %>
<%@ page import="helpers.Constants" %>
<%@ page import="models.SearchParametersContainer" %>
<%@ page import="helpers.DateParsing" %>
<!--
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
-->

<%
    boolean isLoggedIn = false;
    HttpSession s = request.getSession(false);
    if(s != null) {
        isLoggedIn = true;
    }
%>

<%
    try {
        ResultSet pedrs = null;
        EstablishmentsDetails ed = null;
        PriceDetails pd = null;
        boolean found = (boolean) request.getAttribute("found");
        if(found) {
            pedrs = (ResultSet) request.getAttribute("hotel_data");
            ed = new EstablishmentsDetails(pedrs);
            pd = new PriceDetails(pedrs);
        }

        SearchParametersContainer spc = (SearchParametersContainer) request.getAttribute("search_parameters");
        Date checkin = spc.getCheckIn();
        String scheckin = DateParsing.getStringFromDate(checkin);
        Date checkout = spc.getCheckOut();
        String scheckout = DateParsing.getStringFromDate(checkout);
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Search Results</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css" />
    <link rel="stylesheet" href="css/index.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/login.css" />
    <link rel="stylesheet" href="css/messages.css">
    <link rel="stylesheet" href="css/results.css" type="text/css">
</head>

<body>
    <header>
        <nav>
            <a href="http://localhost:8080">
                <div id="logo">
                    <svg viewBox="0 0 1000 1000" role="presentation" aria-hidden="true" focusable="false" style="height:1em;width:1em;fill:currentColor">
                        <path d="m499.3 736.7c-51-64-81-120.1-91-168.1-10-39-6-70 11-93 18-27 45-40 80-40s62 13 80 40c17 23 21 54 11 93-11 49-41 105-91 168.1zm362.2 43c-7 47-39 86-83 105-85 37-169.1-22-241.1-102 119.1-149.1 141.1-265.1 90-340.2-30-43-73-64-128.1-64-111 0-172.1 94-148.1 203.1 14 59 51 126.1 110 201.1-37 41-72 70-103 88-24 13-47 21-69 23-101 15-180.1-83-144.1-184.1 5-13 15-37 32-74l1-2c55-120.1 122.1-256.1 199.1-407.2l2-5 22-42c17-31 24-45 51-62 13-8 29-12 47-12 36 0 64 21 76 38 6 9 13 21 22 36l21 41 3 6c77 151.1 144.1 287.1 199.1 407.2l1 1 20 46 12 29c9.2 23.1 11.2 46.1 8.2 70.1zm46-90.1c-7-22-19-48-34-79v-1c-71-151.1-137.1-287.1-200.1-409.2l-4-6c-45-92-77-147.1-170.1-147.1-92 0-131.1 64-171.1 147.1l-3 6c-63 122.1-129.1 258.1-200.1 409.2v2l-21 46c-8 19-12 29-13 32-51 140.1 54 263.1 181.1 263.1 1 0 5 0 10-1h14c66-8 134.1-50 203.1-125.1 69 75 137.1 117.1 203.1 125.1h14c5 1 9 1 10 1 127.1.1 232.1-123 181.1-263.1z">
                        </path>
                    </svg>
                </div>
            </a>

            <ul>
                <%
                    if(!isLoggedIn) {
                %>
                <%--<li><a href="#">Become a Host</a></li>--%>
                <%--<li><a href="#">Help</a></li>--%>
                <li><a href="/signup.jsp">Sign Up</a></li>
                <li><a href="/login.jsp">Login</a></li>

                <% } else {

                    if(((String)s.getAttribute("type")).equals(Constants.CLIENT)) {

                %>
                <%--<li><a href="#">Help</a></li>--%>
                <%--<li><a href="/user-bookings.html">Your Bookings</a></li>--%>
                <li><a href="/UserProfile">Profile</a></li>
                <li><a href="/logout.do">Logout</a></li>

                <%    } else { %>

                <%--<li><a href="#">Help</a></li>--%>
                <li><a href="/OwnerBookings">Your Bookings</a></li>
                <li><a href="/OwnerProfile">Profile</a></li>
                <li><a href="/logout.do">Logout</a></li>

                <%
                        }
                    }
                %>

            </ul>
        </nav>
    </header>

    <div id="sidebar-container">
        <div id="sidebar-main">
            <h2>Filter</h2>
            <form name="customize-form" action="/Results" method="post">
                <label>Location:</label>
                <input type="text" name="location" value="<%=spc.getLocation()%>" required>
                <label>Type:</label>
                <select name="type">
                    <option value="Hotel">Hotel</option>
                    <option value="Homestay">Homestay</option>
                    <option value="Resort">Resort</option>
                    <option value="Hostel">Hostel</option>
                    <option value="Guest House">Guest House</option>
                </select>
                <label>Check-in Date:</label>
                <input type="date" name="check-in" value="<%=scheckin%>">
                <label>Check-out Date</label>
                <input type="date" name="check-out" value="<%=scheckout%>">
                <label>Number of Rooms: </label>
                <input type="number" name="rooms" value="<%=spc.getNoofrooms()%>">
                <label>Budget: </label>
                <input type="range" min=500 max=15000 step=500 value="<%=spc.getBudget()%>" name="budget" id="budget-slider">
                <span id="slider-val"><%=spc.getBudget()%></span>
                <input type="hidden" name="filter" value="true">
                <input type="submit" value="Filter">
            </form>
        </div>
    </div>

    <div id="results-container">

        <%
            if(!found) {
        %>

            <div class="warning-message">
                No Results Found.
            </div>

        <%
            } else {
        %>

        <div id="results-main">
            <%
                try {
                    while(pedrs.next()) {

            %>
                        <div class="result-item">
                            <img src="assets/images/hotelpics/<%=ed.getPicLocation()%>" alt="" class="place-pic">
                            <div class="left-part">
                                <h3 class="type"><%=ed.getServiceType()%></h3>
                                <div class="place-name"><%=ed.getName()%></div>
                                <p><%=ed.getAddress()%></p>
                            </div>
                            <div class="right-part">
                                <span>₹ </span><div class="price"><%=pd.getPrice()%></div>
                                <p>per night</p>
                                <div class="rating"></div>
                                <div class="book">
                                    <form action="/ConfirmBooking" method="post">
                                        <input hidden name="e_id" value="<%=ed.getId()%>">
                                        <input type="submit" value="Book">
                                    </form>
                                </div>
                            </div>
                        </div>

            <%
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            %>
        </div>
        <%
            }
        %>
    </div>
</body>

<%
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
%>

<script>
    var slider = document.getElementById("budget-slider");
    var output = document.getElementById("slider-val");
    output.innerHTML = slider.value; // Display the default slider value

    // Update the current slider value (each time you drag the slider handle)
    slider.oninput = function () {
        output.innerHTML = this.value;
    } 
</script>

</html>