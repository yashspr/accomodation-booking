<%@ page import="helpers.Constants" %>
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

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/normalize.css" />
    <link rel="stylesheet" href="css/index.css" />
    <link rel="stylesheet" href="css/messages.css">
    <link rel="stylesheet" type="text/css" media="screen" href="css/login.css" />
</head>

<style>
    .note {
        color: red;
        margin-bottom: 0;
    }

    .hidden {
        display: none;
    }
</style>

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

    <div id="main">
        <%
            boolean reg_failed;
            try {
                reg_failed = (boolean) request.getAttribute("reg_failed");
            } catch(NullPointerException e) {
                reg_failed = false;
            }
            if(reg_failed) {
        %>
            <div class="warning-message">
                Registration Failed. Email is already in use. Try Again.
            </div>
        <%
            }
        %>
        <%
            if(isLoggedIn) {
        %>
            <div class="warning-message">
                Already logged in. Signing up again will log you out of this account.
            </div>
        <%
            }
        %>

        <div id="login-form">
            <h2>Sign Up</h2>
            <form action="/signup.do" method="post" name="signupform" onsubmit="event.preventDefault(); validate();">
                <label>Full Name</label>
                <input type="text" name="name" pattern="[a-zA-Z]{2,20} [a-zA-Z]{2,20}" oninvalid="setCustomValidity('First Name and Last Name must be separated with a space and each must be 2-20 characters')" oninput="setCustomValidity('')" required>
                <label>Email</label>
                <input type="email" name="email" required/>
                <label>Password</label>
                <input type="password" name="password" pattern=".{5,40}" oninvalid="setCustomValidity('Password must be between 5-40 characters')" oninput="setCustomValidity('')" required/>
                <label>Confirm Password</label>
                <input type="password" name="cpassword" required/>
                <label>Select Type</label>
                <select name="type">
                    <option value="client">Client</option>
                    <option value="owner">Owner</option>
                </select>
                <p class="note hidden">* Passwords are not matching</p>
                <input type="submit" value="Submit" />
            </form>
        </div>
    </div>



    <script>
        function validate() {
            let myform = document.forms['signupform'];
            let pwd  = myform.password.value;
            let confirmpwd = myform.cpassword.value;
            if(pwd != confirmpwd) {
                document.getElementsByClassName("note")[0].classList = "note";
            } else {
                myform.submit();
                console.log("Form submitted");
            }
        }
    </script>

</body>
</html>