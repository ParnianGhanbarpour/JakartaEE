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
        .form-section, .table-section, .search-section {
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
        .form-control, .form-select {
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
        .btn-members {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 20px;
            font-size: 13px;
            transition: all 0.3s;
        }
        .btn-members:hover {
            transform: scale(1.05);
            color: white;
        }
        .table thead {
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
            color: white;
        }
        .table tbody tr:hover {
            background-color: #f0fdfa;
            transition: all 0.2s;
        }
        .members-selector {
            max-height: 200px;
            overflow-y: auto;
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            padding: 15px;
            background: #f8fafc;
        }
        .member-checkbox {
            display: flex;
            align-items: center;
            padding: 8px;
            margin-bottom: 8px;
            background: white;
            border-radius: 8px;
            transition: all 0.2s;
        }
        .member-checkbox:hover {
            background: #e0f2fe;
            transform: translateX(-5px);
        }
        .member-checkbox input[type="checkbox"] {
            margin-left: 10px;
            width: 18px;
            height: 18px;
            accent-color: #30cfd0;
        }
        .member-checkbox label {
            margin: 0;
            cursor: pointer;
            flex: 1;
        }
        .member-badge {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
            margin: 2px;
            display: inline-block;
        }
        .status-badge {
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
        .budget-badge {
            background: linear-gradient(135deg, #fbbf24 0%, #f59e0b 100%);
            color: white;
            padding: 5px 12px;
            border-radius: 15px;
            font-weight: 600;
        }
        .member-card {
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            padding: 12px;
            margin-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: all 0.3s;
        }
        .member-card:hover {
            border-color: #30cfd0;
            transform: translateX(-5px);
            box-shadow: 0 4px 15px rgba(48, 207, 208, 0.2);
        }
        .person-selector {
            max-height: 300px;
            overflow-y: auto;
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            padding: 15px;
        }
        .person-checkbox {
            display: flex;
            align-items: center;
            padding: 10px;
            margin-bottom: 8px;
            background: white;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.2s;
        }
        .person-checkbox:hover {
            background: #e0f2fe;
            transform: translateX(-5px);
        }
        .person-checkbox input[type="checkbox"] {
            margin-left: 10px;
            width: 20px;
            height: 20px;
            accent-color: #30cfd0;
        }
        .modal-content {
            border-radius: 20px;
            border: none;
        }
        .modal-header {
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
            color: white;
            border-radius: 20px 20px 0 0;
            border-bottom: none;
        }
        .btn-close-white {
            filter: invert(1) grayscale(100%) brightness(200%);
        }
        .no-results {
            text-align: center;
            padding: 40px;
            color: #64748b;
        }
        .project-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            color: white;
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
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
        <p>ایجاد، پیگیری و مدیریت پروژه‌ها با تخصیص اعضا</p>
    </div>

    <div class="search-section">
        <h4 class="mb-4">
            <i class="bi bi-search"></i> جستجوی پروژه‌ها
        </h4>
        <form method="get" action="${pageContext.request.contextPath}/project.do">
            <div class="row g-3">
                <div class="col-md-4">
                    <label for="searchTitle" class="form-label">عنوان پروژه</label>
                    <input type="text" id="searchTitle" name="searchTitle"
                           class="form-control" value="${param.searchTitle}"
                           placeholder="عنوان پروژه را وارد کنید">
                </div>
                <div class="col-md-4">
                    <label for="searchStatus" class="form-label">وضعیت</label>
                    <select id="searchStatus" name="searchStatus" class="form-select">
                        <option value="">-- همه وضعیت‌ها --</option>
                        <option value="ACTIVE" ${param.searchStatus == 'ACTIVE' ? 'selected' : ''}>فعال</option>
                        <option value="IN_PROGRESS" ${param.searchStatus == 'IN_PROGRESS' ? 'selected' : ''}>در حال انجام</option>
                        <option value="COMPLETED" ${param.searchStatus == 'COMPLETED' ? 'selected' : ''}>تکمیل شده</option>
                        <option value="CANCELLED" ${param.searchStatus == 'CANCELLED' ? 'selected' : ''}>لغو شده</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="searchBudget" class="form-label">حداکثر بودجه</label>
                    <input type="text" id="searchBudget" name="searchBudget"
                           class="form-control" value="${param.searchBudget}"
                           placeholder="مثلاً: 50,000,000">
                </div>
                <div class="col-12 text-center">
                    <button type="submit" class="btn btn-search me-2">
                        <i class="bi bi-search"></i> جستجو
                    </button>
                    <a href="${pageContext.request.contextPath}/project.do"
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
        <strong>موفق!</strong> پروژه با موفقیت ثبت شد.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    </c:if>

    <c:if test="${param.deleted == 'true'}">
    <div class="alert alert-info alert-dismissible fade show" role="alert">
        <i class="bi bi-info-circle-fill"></i>
        <strong>حذف شد!</strong> پروژه با موفقیت حذف شد.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </c:if>

        <div class="info-card">
            <i class="bi bi-info-circle-fill"></i>
            <strong>راهنما:</strong> پروژه‌ها را با مشخصات کامل شامل بودجه، تاریخ، وضعیت و انتخاب اعضای تیم ثبت کنید.
            <br><small>⚠️ تاریخ پایان باید بعد از تاریخ شروع باشد و بودجه باید معتبر باشد</small>
        </div>

        <div class="form-section">
            <h4 class="mb-4">
                <i class="bi bi-plus-circle"></i> افزودن پروژه جدید
            </h4>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill"></i>
                    <strong>خطا!</strong> ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/project.do" method="post" id="projectForm">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="title" class="form-label">
                            <i class="bi bi-bookmark"></i> عنوان پروژه *
                        </label>
                        <input type="text" name="title" id="title" class="form-control"
                               required minlength="3" maxlength="100"
                               placeholder="مثلاً: پروژه طراحی سایت سازمانی">
                    </div>

                    <div class="col-md-6">
                        <label for="budget" class="form-label">
                            <i class="bi bi-cash-stack"></i> بودجه (ریال) *
                        </label>
                        <input type="text" name="budget" id="budget" class="form-control"
                               required placeholder="مثلاً: 50,000,000">
                        <input type="hidden" name="budgetValue" id="budgetValue">
                        <small class="text-muted">حداکثر 12 رقم + 2 رقم اعشار</small>
                    </div>

                    <div class="col-md-12">
                        <label for="description" class="form-label">
                            <i class="bi bi-file-text"></i> توضیحات پروژه *
                        </label>
                        <textarea name="description" id="description" class="form-control"
                                  rows="3" required minlength="10" maxlength="500"
                                  placeholder="توضیحات کامل پروژه را وارد کنید..."></textarea>
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

                    <div class="col-md-6">
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

                    <div class="col-md-6">
                        <label class="form-label">
                            <i class="bi bi-people"></i> اعضای پروژه - اختیاری
                        </label>
                        <div class="members-selector">
                            <c:if test="${empty personList}">
                                <div class="alert alert-warning mb-0">
                                    <i class="bi bi-exclamation-triangle"></i>
                                    هیچ پرسنلی برای انتخاب وجود ندارد.
                                </div>
                            </c:if>
                            <c:forEach var="person" items="${personList}">
                                <div class="member-checkbox">
                                    <input type="checkbox" name="personIds" value="${person.id}"
                                           id="person_${person.id}">
                                    <label for="person_${person.id}">
                                        <strong>${person.name} ${person.family}</strong>
                                        <small class="text-muted ms-2">(${person.nationalCode})</small>
                                        <c:if test="${not empty person.organizationGroup}">
                                        <span class="badge bg-secondary ms-2">
                                                ${person.organizationGroup.name}
                                        </span>
                                        </c:if>
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
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
                <div class="no-results">
                    <i class="bi bi-inbox" style="font-size: 48px; color: #cbd5e1; margin-bottom: 15px;"></i>
                    <h5 class="text-muted">
                        <c:choose>
                            <c:when test="${not empty param.searchTitle or not empty param.searchStatus or not empty param.searchBudget}">
                                نتیجه‌ای برای جستجوی شما یافت نشد!
                            </c:when>
                            <c:otherwise>
                                هیچ پروژه‌ای ثبت نشده است. لطفاً پروژه جدید اضافه کنید.
                            </c:otherwise>
                        </c:choose>
                    </h5>
                    <c:if test="${not empty param.searchTitle or not empty param.searchStatus or not empty param.searchBudget}">
                        <a href="${pageContext.request.contextPath}/project.do" class="btn btn-custom mt-3">
                            <i class="bi bi-arrow-counterclockwise"></i> نمایش همه پروژه‌ها
                        </a>
                    </c:if>
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
                            <th class="text-center">اعضا</th>
                            <th class="text-center">عملیات</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="project" items="${projectList}" varStatus="status">
                            <tr>
                                <td class="text-center"><strong>${status.index + 1}</strong></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                    <span class="project-avatar me-2">
                                            ${project.title.substring(0,1)}
                                    </span>
                                        <strong>${project.title}</strong>
                                    </div>
                                </td>
                                <td>
                                    <small class="text-muted">${project.description}</small>
                                </td>
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
                                        <span class="status-badge status-active">
                                            <i class="bi bi-play-circle"></i> فعال
                                        </span>
                                        </c:when>
                                        <c:when test="${project.status == 'IN_PROGRESS'}">
                                        <span class="status-badge status-in-progress">
                                            <i class="bi bi-hourglass-split"></i> در حال انجام
                                        </span>
                                        </c:when>
                                        <c:when test="${project.status == 'COMPLETED'}">
                                        <span class="status-badge status-completed">
                                            <i class="bi bi-check-circle"></i> تکمیل شده
                                        </span>
                                        </c:when>
                                        <c:when test="${project.status == 'CANCELLED'}">
                                        <span class="status-badge status-cancelled">
                                            <i class="bi bi-x-circle"></i> لغو شده
                                        </span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <button type="button" class="btn btn-members"
                                            data-bs-toggle="modal"
                                            data-bs-target="#membersModal${project.id}">
                                        <i class="bi bi-people"></i>
                                            ${project.persons != null ? project.persons.size() : 0} نفر
                                    </button>

                                    <div class="modal fade" id="membersModal${project.id}" tabindex="-1">
                                        <div class="modal-dialog modal-dialog-centered modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">
                                                        <i class="bi bi-people-fill"></i>
                                                        مدیریت اعضای پروژه: ${project.title}
                                                    </h5>
                                                    <button type="button" class="btn-close btn-close-white"
                                                            data-bs-dismiss="modal"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <h6 class="mb-3">
                                                        <i class="bi bi-person-check-fill"></i> اعضای فعلی
                                                    </h6>
                                                    <c:choose>
                                                        <c:when test="${empty project.persons}">
                                                            <div class="alert alert-info">
                                                                <i class="bi bi-info-circle"></i>
                                                                هیچ عضوی به این پروژه اختصاص داده نشده است.
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var="member" items="${project.persons}">
                                                                <div class="member-card">
                                                                    <div class="d-flex align-items-center gap-2">
                                                                    <span style="width: 40px; height: 40px; border-radius: 50%;
                                                                                 background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                                                                 color: white; display: inline-flex;
                                                                                 align-items: center; justify-content: center;
                                                                                 font-weight: bold;">
                                                                            ${member.name.substring(0,1)}${member.family.substring(0,1)}
                                                                    </span>
                                                                        <div>
                                                                            <strong>${member.name} ${member.family}</strong>
                                                                            <br>
                                                                            <small class="text-muted">
                                                                                <i class="bi bi-card-text"></i> ${member.nationalCode}
                                                                            </small>
                                                                        </div>
                                                                    </div>
                                                                    <form action="${pageContext.request.contextPath}/project.do"
                                                                          method="post" style="display:inline;">
                                                                        <input type="hidden" name="action" value="removeMember"/>
                                                                        <input type="hidden" name="projectId" value="${project.id}"/>
                                                                        <input type="hidden" name="personId" value="${member.id}"/>
                                                                        <button type="submit"
                                                                                class="btn btn-danger btn-sm"
                                                                                onclick="return confirm('آیا از حذف ${member.name} ${member.family} از این پروژه مطمئن هستید؟');">
                                                                            <i class="bi bi-x-circle"></i> حذف
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <hr class="my-4">

                                                    <h6 class="mb-3">
                                                        <i class="bi bi-person-plus-fill"></i> افزودن اعضای جدید
                                                    </h6>
                                                    <form action="${pageContext.request.contextPath}/project.do"
                                                          method="post">
                                                        <input type="hidden" name="action" value="addMembers"/>
                                                        <input type="hidden" name="projectId" value="${project.id}"/>

                                                        <div class="person-selector">
                                                            <c:if test="${empty personList}">
                                                                <div class="alert alert-warning">
                                                                    <i class="bi bi-exclamation-triangle"></i>
                                                                    هیچ پرسنلی برای افزودن وجود ندارد.
                                                                </div>
                                                            </c:if>

                                                            <c:forEach var="person" items="${personList}">
                                                                <c:set var="isMember" value="false"/>
                                                                <c:forEach var="member" items="${project.persons}">
                                                                    <c:if test="${member.id == person.id}">
                                                                        <c:set var="isMember" value="true"/>
                                                                    </c:if>
                                                                </c:forEach>

                                                                <c:if test="${!isMember}">
                                                                    <label class="person-checkbox">
                                                                        <input type="checkbox"
                                                                               name="personIds"
                                                                               value="${person.id}">
                                                                        <span class="flex-fill">
                                                                        <strong>${person.name} ${person.family}</strong>
                                                                        <br>
                                                                        <small class="text-muted">
                                                                            <i class="bi bi-card-text"></i> ${person.nationalCode}
                                                                            <c:if test="${not empty person.organizationGroup}">
                                                                                | <i class="bi bi-collection"></i> ${person.organizationGroup.name}
                                                                            </c:if>
                                                                        </small>
                                                                    </span>
                                                                    </label>
                                                                </c:if>
                                                            </c:forEach>
                                                        </div>

                                                        <div class="mt-3 text-center">
                                                            <button type="submit" class="btn btn-success">
                                                                <i class="bi bi-plus-circle"></i> افزودن اعضای انتخاب‌شده
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">بستن</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/project.do"
                                          method="post"
                                          onsubmit="return confirm('آیا از حذف پروژه «${project.title}» مطمئن هستید؟\n\n⚠️ توجه: این عمل قابل بازگشت نیست!');"
                                          style="display: inline;">
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
                        <c:if test="${not empty param.searchTitle or not empty param.searchStatus or not empty param.searchBudget}">
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
        const budgetInput = document.getElementById('budget');
        const budgetValueInput = document.getElementById('budgetValue');

        budgetInput.addEventListener('input', function(e) {
            let value = e.target.value.replace(/,/g, '').replace(/[^\d.]/g, '');

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
                const formatted = parseInt(integer || '0').toLocaleString('en-US');
                e.target.value = decimal !== undefined ? formatted + '.' + decimal : formatted;
                budgetValueInput.value = value;
            } else {
                budgetValueInput.value = '';
            }
        });

        const form = document.getElementById('projectForm');
        form.addEventListener('submit', function(e) {
            const startDate = new Date(document.getElementById('startDate').value);
            const endDate = new Date(document.getElementById('endDate').value);
            const budgetValue = budgetValueInput.value.replace(/,/g, '');
            const title = document.getElementById('title').value.trim();
            const description = document.getElementById('description').value.trim();

            if (endDate <= startDate) {
                e.preventDefault();
                alert('⚠️ تاریخ پایان باید بعد از تاریخ شروع باشد!');
                return false;
            }

            if (!budgetValue || parseFloat(budgetValue) <= 0) {
                e.preventDefault();
                alert('⚠️ بودجه باید بیشتر از صفر باشد!');
                return false;
            }

            if (title.length < 3) {
                e.preventDefault();
                alert('⚠️ عنوان پروژه باید حداقل 3 حرف باشد!');
                return false;
            }

            if (description.length < 10) {
                e.preventDefault();
                alert('⚠️ توضیحات پروژه باید حداقل 10 حرف باشد!');
                return false;
            }

            budgetInput.value = budgetValue;
        });

        const startDateInput = document.getElementById('startDate');
        const endDateInput = document.getElementById('endDate');
        const now = new Date();
        const today = now.toISOString().slice(0, 16);

        startDateInput.setAttribute('min', today);
        endDateInput.setAttribute('min', today);

        startDateInput.addEventListener('change', function() {
            endDateInput.setAttribute('min', this.value);
        });

        setTimeout(() => {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>