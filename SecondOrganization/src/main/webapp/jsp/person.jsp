<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت پرسنل</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
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
            animation: bounceIn 0.6s ease-out;
        }
        @keyframes bounceIn {
            0% {
                opacity: 0;
                transform: scale(0.3);
            }
            50% {
                opacity: 1;
                transform: scale(1.05);
            }
            70% { transform: scale(0.9); }
            100% { transform: scale(1); }
        }
        h2, h4 {
            color: #c2410c;
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
        .form-control, .form-select {
            border: 2px solid #fed7aa;
            border-radius: 10px;
            padding: 12px;
            transition: all 0.3s;
        }
        .form-control:focus, .form-select:focus {
            border-color: #fa709a;
            box-shadow: 0 0 0 0.2rem rgba(250, 112, 154, 0.25);
        }
        .btn-custom {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            color: white;
            border: none;
            padding: 12px 40px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(250, 112, 154, 0.4);
        }
        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(254, 225, 64, 0.6);
            color: white;
        }
        .btn-danger {
            background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
            border: none;
            padding: 8px 20px;
            border-radius: 20px;
            transition: all 0.3s;
        }
        .btn-danger:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 15px rgba(239, 68, 68, 0.5);
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
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            color: white;
        }
        .table tbody tr:hover {
            background-color: #fef3c7;
            transition: all 0.2s;
        }
        .gender-badge {
            padding: 6px 12px;
            border-radius: 15px;
            font-weight: 600;
            display: inline-block;
        }
        .gender-male {
            background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
            color: white;
        }
        .gender-female {
            background: linear-gradient(135deg, #ec4899 0%, #db2777 100%);
            color: white;
        }
        .info-card {
            background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 20px;
            border-left: 4px solid #fa709a;
        }
        .info-card i {
            color: #c2410c;
            margin-left: 10px;
        }
        .salary-badge {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            color: white;
            padding: 5px 12px;
            border-radius: 15px;
            font-weight: 600;
        }
        .person-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            color: white;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
    </style>
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn-home">
        <i class="bi bi-house-door"></i> بازگشت به داشبورد
    </a>

    <div class="page-title">
        <i class="bi bi-person-badge"></i>
        <h2>سامانه مدیریت پرسنل سازمان</h2>
        <p>ثبت و مدیریت اطلاعات کارکنان و پرسنل</p>
    </div>

    <div class="info-card">
        <i class="bi bi-info-circle-fill"></i>
        <strong>راهنما:</strong> اطلاعات کامل پرسنل شامل نام، کد ملی، جنسیت و حقوق را در این بخش ثبت کنید.
    </div>

    <div class="form-section">
        <h4 class="mb-4">
            <i class="bi bi-person-plus"></i> افزودن پرسنل جدید
        </h4>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <strong>خطا!</strong> ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/person.do" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">
                        <i class="bi bi-person"></i> نام *
                    </label>
                    <input type="text" name="name" id="name" class="form-control"
                           required pattern="^[a-zA-Zآ-ی\s]{3,20}$"
                           placeholder="مثلاً: علی">
                </div>

                <div class="col-md-6">
                    <label for="family" class="form-label">
                        <i class="bi bi-people"></i> نام خانوادگی *
                    </label>
                    <input type="text" name="family" id="family" class="form-control"
                           required pattern="^[a-zA-Zآ-ی\s]{3,20}$"
                           placeholder="مثلاً: احمدی">
                </div>

                <div class="col-md-6">
                    <label for="nationalCode" class="form-label">
                        <i class="bi bi-card-text"></i> کد ملی *
                    </label>
                    <input type="text" name="nationalCode" id="nationalCode"
                           class="form-control" required pattern="^[0-9]{10}$"
                           maxlength="10" placeholder="مثلاً: 1234567890">
                </div>

                <div class="col-md-6">
                    <label for="gender" class="form-label">
                        <i class="bi bi-gender-ambiguous"></i> جنسیت *
                    </label>
                    <select name="gender" id="gender" class="form-select" required>
                        <option value="">-- انتخاب کنید --</option>
                        <c:forEach var="g" items="${genders}">
                            <option value="${g}">${g.title}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="salary" class="form-label">
                        <i class="bi bi-cash-coin"></i> حقوق (ریال)
                    </label>
                    <input type="number" name="salary" id="salary"
                           class="form-control" step="0.01" min="0"
                           placeholder="مثلاً: 15000000">
                </div>

                <div class="col-md-6">
                    <label for="birthdate" class="form-label">
                        <i class="bi bi-calendar-event"></i> تاریخ تولد
                    </label>
                    <input type="date" name="birthdate" id="birthdate"
                           class="form-control">
                </div>

                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-custom">
                        <i class="bi bi-save"></i> ذخیره اطلاعات پرسنل
                    </button>
                </div>
            </div>
        </form>
    </div>

    <div class="table-section">
        <h4 class="text-center mb-4">
            <i class="bi bi-people-fill"></i> لیست پرسنل ثبت‌شده
        </h4>

        <c:if test="${empty personList}">
            <div class="alert alert-info text-center" role="alert">
                <i class="bi bi-info-circle"></i>
                هیچ پرسنلی ثبت نشده است. لطفاً پرسنل جدید اضافه کنید.
            </div>
        </c:if>

        <c:if test="${not empty personList}">
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle">
                    <thead>
                    <tr>
                        <th class="text-center">ردیف</th>
                        <th>نام و نام خانوادگی</th>
                        <th class="text-center">کد ملی</th>
                        <th class="text-center">جنسیت</th>
                        <th class="text-center">حقوق</th>
                        <th class="text-center">تاریخ تولد</th>
                        <th class="text-center">عملیات</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="person" items="${personList}" varStatus="status">
                        <tr>
                            <td class="text-center"><strong>${status.index + 1}</strong></td>
                            <td>
                                <div class="d-flex align-items-center">
                                    <span class="person-avatar me-2">
                                            ${person.name.substring(0,1)}${person.family.substring(0,1)}
                                    </span>
                                    <strong>${person.name} ${person.family}</strong>
                                </div>
                            </td>
                            <td class="text-center">
                                <code>${person.nationalCode}</code>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${person.gender == 'male'}">
                                        <span class="gender-badge gender-male">
                                            <i class="bi bi-gender-male"></i> مرد
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="gender-badge gender-female">
                                            <i class="bi bi-gender-female"></i> زن
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <c:if test="${not empty person.salary}">
                                    <span class="salary-badge">
                                        <i class="bi bi-currency-dollar"></i>
                                        ${person.salary}
                                    </span>
                                </c:if>
                                <c:if test="${empty person.salary}">
                                    <small class="text-muted">-</small>
                                </c:if>
                            </td>
                            <td class="text-center">
                                <c:if test="${not empty person.birthdate}">
                                    <small>${person.birthdate}</small>
                                </c:if>
                                <c:if test="${empty person.birthdate}">
                                    <small class="text-muted">-</small>
                                </c:if>
                            </td>
                            <td class="text-center">
                                <form action="${pageContext.request.contextPath}/person.do"
                                      method="post"
                                      onsubmit="return confirm('آیا از حذف ${person.name} ${person.family} مطمئن هستید؟');">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <input type="hidden" name="id" value="${person.id}"/>
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
                    <strong>تعداد کل پرسنل:</strong>
                    <span class="badge bg-primary fs-6 ms-2">${personList.size()}</span>
                </div>
            </div>
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('nationalCode').addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        e.target.value = value.substring(0, 10);
    });

    document.querySelector('form').addEventListener('submit', function(e) {
        const nationalCode = document.getElementById('nationalCode').value;

        if (nationalCode.length !== 10) {
            e.preventDefault();
            alert('کد ملی باید دقیقاً 10 رقم باشد!');
            return false;
        }

        if (/^(\d)\1{9}$/.test(nationalCode)) {
            e.preventDefault();
            alert('کد ملی نامعتبر است! (تمام ارقام یکسان هستند)');
            return false;
        }
    });

    const salaryInput = document.getElementById('salary');
    if (salaryInput) {
        salaryInput.addEventListener('blur', function(e) {
            if (e.target.value) {
                const value = parseFloat(e.target.value);
                e.target.value = value.toLocaleString('en-US');
            }
        });
    }
</script>
</body>
</html>