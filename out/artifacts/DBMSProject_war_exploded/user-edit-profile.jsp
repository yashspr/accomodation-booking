<%@ page import="java.sql.*" %>
<%@ page import="helpers.Constants" %>
<%@ page import="helpers.DateParsing" %>
<%@ page import="helpers.ConnectionManager" %>
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
        Connection con = ConnectionManager.getNewConnection();

        if(s == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        else {
            String query = "select u.email, ud.* " +
                    "from users u, user_details ud " +
                    "where u.id=ud.id and u.id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, (int) s.getAttribute("id"));
            ResultSet rs = ps.executeQuery();

            Users u = new Users(rs);
            UserDetails ud = new UserDetails(rs);
            rs.next();

%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Edit Your Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="main.css" />
    <link rel="stylesheet" href="css/normalize.css" />
    <link rel="stylesheet" href="css/index.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/login.css" />
    <link rel="stylesheet" type="text/css" href="css/profile.css" />
    <link rel="stylesheet" href="css/edit-profile.css">
    <link rel="stylesheet" href="css/user-edit-profile.css">
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

    <div class="main-area">
        <h1>Welcome, <%=ud.getFirstName()%></h1>

            <div class="divider"></div>

            <div id="profile-area">
                <img id="profile-pic" src="assets/images/placeholder.png" alt="Placeholder">
                <div id="user-details">
                    <form action="/EditUserProfile" method="post">
                        <h3>First Name: </h3>
                        <input type="text" name="fname" value="<%=ud.getFirstName()%>">
                        <br>
                        <h3>Last Name: </h3>
                        <input type="text" name="lname" value="<%=ud.getLastName()%>">
                        <br>
                        <h3>Phone: </h3>
                        <input type="text" name="phone" value="<%=ud.getPhone()%>">
                        <br>
                        <h3>Email: </h3>
                        <input type="email" name="email" value="yashspr5@gmail.com" disabled>
                        <br>
                        <input type="hidden" name="change_password" value="false">
                        <input type="submit" value="Save">
                    </form>
                </div>
            </div>
    </div>
    <div class="main-area">
        <div id="password-change">
            <h2>Change Your Password</h2>
                <form action="/EditUserProfile" method="post">
                    <h3>Old Password: </h3>
                    <input type="password" name="old_password">
                    <br>
                    <h3>New Password: </h3>
                    <input type="password" name="new_password">
                    <br>
                    <input type="hidden" name="change_password" value="true">
                    <input type="submit" value="Change Password">
                </form>
        </div>
    </div>

</body>

</html>

<%
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
%>