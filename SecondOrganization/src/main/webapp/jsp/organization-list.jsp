<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>لیست سازمان‌ها</title>
    <style>
        body { font-family: Tahoma, Arial; direction: rtl; text-align: right; }
        .menu { background: #f0f0f0; padding: 10px; margin: 10px 0; }
        .menu a { margin: 0 10px; text-decoration: none; color: #0066cc; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 12px; text-align: right; }
        th { background-color: #f2f2f2; }
        .btn {
            padding: 8px 15px;
            background: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 5px;
        }
        .btn-edit { background: #ffc107; color: black; }
        .btn-delete { background: #dc3545; }
    </style>
</head>
<body>
<div class="menu">
    <a href="${pageContext.request.contextPath}/">صفحه اصلی</a>
    <a href="${pageContext.request.contextPath}/organization">مدیریت سازمان</a>
    <a href="${pageContext.request.contextPath}/department">مدیریت دپارتمان</a>
</div>

<h1>مدیریت سازمان‌ها</h1>

<a href="${pageContext.request.contextPath}/organization?action=new" class="btn">سازمان جدید</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>نام سازمان</th>
        <th>آدرس</th>
        <th>عملیات</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="org" items="${organizations}">
        <tr>
            <td>${org.id}</td>
            <td>${org.name}</td>
            <td>${org.address}</td>
            <td>
                <a href="${pageContext.request.contextPath}/organization?action=edit&id=${org.id}"
                   class="btn btn-edit">ویرایش</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>