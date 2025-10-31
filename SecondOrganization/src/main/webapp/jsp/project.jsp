<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت پروژه‌ها</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
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
            animation: slideInRight 0.5s ease-out;
        }
        @keyframes slideInRight {
            from {
                opacity: 0;
                transform: translateX(50px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }
        h2, h4 {
            color: #330867;
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
            color: white;
        }
        .page-title h2 {
            color: white;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }
        .page-title p {
            color: rgba(255,255,255,0.9);
        }
        .form-label {
            font-weight: 600;
            color: #2d3748;
        }
        .form-control, .form-select, .form-control[type="date"], .form-control[type="datetime-local"] {
            border: 2px solid #bfdbfe;
            border-radius: 10px;
            padding: 12px;
            transition: all 0.3s;
        }
        .form-control:focus, .form-select:focus {
            border-color: #30cfd0;
            box-shadow: 0 0 0 0.2rem rgba(48, 207, 208, 0.25);
        }
        .btn-custom {
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
            color: white;
            border: none;
            padding: 12px 40px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(48, 207, 208, 0.4);
        }
        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(51, 8, 103, 0.6);
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
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
            color: white;
        }
        .table tbody tr:hover {
            background-color: #eff6ff;
            transition: all 0.2s;
        }
        .badge-status {
            padding: 8px 15px;
            border-radius: 20px;
            font-weight: 600;
            display: inline-block;
        }
        .status-active {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            color: white;
        }
        .status-in-progress {
            background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
            color: white;
        }
        .status-completed {
            background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
            color: white;
        }
        .status-cancelled {
            background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
            color: white;
        }
        .info-card {
            background: linear-gradient(135deg, #e0f2fe 0%, #dbeafe 100%);
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 20px;
            border-left: 4px solid #30cfd0;
        }
        .info-card i {
            color: #330867;
            margin-left: 10px;
        }
        .budget-badge {
            background: linear-gradient(135deg, #fbbf24 0%, #f59e0b 100%);
            color: white;
            padding: 5px 12px;
            border-radius: 15px;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn-home">
        <i class="bi bi-house-door"></i> بازگشت به داشبورد
    </a>

    <div class="page-title">
        <i class="bi bi-kanban"></i>
        <h2>سامانه مدیریت پروژه‌های سازمانی</h2>
        <p>ایجاد، پیگیری و مدیریت پروژه‌های در حال اجرا</p>
    </div>

    <div class="info-card">
        <i class="bi bi-info-circle-fill"></i>
        <strong>راهنما:</strong> پروژه‌ها را با مشخصات کامل شامل بودجه، تاریخ شروع و پایان و وضعیت فعلی ثبت کنید.
    </div>

    <div class="form-section">
        <h4 class="mb-4">
            <i class="bi bi-plus-circle"></i> افزودن پروژه جدید
        </h4>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <strong>خطا!</strong> ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/project.do" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="title" class="form-label">
                        <i class="bi bi-bookmark"></i> عنوان پروژه *
                    </label>
                    <input type="text" name="title" id="title" class="form-control"
                           required placeholder="مثلاً: پروژه طراحی سایت">
                </div>

                <div class="col-md-6">
                    <label for="budget" class="form-label">
                        <i class="bi bi-cash-stack"></i> بودجه (ریال) *
                    </label>
                    <input type="number" name="budget" id="budget" class="form-control"
                           required step="0.01" min="0" placeholder="مثلاً: 50000000">
                </div>

                <div class="col-md-12">
                    <label for="description" class="form-label">
                        <i class="bi bi-file-text"></i> توضیحات پروژه *
                    </label>
                    <textarea name="description" id="description" class="form-control"
                              rows="3" required placeholder="توضیحات کامل پروژه را وارد کنید..."></textarea>
                </div>

                <div class="col-md-6">
                    <label for="startDate" class="form-label">
                        <i class="bi bi-calendar-check"></i> تاریخ شروع *
                    </label>
                    <input type="datetime-local" name="startDate" id="startDate"
                           class="form-control" required>
                </div>

                <div class="col-md-6">
                    <label for="endDate" class="form-label">
                        <i class="bi bi-calendar-x"></i> تاریخ پایان *
                    </label>
                    <input type="datetime-local" name="endDate" id="endDate"
                           class="form-control" required>
                </div>

                <div class="col-md-12">
                    <label for="status" class="form-label">
                        <i class="bi bi-flag"></i> وضعیت پروژه *
                    </label>
                    <select name="status" id="status" class="form-select" required>
                        <option value="">-- انتخاب کنید --</option>
                        <option value="ACTIVE">فعال</option>
                        <option value="IN_PROGRESS">در حال انجام</option>
                        <option value="COMPLETED">تکمیل شده</option>
                        <option value="CANCELLED">لغو شده</option>
                    </select>
                </div>

                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-custom">
                        <i class="bi bi-save"></i> ذخیره پروژه
                    </button>
                </div>
            </div>
        </form>
    </div>

    <div class="table-section">
        <h4 class="text-center mb-4">
            <i class="bi bi-list-check"></i> لیست پروژه‌های ثبت‌شده
        </h4>

        <c:if test="${empty projectList}">
            <div class="alert alert-info text-center" role="alert">
                <i class="bi bi-info-circle"></i>
                هیچ پروژه‌ای ثبت نشده است. لطفاً پروژه جدید اضافه کنید.
            </div>
        </c:if>

        <c:if test="${not empty projectList}">
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle">
                    <thead>
                    <tr>
                        <th class="text-center">ردیف</th>
                        <th>عنوان پروژه</th>
                        <th>توضیحات</th>
                        <th class="text-center">بودجه</th>
                        <th class="text-center">تاریخ شروع</th>
                        <th class="text-center">تاریخ پایان</th>
                        <th class="text-center">وضعیت</th>
                        <th class="text-center">عملیات</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="project" items="${projectList}" varStatus="status">
                        <tr>
                            <td class="text-center"><strong>${status.index + 1}</strong></td>
                            <td><strong>${project.title}</strong></td>
                            <td><small>${project.description}</small></td>
                            <td class="text-center">
                                <span class="budget-badge">
                                    <i class="bi bi-currency-exchange"></i>
                                    ${project.budget}
                                </span>
                            </td>
                            <td class="text-center">
                                <small>${project.startDate}</small>
                            </td>
                            <td class="text-center">
                                <small>${project.endDate}</small>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${project.status == 'ACTIVE'}">
                                        <span class="badge-status status-active">
                                            <i class="bi bi-play-circle"></i> فعال
                                        </span>
                                    </c:when>
                                    <c:when test="${project.status == 'IN_PROGRESS'}">
                                        <span class="badge-status status-in-progress">
                                            <i class="bi bi-hourglass-split"></i> در حال انجام
                                        </span>
                                    </c:when>
                                    <c:when test="${project.status == 'COMPLETED'}">
                                        <span class="badge-status status-completed">
                                            <i class="bi bi-check-circle"></i> تکمیل شده
                                        </span>
                                    </c:when>
                                    <c:when test="${project.status == 'CANCELLED'}">
                                        <span class="badge-status status-cancelled">
                                            <i class="bi bi-x-circle"></i> لغو شده
                                        </span>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <form action="${pageContext.request.contextPath}/project.do"
                                      method="post"
                                      onsubmit="return confirm('آیا از حذف پروژه «${project.title}» مطمئن هستید؟');">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <input type="hidden" name="id" value="${project.id}"/>
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

            <div class="mt-4 text-center">
                <div class="info-card d-inline-block">
                    <i class="bi bi-bar-chart-fill"></i>
                    <strong>تعداد کل پروژه‌ها:</strong>
                    <span class="badge bg-primary fs-6 ms-2">${projectList.size()}</span>
                </div>
            </div>
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.querySelector('form').addEventListener('submit', function(e) {
        const startDate = new Date(document.getElementById('startDate').value);
        const endDate = new Date(document.getElementById('endDate').value);
        const budget = parseFloat(document.getElementById('budget').value);

        if (endDate <= startDate) {
            e.preventDefault();
            alert('تاریخ پایان باید بعد از تاریخ شروع باشد!');
            return false;
        }

        if (budget <= 0) {
            e.preventDefault();
            alert('بودجه باید بیشتر از صفر باشد!');
            return false;
        }
    });

    document.getElementById('budget').addEventListener('input', function(e) {
        let value = e.target.value.replace(/,/g, '');
        if (!isNaN(value) && value !== '') {
            e.target.value = Number(value).toLocaleString('en-US');
        }
    });
</script>
</body>
</html>