<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="fa">
<head>
    <meta charset="UTF-8">
    <title>مدیریت دپارتمان‌ها</title>
    <link rel="stylesheet" href="../assets/css/form.css">
    <style>
        body {
            font-family: "Vazirmatn", sans-serif;
            direction: rtl;
            background: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%;
            margin: 30px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 3px 8px rgba(0,0,0,0.2);
        }
        h2 {
            text-align: center;
            color: #222;
        }
        form {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-top: 20px;
        }
        label {
            font-weight: bold;
        }
        input, select {
            padding: 8px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }
        .btn {
            grid-column: 1 / span 2;
            background-color: #28a745;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #218838;
        }
        table {
            width: 100%;
            margin-top: 30px;
            border-collapse: collapse;
            text-align: center;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .error {
            color: red;
            font-weight: bold;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>افزودن دپارتمان جدید</h2>

    ```
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="/department.do" method="post">
        <label for="field">رشته فعالیت:</label>
        <input type="text" name="field" id="field" required>

        <label for="duty">وظیفه:</label>
        <input type="text" name="duty" id="duty">

        <label for="phoneNumber">شماره تماس:</label>
        <input type="text" name="phoneNumber" id="phoneNumber">

        <label for="organizationName">نام سازمان:</label>
        <select name="organizationName" id="organizationName" required>
            <option value="">انتخاب کنید...</option>
            <c:forEach var="org" items="${organizationList}">
                <option value="${org.name}">${org.name}</option>
            </c:forEach>
        </select>

        <button type="submit" class="btn">ذخیره</button>
    </form>

    <h2>لیست دپارتمان‌ها</h2>
    <table>
        <thead>
        <tr>
            <th>کد</th>
            <th>رشته فعالیت</th>
            <th>وظیفه</th>
            <th>شماره تماس</th>
            <th>سازمان</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="d" items="${departmentList}">
            <tr>
                <td>${d.id}</td>
                <td>${d.field}</td>
                <td>${d.duty}</td>
                <td>${d.phoneNumber}</td>
                <td>${d.organization.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    ```

</div>
</body>
</html>
