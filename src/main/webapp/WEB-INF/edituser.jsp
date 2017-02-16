<html>
    <body>
        <h2>Edit</h2>
        <p> ${error}  </p>
        <form action="/edituser?${id}" method="post">
            Username:<br/>
            <input type="text" name="username"/>
            <br/>
            First Name:<br/>
            <input type="text" name="name">
            <br><br>
            <input type="submit" value="Submit">

        </form>
        <form action="/" method="get">
                <input type="submit" value="back">
        </form>


    </body>
</html>