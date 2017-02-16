<html>
    <body>
        <h2>Register</h2>
        <p> ${error}  </p>
        <form action="/register" method="post">
            Username:<br/>
            <input type="text" name="username"/>
            <br/>
            Password:<br/>
            <input type="password" name="password">
            <br/>
            Password Confirmation:<br/>
            <input type="password" name="repassword">
            <br/>
            First Name:<br/>
            <input type="text" name="name">
            <br><br>
            <input type="submit" value="Submit">
        </form>
        <form action="/login" method="get">
        <br><br>
            <input type="submit" value="Logout">
        </form>


    </body>
</html>
