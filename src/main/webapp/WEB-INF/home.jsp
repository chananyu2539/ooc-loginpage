
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>


<html>
<body>
<h2>I'm home</h2>

<sql:setDataSource
        var="myDS"
        driver="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:3306/test"
        user="root" password=""
    />

    <sql:query var="listUsers"   dataSource="${myDS}">
        SELECT * FROM test_table;
    </sql:query>

    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of users</h2></caption>
            <tr>
                <th>Username</th>
                <th>Name</th>

            </tr>
            <c:forEach var="user" items="${listUsers.rows}">
                <tr>
                    <td><c:out value="${user.username}" /></td>
                    <td><c:out value="${user.name}" /></td>
                </tr>
            </c:forEach>
        </table>
    </div>


<form action="/login" method="get">
    <br><br>
    <input type="submit" value="Logout">
</form>
</body>
</html>
