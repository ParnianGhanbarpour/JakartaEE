<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Persons</title>
</head>
<body>
<h2>Persons</h2>

<form action="person.do" method="post">
    Name: <input type="text" name="name" required/>
    Family: <input type="text" name="family" required/>
    National Code: <input type="text" name="nationalCode" required/>
    Gender:
    <select name="gender" required>
        <c:forEach var="g" items="${genders}">
            <option value="${g}">${g}</option>
        </c:forEach>
    </select>
    <button type="submit">Add Person</button>
</form>

<hr/>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Family</th>
        <th>National Code</th>
        <th>Gender</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="person" items="${personList}">
        <tr>
            <td>${person.id}</td>
            <td>${person.name}</td>
            <td>${person.family}</td>
            <td>${person.nationalCode}</td>
            <td>${person.gender}</td>
            <td>
                <form action="person.do" method="post" style="display:inline;">
                    <input type="hidden" name="_method" value="DELETE"/>
                    <input type="hidden" name="id" value="${person.id}"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
