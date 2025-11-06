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
        .search-section {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            padding: 30px;
            margin-bottom: 30px;
            animation: bounceIn 0.6s ease-out;
        }

        .btn-search {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
        }

        .btn-search:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(79, 172, 254, 0.5);
            color: white;
        }

        .btn-clear {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 25px;
            transition: all 0.3s;
        }

        .btn-clear:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(245, 87, 108, 0.5);
            color: white;
        }

        .projects-badge {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 5px 12px;
            border-radius: 15px;
            font-size: 12px;
            margin: 2px;
            display: inline-block;
        }

        .btn-view-projects {
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
            color: white;
            border: none;
            padding: 6px 15px;
            border-radius: 15px;
            font-size: 13px;
            transition: all 0.3s;
        }

        .btn-view-projects:hover {
            transform: scale(1.05);
            color: white;
        }

        .modal-content {
            border-radius: 20px;
            border: none;
        }

        .modal-header {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            color: white;
            border-radius: 20px 20px 0 0;
            border-bottom: none;
        }

        .project-item {
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 10px;
            transition: all 0.3s;
        }

        .project-item:hover {
            border-color: #fa709a;
            transform: translateX(-5px);
            box-shadow: 0 4px 15px rgba(250, 112, 154, 0.2);
        }

        .no-results {
            text-align: center;
            padding: 40px;
            color: #64748b;
        }

        .btn-close-white {
            filter: invert(1) grayscale(100%) brightness(200%);
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

    <div class="search-section">
        <h4 class="mb-4">
            <i class="bi bi-search"></i> جستجوی پرسنل
        </h4>
        <form method="get" action="${pageContext.request.contextPath}/person.do">
            <div class="row g-3">
                <div class="col-md-4">
                    <label for="searchName" class="form-label">نام</label>
                    <input type="text" id="searchName" name="searchName"
                           class="form-control" value="${param.searchName}"
                           placeholder="نام را وارد کنید">
                </div>
                <div class="col-md-4">
                    <label for="searchFamily" class="form-label">نام خانوادگی</label>
                    <input type="text" id="searchFamily" name="searchFamily"
                           class="form-control" value="${param.searchFamily}"
                           placeholder="نام خانوادگی را وارد کنید">
                </div>
                <div class="col-md-4">
                    <label for="searchNationalCode" class="form-label">کد ملی</label>
                    <input type="text" id="searchNationalCode" name="searchNationalCode"
                           class="form-control" value="${param.searchNationalCode}"
                           placeholder="کد ملی را وارد کنید" maxlength="10">
                </div>
                <div class="col-12 text-center">
                    <button type="submit" class="btn btn-search me-2">
                        <i class="bi bi-search"></i> جستجو
                    </button>
                    <a href="${pageContext.request.contextPath}/person.do"
                       class="btn btn-clear">
                        <i class="bi bi-x-circle"></i> پاک کردن فیلترها
                    </a>
                </div>
            </div>
        </form>
    </div>

    <c:if test="${param.success == 'true'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle-fill"></i>
        <strong>موفق!</strong> پرسنل با موفقیت ثبت شد.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    </c:if>

    <c:if test="${param.deleted == 'true'}">
    <div class="alert alert-info alert-dismissible fade show" role="alert">
        <i class="bi bi-info-circle-fill"></i>
        <strong>حذف شد!</strong> پرسنل با موفقیت حذف شد.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    </c:if>

    <div class="info-card">
        <i class="bi bi-info-circle-fill"></i>
        <strong>راهنما:</strong> اطلاعات کامل پرسنل شامل نام، کد ملی، جنسیت و حقوق را در این بخش ثبت کنید.
        <br><small>⚠️ کد ملی باید یکتا باشد و username اختیاری است (در صورت عدم ورود، خودکار ایجاد می‌شود)</small>
    </div>

    <div class="form-section">
        <h4 class="mb-4">
            <i class="bi bi-person-plus"></i> افزودن پرسنل جدید
        </h4>

        <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i>
            <strong>خطا!</strong> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/person.do" method="post" id="personForm">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">
                        <i class="bi bi-person"></i> نام *
                    </label>
                    <input type="text" name="name" id="name" class="form-control"
                           required minlength="3" maxlength="20"
                           placeholder="مثلا : محمد" >
                </div>
                <div class="col-md-6">
                    <label for="family" class="form-label">
                        <i class="bi bi-people"></i> نام خانوادگی *
                    </label>
                    <input type="text" name="family" id="family" class="form-control"
                           required minlength="3" maxlength="20"
                           placeholder="مثلاً: احمدی">
                </div>

                <div class="col-md-6">
                    <label for="nationalCode" class="form-label">
                        <i class="bi bi-card-text"></i> کد ملی * (باید یکتا باشد)
                    </label>
                    <input type="text" name="nationalCode" id="nationalCode"
                           class="form-control" required
                           pattern="[0-9]{10}" maxlength="10"
                           placeholder="1234567890">
                    <small class="text-muted">فقط 10 رقم عددی</small>
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
                        <i class="bi bi-cash-coin"></i> حقوق (ریال) - اختیاری
                    </label>
                    <input type="text" name="salary" id="salary"
                           class="form-control"
                           placeholder="مثلاً: 15,000,000">
                    <small class="text-muted">حداکثر 12 رقم + 2 رقم اعشار</small>
                </div>

                <div class="col-md-6">
                    <label for="birthdate" class="form-label">
                        <i class="bi bi-calendar-event"></i> تاریخ تولد - اختیاری
                    </label>
                    <input type="date" name="birthdate" id="birthdate"
                           class="form-control">
                </div>

                <div class="col-md-6">
                    <label for="username" class="form-label">
                        <i class="bi bi-person-circle"></i> نام کاربری - اختیاری
                    </label>
                    <input type="text" name="username" id="username"
                           class="form-control"
                           pattern="^[A-Za-z][A-Za-z0-9_]{4,19}$"
                           placeholder="در صورت خالی بودن خودکار ایجاد می‌شود">
                    <small class="text-muted">اگر خالی بماند، از کد ملی استفاده می‌شود</small>
                </div>

                <div class="col-md-6">
                    <label for="organizationGroupId" class="form-label">
                        <i class="bi bi-collection"></i> گروه سازمانی - اختیاری
                    </label>
                    <select name="organizationGroupId" id="organizationGroupId" class="form-select">
                        <option value="">-- بدون گروه --</option>
                        <c:forEach var="group" items="${organizationGroupList}">
                            <option value="${group.id}">
                                    ${group.name} - ${group.department.name}
                            </option>
                        </c:forEach>
                    </select>
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
            <div class="no-results">
                <i class="bi bi-inbox" style="font-size: 48px; color: #cbd5e1; margin-bottom: 15px;"></i>
                <h5 class="text-muted">
                    <c:choose>
                        <c:when test="${not empty param.searchName or not empty param.searchFamily or not empty param.searchNationalCode}">
                            نتیجه‌ای برای جستجوی شما یافت نشد!
                        </c:when>
                        <c:otherwise>
                            هیچ پرسنلی ثبت نشده است. لطفاً پرسنل جدید اضافه کنید.
                        </c:otherwise>
                    </c:choose>
                </h5>
                <c:if test="${not empty param.searchName or not empty param.searchFamily or not empty param.searchNationalCode}">
                    <a href="${pageContext.request.contextPath}/person.do" class="btn btn-custom mt-3">
                        <i class="bi bi-arrow-counterclockwise"></i> نمایش همه پرسنل
                    </a>
                </c:if>
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
                        <th>نام کاربری</th>
                        <th>گروه سازمانی</th>
                        <th class="text-center">پروژه‌ها</th>
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
                                <c:choose>
                                    <c:when test="${not empty person.salary}">
                                        <span class="salary-badge">
                                            <i class="bi bi-currency-dollar"></i>
                                            ${person.salary}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <small class="text-muted">-</small>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${not empty person.birthdate}">
                                        <small>${person.birthdate}</small>
                                    </c:when>
                                    <c:otherwise>
                                        <small class="text-muted">-</small>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty person.user and not empty person.user.username}">
                                        <span class="badge bg-success">
                                            <i class="bi bi-person-check"></i>
                                            ${person.user.username}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <small class="text-muted">
                                            <i class="bi bi-x-circle"></i> بدون کاربر
                                        </small>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty person.organizationGroup}">
                                        <small>
                                            <i class="bi bi-collection"></i>
                                                ${person.organizationGroup.name}
                                            <br>
                                            <small class="text-muted">${person.organizationGroup.department.name}</small>
                                        </small>
                                    </c:when>
                                    <c:otherwise>
                                        <small class="text-muted">بدون گروه</small>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${not empty person.projects && !empty person.projects}">
                                        <button type="button" class="btn btn-view-projects"
                                                data-bs-toggle="modal"
                                                data-bs-target="#projectsModal${person.id}">
                                            <i class="bi bi-kanban"></i> ${person.projects.size()} پروژه
                                        </button>

                                        <div class="modal fade" id="projectsModal${person.id}" tabindex="-1">
                                            <div class="modal-dialog modal-dialog-centered modal-lg">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">
                                                            <i class="bi bi-kanban"></i>
                                                            پروژه‌های ${person.name} ${person.family}
                                                        </h5>
                                                        <button type="button" class="btn-close btn-close-white"
                                                                data-bs-dismiss="modal"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <c:forEach var="project" items="${person.projects}">
                                                            <div class="project-item">
                                                                <div class="d-flex justify-content-between align-items-start">
                                                                    <div>
                                                                        <h6 class="mb-2">
                                                                            <i class="bi bi-bookmark-fill" style="color: #fa709a;"></i>
                                                                                ${project.title}
                                                                        </h6>
                                                                        <p class="mb-2 text-muted small">
                                                                                ${project.description}
                                                                        </p>
                                                                        <div class="d-flex gap-3 flex-wrap">
                                                                            <small>
                                                                                <i class="bi bi-calendar-check"></i>
                                                                                شروع: ${project.startDate}
                                                                            </small>
                                                                            <small>
                                                                                <i class="bi bi-calendar-x"></i>
                                                                                پایان: ${project.endDate}
                                                                            </small>
                                                                            <small>
                                                                                <i class="bi bi-cash-stack"></i>
                                                                                بودجه: ${project.budget}
                                                                            </small>
                                                                        </div>
                                                                    </div>
                                                                    <span class="badge"
                                                                          style="background: linear-gradient(135deg, #10b981 0%, #059669 100%);">
                                                                            ${project.status.persianTitle}
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary"
                                                                data-bs-dismiss="modal">بستن</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <small class="text-muted">
                                            <i class="bi bi-x-circle"></i> بدون پروژه
                                        </small>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <form action="${pageContext.request.contextPath}/person.do"
                                      method="post"
                                      onsubmit="return confirm('آیا از حذف ${person.name} ${person.family} مطمئن هستید؟\n\n⚠️ توجه: حذف پرسنل، کاربر مرتبط را حذف نمی‌کند.');">
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
                    <c:if test="${not empty param.searchName or not empty param.searchFamily or not empty param.searchNationalCode}">
                        <span class="ms-3 text-success">
                            <i class="bi bi-funnel"></i> جستجو فعال
                        </span>
                    </c:if>
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

    const salaryInput = document.getElementById('salary');
    salaryInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/,/g, '');

        value = value.replace(/[^\d.]/g, '');

        const parts = value.split('.');
        if (parts.length > 2) {
            value = parts[0] + '.' + parts.slice(1).join('');
        }

        if (parts[0].length > 12) {
            parts[0] = parts[0].substring(0, 12);
        }
        if (parts[1] && parts[1].length > 2) {
            parts[1] = parts[1].substring(0, 2);
        }

        value = parts.join('.');

        if (value) {
            const [integer, decimal] = value.split('.');
            const formatted = parseInt(integer).toLocaleString('en-US');
            e.target.value = decimal !== undefined ? formatted + '.' + decimal : formatted;
        }
    });

    document.getElementById('personForm').addEventListener('submit', function(e) {
        const nationalCode = document.getElementById('nationalCode').value;
        const name = document.getElementById('name').value.trim();
        const family = document.getElementById('family').value.trim();

        if (nationalCode.length !== 10) {
            e.preventDefault();
            alert(' کد ملی باید دقیقاً 10 رقم باشد!');
            document.getElementById('nationalCode').focus();
            return false;
        }

        if (/^(\d)\1{9}$/.test(nationalCode)) {
            e.preventDefault();
            alert(' کد ملی نامعتبر است! (تمام ارقام یکسان هستند)');
            document.getElementById('nationalCode').focus();
            return false;
        }

        if (name.length < 3 || family.length < 3) {
            e.preventDefault();
            alert(' نام و نام خانوادگی باید حداقل 3 حرف باشند!');
            return false;
        }

        const salaryValue = salaryInput.value.replace(/,/g, '');
        if (salaryValue && parseFloat(salaryValue) < 0) {
            e.preventDefault();
            alert(' حقوق نمی‌تواند منفی باشد!');
            salaryInput.focus();
            return false;
        }

        return true;
    });

    setTimeout(() => {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);

    const birthdateInput = document.getElementById('birthdate');
    const today = new Date().toISOString().split('T')[0];
    birthdateInput.setAttribute('max', today);
</script>
</body>
</html>

