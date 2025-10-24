<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>فرم سازمان</title>
    <style>
        body { font-family: Tahoma, Arial; direction: rtl; text-align: right; }
        .menu { background: #f0f0f0; padding: 10px; margin: 10px 0; }
        .menu a { margin: 0 10px; text-decoration: none; color: #0066cc; }
        .form-container { max-width: 500px; margin: 20px auto; }
        .form-group { margin: 15px 0; }
        label { display: block; margin-bottom: 5px; }
        input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            padding: 10px 20px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="menu">
    <a href="${pageContext.request.contextPath}/">صفحه اصلی</a>
    <a href="${pageContext.request.contextPath}/organization">مدیریت سازمان</a>
    <a href="${pageContext.request.contextPath}/department">مدیریت دپارتمان</a>
</div>

<div class="form-container">
    <h1>
        <c:choose>
            <c:when test="${not empty organization}">ویرایش سازمان</c:when>
            <c:otherwise>سازمان جدید</c:otherwise>
        </c:choose>
    </h1>

    <form action="${pageContext.request.contextPath}/organization" method="post">
        <c:if test="${not empty organization}">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="id" value="${organization.id}">
        </c:if>

        <div class="form-group">
            <label>نام سازمان:</label>
            <input type="text" name="name" value="${organization.name}" required>
        </div>

        <div class="form-group">
            <label>آدرس:</label>
            <input type="text" name="address" value="${organization.address}" required>
        </div>

        <button type="submit" class="btn">ذخیره</button>
        <a href="${pageContext.request.contextPath}/organization" style="margin-right: 10px;">لغو</a>
    </form>
</div>
</body>
</html>