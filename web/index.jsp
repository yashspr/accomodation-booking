<%@ page import="helpers.Constants" %>
<!-- <%--
  Created by IntelliJ IDEA.
  User: yashas
  Date: 8/10/18
  Time: 10:06 PM
  To change this template use File | Settings | File Templates.
--%> -->

<!--
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
-->

<%
    boolean isLoggedIn = false;
    HttpSession s = request.getSession(false);
    if(s!=null) {
        isLoggedIn = true;
    }
%>

<html>
<head>
    <title>Vacation, Rentals, Homes and Experiences</title>
    <link rel="stylesheet" href="css/normalize.css" />
    <link rel="stylesheet" href="css/index.css" />
</head>
<body>
<div class="main-container">
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

    <div id="quick-search">
        <form action="/Results" method="post">
            <h2>Book unique homes and experiences.</h2>
            <label>Location</label>
            <input type="text" name="quick-location" placeholder="Anywhere" required /><br>
            <div id="check-out-date">
                <label>Check-Out Date</label>
                <input type="date" name="quick-check-out" id="quick-check-out" placeholder="Check-Out Date" /><br>
            </div>
            <div id="check-in-date">
                <label>Check-In Date</label>
                <input type="date" name="quick-check-in" id="quick-check-in" placeholder="Check-In Date" />
            </div>
            <label>Number Of Rooms</label>
            <input type="number" name="quick-rooms" placeholder="Ex. 2" value="1" /><br>
            <input type="hidden" name="filter" value="false">
            <input type="submit" value="Search" />
        </form>
    </div>
</div>
</body>

<script>
    let n = new Date();
    let y = n.getFullYear();
    let m = n.getMonth() + 1;
    if(m < 10) {
        m = "0" + m;
    }
    let d = n.getDate();
    let d2 = d;
    if(d<10) {
        d = "0" + d;
        d2 += 1;
        if(d2<10) {
            d2  = "0" + d2;
        }
    }

    document.getElementById("quick-check-in").value = y + "-" + m + "-" + d;
    document.getElementById("quick-check-out").value = y + "-" + m + "-" + d2;

    console.log(document.getElementById("quick-check-in").value);
    console.log(document.getElementById("quick-check-out").value);
</script>

</html>
