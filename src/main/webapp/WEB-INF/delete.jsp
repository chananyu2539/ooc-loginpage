<html>
    <body>
        <h2>Are you sure you want to delete?</h2>
        <p> ${error}  </p>
        <form action="/deleteuser" method="post">
            <input type="submit" value="yes">
        </form>
        <form action="/" method="get">
            <input type="submit" value="no">
        </form>



    </body>
</html>