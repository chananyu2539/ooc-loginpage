<html>
    <body>
        <h2>Add User</h2>
        <p> ${error}  </p>
        <form action="/adduser" method="post">
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
        <form action="/" method="get">
        <br><br>
            <input type="submit" value="back">
        </form>


    </body>
</html>
