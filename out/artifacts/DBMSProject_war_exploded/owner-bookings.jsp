<%@ page import="java.sql.*" %>
<%@ page import="helpers.Constants" %>
<%@ page import="helpers.DateParsing" %>
<%@ page import="models.*" %>
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

        ResultSet rs = (ResultSet) request.getAttribute("resultset");
        BookingDetails bd = new BookingDetails(rs);
        UserDetails ud = new UserDetails(rs);
        Users u = new Users(rs);

%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Bookings</title>
    <link rel="stylesheet" href="css/normalize.css" />
    <link rel="stylesheet" href="css/index.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/login.css" />
    <link rel="stylesheet" href="css/owner-bookings.css">
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

                if(s.getAttribute("type").equals(Constants.CLIENT)) {

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

    <div id="main">
        <div id="main-content">
            <h1>Your. Recent. Bookings.</h1>
            <form action="#">
                <input type="text" placeholder="Enter Name to Search">
                <input type="submit" value="Search" />
            </form>

            <%
                if (bd.getLength() != 0) {
                    rs.beforeFirst();
            %>

            <div id="bookings">

                <%
                    while(rs.next()) {
                %>

                <div class="booking-item">
                    <div class="booking-main-details">
                        <h3><%=ud.getFirstName() + " " + ud.getLastName()%></h3>
                        <p>Phone: +91 <%=ud.getPhone()%></p>
                        <p>Email: <%=u.getEmail()%></p>
                    </div>
                    <div class="booking-sub-details">
                        <p>Days of stay: <%=DateParsing.getDaysFromDates(bd.getStartDate(), bd.getEndDate())%></p>
                        <p>Date: <span class="booking-date"><%=DateParsing.getStringFromDate(bd.getStartDate())%></span> to <span class="booking-date"><%=DateParsing.getStringFromDate(bd.getEndDate())%></span></p>
                        <p>Number of rooms: <strong><%=bd.getBookedRooms()%></strong></p>
                    </div>
                    <div class="booking-price-details">
                        <p>Total Price: ₹ <%=bd.getTotalPrice()%> </p>
                        <div class="booking-payment-status not-paid">Cash On Arrival</div>
                    </div>
                </div>

                <%
                    }
                %>
            </div>

            <%
                }
            %>
        </div>
    </div>

</body>
</html>

<%
    } catch(Exception e) {
        e.printStackTrace();
    }
%>