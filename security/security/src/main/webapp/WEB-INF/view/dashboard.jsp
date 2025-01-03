<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <title>Dashboard</title>
    <style>

    </style>
</head>
<body>
<div class="navbar">
    <a href="/login.jsp">Login</a>
    <a href="/register.jsp">Register</a>
    <a href="/changePassword.jsp">Change Password</a>
</div>
<div class="content">
    <h2>List of Users</h2>
    <c:if test="${empty users}">
        <p>No users available.</p>
    </c:if>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
