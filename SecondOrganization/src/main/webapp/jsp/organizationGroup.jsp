<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Organization Groups</title>
</head>
<body>
<h2>Organization Groups</h2>

<form action="organizationGroup.do" method="post">
    Name: <input type="text" name="name" required/>
    Specialty: <input type="text" name="specialty" required/>
    Department:
    <select name="departmentId" required>
        <c:forEach var="dept" items="${departmentList}">
            <option value="${dept.id}">${dept.name}</option>
        </c:forEach>
    </select>
    <button type="submit">Add Group</button>
</form>

<hr/>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Specialty</th>
        <th>Department</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="group" items="${organizationGroupList}">
        <tr>
            <td>${group.id}</td>
            <td>${group.name}</td>
            <td>${group.specialty}</td>
            <td>${group.department.name}</td>
            <td>
                <form action="organizationGroup.do" method="post" style="display:inline;">
                    <input type="hidden" name="_method" value="DELETE"/>
                    <input type="hidden" name="id" value="${group.id}"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
