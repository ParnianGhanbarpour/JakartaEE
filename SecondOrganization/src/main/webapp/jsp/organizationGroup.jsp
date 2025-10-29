<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت گروه‌های سازمانی</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
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
            animation: fadeInUp 0.5s ease-out;
        }
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        h2, h4 {
            color: #16a34a;
            font-weight: bold;
            margin-bottom: 25px;
        }
        .page-title {
            text-align: center;
            margin-bottom: 40px;
        }
        .page-title i {
            font-size: 48px;
            margin-bottom: 10px;
            display: block;
        }
        .form-label {
            font-weight: 600;
            color: #2d3748;
        }
        .form-control, .form-select {
            border: 2px solid #bbf7d0;
            border-radius: 10px;
            padding: 12px;
            transition: all 0.3s;
        }
        .form-control:focus, .form-select:focus {
            border-color: #43e97b;
            box-shadow: 0 0 0 0.2rem rgba(67, 233, 123, 0.25);
        }
        .btn-custom {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            color: white;
            border: none;
            padding: 12px 40px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(67, 233, 123, 0.4);
        }
        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(56, 249, 215, 0.6);
            color: white;
        }
        .btn-danger {
            background: linear-gradient(135deg, #f857a6 0%, #ff5858 100%);
            border: none;
            padding: 8px 20px;
            border-radius: 20px;
            transition: all 0.3s;
        }
        .btn-danger:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 15px rgba(248, 87, 166, 0.5);
        }
        .btn-home {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 10px 30px;
            border-radius: 25px;
            font-weight: 600;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
            margin-bottom: 20px;
        }
        .btn-home:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5);
            color: white;
        }
        .table {
            border-radius: 10px;
            overflow: hidden;
        }
        .table thead {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            color: white;
        }
        .table tbody tr:hover {
            background-color: #ecfdf5;
            transition: all 0.2s;
        }
        .badge-status {
            padding: 8px 15px;
            border-radius: 20px;
            font-weight: 600;
            display: inline-block;
        }
        .badge-active {
            background-color: #10b981;
            color: white;
        }
        .badge-inactive {
            background-color: #ef4444;
            color: white;
        }
        .alert-info {
            background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
            border: none;
            color: #2d3748;
            border-radius: 10px;
        }
        .info-card {
            background: linear-gradient(135deg, #e0f2fe 0%, #dbeafe 100%);
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 20px;
            border-left: 4px solid #43e97b;
        }
        .info-card i {
            color: #16a34a;
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- دکمه بازگشت به صفحه اصلی -->
    <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn-home">
        <i class="bi bi-house-door"></i> بازگشت به داشبورد
    </a>

    <!-- عنوان صفحه -->
    <div class="page-title">
        <i class="bi bi-collection"></i>
        <h2>سامانه مدیریت گروه‌های سازمانی</h2>
        <p class="text-muted">ایجاد و مدیریت گروه‌های کاری در دپارتمان‌های مختلف</p>
    </div>

    <!-- راهنمای استفاده -->
    <div class="info-card">
        <i class="bi bi-info-circle-fill"></i>
        <strong>راهنما:</strong> گروه‌های سازمانی، تیم‌های کاری در هر دپارتمان هستند. هر گروه می‌تواند دارای تخصص خاصی باشد.
    </div>

    <!-- فرم افزودن گروه -->
    <div class="form-section">
        <h4 class="mb-4">
            <i class="bi bi-plus-circle"></i> افزودن گروه سازمانی جدید
        </h4>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <strong>خطا!</strong> ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/organizationGroup.do" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">
                        <i class="bi bi-tag"></i> نام گروه *
                    </label>
                    <input type="text" name="name" id="name" class="form-control"
                           required placeholder="مثلاً: گروه توسعه نرم‌افزار">
                </div>

                <div class="col-md-6">
                    <label for="specialty" class="form-label">
                        <i class="bi bi-star"></i> تخصص گروه *
                    </label>
                    <input type="text" name="specialty" id="specialty" class="form-control"
                           required placeholder="مثلاً: برنامه‌نویسی وب">
                </div>

                <div class="col-md-12">
                    <label for="departmentId" class="form-label">
                        <i class="bi bi-diagram-3"></i> انتخاب دپارتمان *
                    </label>
                    <select name="departmentId" id="departmentId" class="form-select" required>
                        <option value="">-- انتخاب کنید --</option>
                        <c:forEach var="dept" items="${departmentList}">
                            <option value="${dept.id}">
                                    ${dept.name} - ${dept.field}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-custom">
                        <i class="bi bi-save"></i> ذخیره گروه سازمانی
                    </button>
                </div>
            </div>
        </form>
    </div>

    <!-- جدول گروه‌ها -->
    <div class="table-section">
        <h4 class="text-center mb-4">
            <i class="bi bi-list-ul"></i> لیست گروه‌های سازمانی ثبت‌شده
        </h4>

        <c:if test="${empty organizationGroupList}">
            <div class="alert alert-info text-center" role="alert">
                <i class="bi bi-info-circle"></i>
                هیچ گروه سازمانی ثبت نشده است. لطفاً گروه جدید اضافه کنید.
            </div>
        </c:if>

        <c:if test="${not empty organizationGroupList}">
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle text-center">
                    <thead>
                    <tr>
                        <th><i class="bi bi-hash"></i> ردیف</th>
                        <th><i class="bi bi-key"></i> کد گروه</th>
                        <th><i class="bi bi-tag"></i> نام گروه</th>
                        <th><i class="bi bi-star"></i> تخصص</th>
                        <th><i class="bi bi-diagram-3"></i> دپارتمان</th>
                        <th><i class="bi bi-building"></i> رشته فعالیت</th>
                        <th><i class="bi bi-toggle-on"></i> وضعیت</th>
                        <th><i class="bi bi-tools"></i> عملیات</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="group" items="${organizationGroupList}" varStatus="status">
                        <tr>
                            <td><strong>${status.index + 1}</strong></td>
                            <td><span class="badge bg-primary">${group.id}</span></td>
                            <td><strong>${group.name}</strong></td>
                            <td>
                                <span class="badge bg-info text-dark">
                                    <i class="bi bi-star-fill"></i> ${group.specialty}
                                </span>
                            </td>
                            <td>${group.department.name}</td>
                            <td>
                                <small class="text-muted">${group.department.field}</small>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${group.deleted}">
                                        <span class="badge-status badge-inactive">
                                            <i class="bi bi-x-circle"></i> غیرفعال
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-status badge-active">
                                            <i class="bi bi-check-circle"></i> فعال
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/organizationGroup.do"
                                      method="post"
                                      onsubmit="return confirm('آیا از حذف گروه «${group.name}» مطمئن هستید؟\n\nتوجه: این عملیات قابل بازگشت نیست!');">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <input type="hidden" name="id" value="${group.id}"/>
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="bi bi-trash"></i> حذف
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- آمار کلی -->
            <div class="mt-4 text-center">
                <div class="info-card d-inline-block">
                    <i class="bi bi-bar-chart-fill"></i>
                    <strong>تعداد کل گروه‌های ثبت‌شده:</strong>
                    <span class="badge bg-success fs-6 ms-2">${organizationGroupList.size()}</span>
                </div>
            </div>
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // افزودن انیمیشن به جدول
    document.addEventListener('DOMContentLoaded', function() {
        const rows = document.querySelectorAll('tbody tr');
        rows.forEach((row, index) => {
            row.style.animationDelay = `${index * 0.05}s`;
            row.classList.add('fade-in-row');
        });
    });

    // اعتبارسنجی فرم
    document.querySelector('form').addEventListener('submit', function(e) {
        const name = document.getElementById('name').value.trim();
        const specialty = document.getElementById('specialty').value.trim();
        const departmentId = document.getElementById('departmentId').value;

        if (name.length < 3) {
            e.preventDefault();
            alert('نام گروه باید حداقل ۳ کاراکتر باشد!');
            return false;
        }

        if (specialty.length < 3) {
            e.preventDefault();
            alert('تخصص گروه باید حداقل ۳ کاراکتر باشد!');
            return false;
        }

        if (!departmentId) {
            e.preventDefault();
            alert('لطفاً دپارتمان را انتخاب کنید!');
            return false;
        }
    });
</script>

<style>
    @keyframes fadeInRow {
        from {
            opacity: 0;
            transform: translateX(-20px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }

    .fade-in-row {
        animation: fadeInRow 0.5s ease-out forwards;
        opacity: 0;
    }
</style>
</body>
</html>