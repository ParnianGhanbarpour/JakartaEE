<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <title>مدیریت سازمان‌ها</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f7fc;
            font-family: "Vazirmatn", sans-serif;
        }
        .container {
            margin-top: 50px;
        }
        .form-section, .table-section {
            background: white;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            padding: 25px;
            margin-bottom: 40px;
        }
        .btn-custom {
            background-color: #0d6efd;
            color: white;
            border: none;
            transition: 0.3s;
        }
        .btn-custom:hover {
            background-color: #0b5ed7;
        }
        h2 {
            color: #0d6efd;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">مدیریت سازمان‌ها</h2>

    ```
    <div class="form-section">
        <form action="${pageContext.request.contextPath}/organisation.do" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">نام سازمان</label>
                    <input type="text" id="name" name="name" class="form-control" required placeholder="مثلاً سازمان مرکزی">
                </div>
                <div class="col-md-6">
                    <label for="type" class="form-label">نوع سازمان</label>
                    <input type="text" id="type" name="type" class="form-control" placeholder="مثلاً خصوصی، دولتی، ...">
                </div>
                <div class="col-12 text-center mt-3">
                    <button type="submit" class="btn btn-custom px-4">افزودن سازمان</button>
                </div>
            </div>
        </form>
    </div>

    <div class="table-section">
        <h4 class="text-center mb-3">لیست سازمان‌های ثبت‌شده</h4>
        <table class="table table-striped table-hover align-middle text-center">
            <thead class="table-primary">
            <tr>
                <th>شناسه</th>
                <th>نام سازمان</th>
                <th>نوع سازمان</th>
                <th>وضعیت</th>
                <th>عملیات</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="o" items="${organisationList}">
                <tr>
                    <td>${o.id}</td>
                    <td>${o.name}</td>
                    <td>${o.organizationType}</td>
                    <td>
                        <c:choose>
                            <c:when test="${o.deleted}">غیرفعال</c:when>
                            <c:otherwise>فعال</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/organisation.do" method="post" onsubmit="return confirm('آیا از حذف مطمئن هستید؟');">
                            <input type="hidden" name="_method" value="delete"/>
                            <input type="hidden" name="id" value="${o.id}"/>
                            <button type="submit" class="btn btn-danger btn-sm">حذف</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    ```

</div>
</body>
</html>
