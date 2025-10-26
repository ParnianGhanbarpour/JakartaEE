<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت سازمان‌ها</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: "Vazirmatn", Tahoma, Arial, sans-serif;
            min-height: 100vh;
            padding: 20px 0;
        }
        .container {
            margin-top: 30px;
        }
        .form-section, .table-section {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            padding: 30px;
            margin-bottom: 30px;
            animation: fadeIn 0.5s ease-in;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        h2 {
            color: #5a67d8;
            font-weight: bold;
            margin-bottom: 25px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
        }
        .form-label {
            font-weight: 600;
            color: #4a5568;
            margin-bottom: 8px;
        }
        .form-control, .form-select {
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            padding: 12px;
            transition: all 0.3s;
        }
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .btn-custom {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 12px 40px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
        }
        .table {
            border-radius: 10px;
            overflow: hidden;
        }
        .table thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .table tbody tr {
            transition: all 0.2s;
        }
        .table tbody tr:hover {
            background-color: #f7fafc;
            transform: scale(1.01);
        }
        .badge-status {
            padding: 8px 15px;
            border-radius: 20px;
            font-weight: 600;
        }
        .badge-active {
            background-color: #48bb78;
            color: white;
        }
        .badge-inactive {
            background-color: #f56565;
            color: white;
        }
        .btn-delete {
            background: linear-gradient(135deg, #fc466b 0%, #3f5efb 100%);
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 20px;
            transition: all 0.3s;
        }
        .btn-delete:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 15px rgba(252, 70, 107, 0.4);
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">
        <i class="bi bi-building"></i> سامانه مدیریت سازمان‌ها
    </h2>

    <div class="form-section">
        <h4 class="mb-4">افزودن سازمان جدید</h4>
        <form action="${pageContext.request.contextPath}/organisation.do" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">نام سازمان *</label>
                    <input type="text" id="name" name="name" class="form-control"
                           required placeholder="مثلاً: شرکت نرم‌افزاری پارس">
                </div>
                <div class="col-md-6">
                    <label for="type" class="form-label">نوع سازمان</label>
                    <input type="text" id="type" name="type" class="form-control"
                           placeholder="مثلاً: خصوصی، دولتی، آموزشی">
                </div>
                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-custom">
                        <i class="bi bi-plus-circle"></i> افزودن سازمان
                    </button>
                </div>
            </div>
        </form>
    </div>

    <div class="table-section">
        <h4 class="text-center mb-4">لیست سازمان‌های ثبت‌شده</h4>

        <c:if test="${empty organisationList}">
            <div class="alert alert-info text-center" role="alert">
                هیچ سازمانی ثبت نشده است. لطفاً سازمان جدید اضافه کنید.
            </div>
        </c:if>

        <c:if test="${not empty organisationList}">
            <div class="table-responsive">
                <table class="table table-hover align-middle text-center">
                    <thead>
                    <tr>
                        <th>ردیف</th>
                        <th>شناسه</th>
                        <th>نام سازمان</th>
                        <th>نوع سازمان</th>
                        <th>وضعیت</th>
                        <th>عملیات</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="o" items="${organisationList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${o.id}</td>
                            <td><strong>${o.name}</strong></td>
                            <td>${o.organizationType}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${o.deleted}">
                                        <span class="badge-status badge-inactive">غیرفعال</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-status badge-active">فعال</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/organisation.do"
                                      method="post"
                                      onsubmit="return confirm('آیا از حذف «${o.name}» مطمئن هستید؟');">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <input type="hidden" name="id" value="${o.id}"/>
                                    <button type="submit" class="btn btn-delete">
                                        <i class="bi bi-trash"></i> حذف
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>