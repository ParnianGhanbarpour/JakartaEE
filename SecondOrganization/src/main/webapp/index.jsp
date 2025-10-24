<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>سامانه مدیریت</title>
    <style>
        body { font-family: Tahoma, Arial; direction: rtl; text-align: center; }
        .menu { margin: 50px auto; width: 300px; }
        .menu a {
            display: block;
            margin: 20px 0;
            padding: 15px;
            background: #0066cc;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 18px;
        }
        .menu a:hover { background: #0055aa; }
    </style>
</head>
<body>
<h1>سامانه مدیریت سازمانی</h1>
<p>به سامانه مدیریت سازمان خوش آمدید</p>

<div class="menu">
    <a href="${pageContext.request.contextPath}/organization">مدیریت سازمان‌ها</a>
    <a href="${pageContext.request.contextPath}/department">مدیریت دپارتمان‌ها</a>
</div>
</body>
</html>