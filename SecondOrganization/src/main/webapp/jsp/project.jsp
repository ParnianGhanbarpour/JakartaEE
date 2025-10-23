<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Projects</title>
</head>
<body>
<h2>Projects</h2>

<form action="project.do" method="post">
    Title: <input type="text" name="title" required/>
    Description: <input type="text" name="description" required/>
    Start Date: <input type="datetime-local" name="startDate" required/>
    End Date: <input type="datetime-local" name="endDate" required/>
    Budget: <input type="number" step="0.01" name="budget" required/>
    Status:
    <select name="status" required>
        <c:forEach var="s" items="${ProjectStatus.values()}">
            <option value="${s}">${s}</option>
        </c:forEach>
    </select>
    <button type="submit">Add Project</button>
</form>

<hr/>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Budget</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="project" items="${projectList}">
        <tr>
            <td>${project.id}</td>
            <td>${project.title}</td>
            <td>${project.description}</td>
            <td>${project.startDate}</td>
            <td>${project.endDate}</td>
            <td>${project.budget}</td>
            <td>${project.status}</td>
            <td>
                <form action="project.do" method="post" style="display:inline;">
                    <input type="hidden" name="_method" value="DELETE"/>
                    <input type="hidden" name="id" value="${project.id}"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
