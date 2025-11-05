<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت دپارتمان‌ها</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background: linear-gradient(135deg, #ccb5cd 0%, #f5576c 100%);
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
            border-color: #c8a5cd;
            box-shadow: 0 0 0 0.2rem rgba(240, 147, 251, 0.25);
        }
        .btn-custom {
            background: linear-gradient(135deg, #ffcefb 0%, #f5576c 100%);
            color: white;
            border: none;
            padding: 12px 40px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
        }
        .btn-home {
            background: linear-gradient(135deg, #1c3551 0%, #7fbbff 100%);
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
            background: linear-gradient(135deg, #743f8c 0%, #bc5766 100%);
            color: white;
        }
        .table tbody tr:hover {
            background-color: #fff5f7;
            transform: scale(1.005);
            transition: all 0.2s;
        }
        .required::after {
            content: " *";
            color: #e53e3e;
        }
        .branch-option {
            transition: all 0.3s ease;
        }

        .form-text {
            font-size: 0.875rem;
            color: #6c757d;
            margin-top: 0.25rem;
        }

        select:disabled {
            background-color: #e9ecef;
            opacity: 0.6;
        }
    </style>
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn-home">
        <i class="bi bi-house-door"></i> بازگشت به داشبورد
    </a>

    <h2 class="text-center">
        <i class="bi bi-diagram-3"></i> سامانه مدیریت دپارتمان‌ها
    </h2>

    <div class="form-section">
        <h4 class="mb-4">افزودن دپارتمان جدید</h4>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <strong>خطا!</strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill"></i>
                <strong>موفقیت!</strong> ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/department.do" method="post" id="departmentForm">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label required">نام دپارتمان</label>
                    <input type="text" name="name" id="name" class="form-control"
                           required placeholder="مثلاً: دپارتمان فناوری اطلاعات"
                           value="${param.name}">
                </div>

                <div class="col-md-6">
                    <label for="field" class="form-label required">رشته فعالیت</label>
                    <input type="text" name="field" id="field" class="form-control"
                           required placeholder="مثلاً: فناوری اطلاعات"
                           value="${param.field}">
                </div>

                <div class="col-md-6">
                    <label for="duty" class="form-label">وظیفه</label>
                    <input type="text" name="duty" id="duty" class="form-control"
                           placeholder="مثلاً: توسعه نرم‌افزار"
                           value="${param.duty}">
                </div>

                <div class="col-md-6">
                    <label for="phoneNumber" class="form-label">شماره تماس</label>
                    <input type="tel" name="phoneNumber" id="phoneNumber" class="form-control"
                           placeholder="مثلاً: 021-12345678"
                           value="${param.phoneNumber}">
                </div>

                <div class="col-md-6">
                    <label for="organizationName" class="form-label required">سازمان</label>
                    <select name="organizationName" id="organizationName" class="form-select" required
                            onchange="filterBranches()">
                        <option value="">-- لطفاً سازمان را انتخاب کنید --</option>
                        <c:forEach var="org" items="${organizationList}">
                            <option value="${org.name}"
                                ${param.organizationName eq org.name ? 'selected' : ''}>
                                    ${org.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="branchId" class="form-label required">شعبه</label>
                    <select name="branchId" id="branchId" class="form-select" required disabled>
                        <option value="">-- ابتدا سازمان را انتخاب کنید --</option>
                        <c:forEach var="branch" items="${branchList}">
                            <option value="${branch.id}"
                                    data-organization="${branch.organization.name}"
                                    class="branch-option"
                                    style="display: none;">
                                    ${branch.name}
                                <c:if test="${not empty branch.city}"> - ${branch.city}</c:if>
                            </option>
                        </c:forEach>
                    </select>
                    <div class="form-text" id="branchHelpText">
                        پس از انتخاب سازمان، شعبه‌های مربوطه نمایش داده می‌شوند
                    </div>
                </div>

                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-custom me-3">
                        <i class="bi bi-save"></i> ذخیره دپارتمان
                    </button>
                    <button type="reset" class="btn btn-secondary" onclick="resetForm()">
                        <i class="bi bi-arrow-clockwise"></i> بازنشانی فرم
                    </button>
                </div>
            </div>
        </form>
    </div>

    <div class="table-section">
        <h4 class="text-center mb-4">لیست دپارتمان‌های ثبت‌شده</h4>

        <c:if test="${empty departmentList}">
            <div class="alert alert-info text-center" role="alert">
                <i class="bi bi-info-circle"></i>
                هیچ دپارتمانی ثبت نشده است.
            </div>
        </c:if>

        <c:if test="${not empty departmentList}">
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle text-center">
                    <thead>
                    <tr>
                        <th>ردیف</th>
                        <th>نام دپارتمان</th>
                        <th>رشته فعالیت</th>
                        <th>وظیفه</th>
                        <th>شماره تماس</th>
                        <th>سازمان</th>
                        <th>شعبه</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="d" items="${departmentList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td><strong>${d.name}</strong></td>
                            <td>${d.field}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty d.duty}">${d.duty}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty d.phoneNumber}">${d.phoneNumber}</c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="badge bg-primary">${d.organization.name}</span>
                            </td>
                            <td>
                                <span class="badge bg-secondary">${d.branch.name}</span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="mt-3 text-center text-muted">
                <small>تعداد کل: ${departmentList.size()} دپارتمان</small>
            </div>
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function filterBranches() {
        const organizationSelect = document.getElementById('organizationName');
        const branchSelect = document.getElementById('branchId');
        const branchOptions = document.querySelectorAll('.branch-option');
        const helpText = document.getElementById('branchHelpText');

        const selectedOrganization = organizationSelect.value;

        branchOptions.forEach(option => {
            option.style.display = 'none';
        });

        let visibleCount = 0;

        if (!selectedOrganization) {
            helpText.textContent = 'پس از انتخاب سازمان، شعبه‌های مربوطه نمایش داده می‌شوند';
            branchSelect.disabled = true;
            branchSelect.value = '';
        } else {
            branchOptions.forEach(option => {
                if (option.getAttribute('data-organization') === selectedOrganization) {
                    option.style.display = '';
                    visibleCount++;
                }
            });

            if (visibleCount === 0) {
                helpText.textContent = 'این سازمان هیچ شعبه‌ای ندارد';
                branchSelect.disabled = true;
                branchSelect.innerHTML = '<option value="">-- این سازمان شعبه‌ای ندارد --</option>';
            } else {
                helpText.textContent = visibleCount + ' شعبه یافت شد. لطفاً یکی را انتخاب کنید.';
                branchSelect.disabled = false;
                branchSelect.value = '';
            }
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        const initialOrganization = document.getElementById('organizationName').value;
        if (initialOrganization) {
            filterBranches();

            const selectedBranchId = '${param.branchId}';
            if (selectedBranchId) {
                setTimeout(() => {
                    document.getElementById('branchId').value = selectedBranchId;
                }, 100);
            }
        }

        document.getElementById('organizationName').addEventListener('change', filterBranches);
    });

    document.getElementById('departmentForm').addEventListener('submit', function(e) {
        const name = document.getElementById('name').value.trim();
        const field = document.getElementById('field').value.trim();
        const organization = document.getElementById('organizationName').value;
        const branch = document.getElementById('branchId').value;

        let errors = [];

        if (!name) errors.push('نام دپارتمان اجباری است');
        if (!field) errors.push('رشته فعالیت اجباری است');
        if (!organization) errors.push('انتخاب سازمان اجباری است');
        if (!branch) errors.push('انتخاب شعبه اجباری است');

        if (errors.length > 0) {
            e.preventDefault();
            alert('لطفاً موارد زیر را بررسی کنید:\n' + errors.join('\n'));
        }
    });
</script>
</body>
</html>