<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت دپارتمان‌ها</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
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
            animation: slideIn 0.5s ease-out;
        }
        @keyframes slideIn {
            from { opacity: 0; transform: translateX(-50px); }
            to { opacity: 1; transform: translateX(0); }
        }
        h2, h4 {
            color: #d53369;
            font-weight: bold;
            margin-bottom: 25px;
        }
        .form-label {
            font-weight: 600;
            color: #4a5568;
        }
        .form-control, .form-select {
            border: 2px solid #fecaca;
            border-radius: 10px;
            padding: 12px;
            transition: all 0.3s;
        }
        .form-control:focus, .form-select:focus {
            border-color: #f093fb;
            box-shadow: 0 0 0 0.2rem rgba(240, 147, 251, 0.25);
        }
        .btn-custom {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            border: none;
            padding: 12px 40px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
        }
        .btn-custom:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(245, 87, 108, 0.5);
        }
        .alert-info {
            background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
            border: none;
            color: #2d3748;
            border-radius: 10px;
        }
        .table {
            border-radius: 10px;
            overflow: hidden;
        }
        .table thead {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }
        .table tbody tr:hover {
            background-color: #fff5f7;
            transform: scale(1.005);
            transition: all 0.2s;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">
        <i class="bi bi-diagram-3"></i> سامانه مدیریت دپارتمان‌ها
    </h2>

    <div class="form-section">
        <h4 class="mb-4">افزودن دپارتمان جدید</h4>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <strong>خطا!</strong> ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/department.do" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="field" class="form-label">رشته فعالیت *</label>
                    <input type="text" name="field" id="field" class="form-control"
                           required placeholder="مثلاً: فناوری اطلاعات">
                </div>
                <div class="col-md-6">
                    <label for="duty" class="form-label">وظیفه</label>
                    <input type="text" name="duty" id="duty" class="form-control"
                           placeholder="مثلاً: توسعه نرم‌افزار">
                </div>
                <div class="col-md-6">
                    <label for="phoneNumber" class="form-label">شماره تماس</label>
                    <input type="text" name="phoneNumber" id="phoneNumber" class="form-control"
                           placeholder="مثلاً: 021-12345678">
                </div>
                <div class="col-md-6">
                    <label for="organizationName" class="form-label">نام سازمان *</label>
                    <select name="organizationName" id="organizationName" class="form-select" required>
                        <option value="">-- انتخاب کنید --</option>
                        <c:forEach var="org" items="${organizationList}">
                            <option value="${org.name}">${org.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-custom">
                        <i class="bi bi-save"></i> ذخیره دپارتمان
                    </button>
                </div>
            </div>
        </form>
    </div>

    <div class="table-section">
        <h4 class="text-center mb-4">لیست دپارتمان‌های ثبت‌شده</h4>

        <c:if test="${empty departmentList}">
            <div class="alert alert-info text-center" role="alert">
                هیچ دپارتمانی ثبت نشده است.
            </div>
        </c:if>

        <c:if test="${not empty departmentList}">
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle text-center">
                    <thead>
                    <tr>
                        <th>ردیف</th>
                        <th>کد</th>
                        <th>رشته فعالیت</th>
                        <th>وظیفه</th>
                        <th>شماره تماس</th>
                        <th>سازمان</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="d" items="${departmentList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${d.id}</td>
                            <td><strong>${d.field}</strong></td>
                            <td>${d.duty}</td>
                            <td>${d.phoneNumber}</td>
                            <td>${d.organization.name}</td>
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