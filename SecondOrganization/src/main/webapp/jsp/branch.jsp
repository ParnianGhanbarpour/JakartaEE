<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت شعب</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
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
            animation: zoomIn 0.5s ease-out;
        }
        @keyframes zoomIn {
            from { opacity: 0; transform: scale(0.9); }
            to { opacity: 1; transform: scale(1); }
        }
        h2, h4 {
            color: #0077b6;
            font-weight: bold;
            margin-bottom: 25px;
        }
        .form-label {
            font-weight: 600;
            color: #2d3748;
        }
        .form-control, .form-select {
            border: 2px solid #bee3f8;
            border-radius: 10px;
            padding: 12px;
            transition: all 0.3s;
        }
        .form-control:focus, .form-select:focus {
            border-color: #4facfe;
            box-shadow: 0 0 0 0.2rem rgba(79, 172, 254, 0.25);
        }
        .btn-custom {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
            border: none;
            padding: 12px 40px;
            border-radius: 25px;
            font-weight: bold;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(79, 172, 254, 0.4);
        }
        .btn-custom:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0, 242, 254, 0.6);
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
        .table {
            border-radius: 10px;
            overflow: hidden;
        }
        .table thead {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }
        .table tbody tr:hover {
            background-color: #e6f7ff;
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
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">
        <i class="bi bi-geo-alt"></i> سامانه مدیریت شعب سازمان
    </h2>

    <div class="form-section">
        <h4 class="mb-4">افزودن شعبه جدید</h4>
        <form action="${pageContext.request.contextPath}/branch.do" method="post">
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="name" class="form-label">نام شعبه *</label>
                    <input type="text" id="name" name="name" class="form-control"
                           required placeholder="مثلاً: شعبه مرکزی">
                </div>
                <div class="col-md-6">
                    <label for="manager" class="form-label">نام مدیر شعبه *</label>
                    <input type="text" id="manager" name="manager" class="form-control"
                           required placeholder="مثلاً: علی احمدی">
                </div>
                <div class="col-md-6">
                    <label for="city" class="form-label">شهر</label>
                    <input type="text" id="city" name="city" class="form-control"
                           placeholder="مثلاً: تهران">
                </div>
                <div class="col-md-6">
                    <label for="address" class="form-label">آدرس</label>
                    <input type="text" id="address" name="address" class="form-control"
                           placeholder="خیابان ...، پلاک ...">
                </div>
                <div class="col-md-12">
                    <label for="organizationId" class="form-label">سازمان *</label>
                    <select id="organizationId" name="organizationId" class="form-select" required>
                        <option value="">-- انتخاب سازمان --</option>
                        <c:forEach var="org" items="${organizationList}">
                            <option value="${org.id}">${org.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-custom">
                        <i class="bi bi-plus-circle"></i> افزودن شعبه
                    </button>
                </div>
            </div>
        </form>
    </div>

    <div class="table-section">
        <h4 class="text-center mb-4">لیست شعب ثبت‌شده</h4>

        <c:if test="${empty branchList}">
            <div class="alert alert-info text-center" role="alert">
                هیچ شعبه‌ای ثبت نشده است. لطفاً شعبه جدید اضافه کنید.
            </div>
        </c:if>

        <c:if test="${not empty branchList}">
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle text-center">
                    <thead>
                    <tr>
                        <th>ردیف</th>
                        <th>شناسه</th>
                        <th>نام شعبه</th>
                        <th>شهر</th>
                        <th>مدیر</th>
                        <th>سازمان</th>
                        <th>وضعیت</th>
                        <th>عملیات</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="b" items="${branchList}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${b.id}</td>
                            <td><strong>${b.name}</strong></td>
                            <td>${b.city}</td>
                            <td>${b.manager}</td>
                            <td>${b.organization.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${b.deleted}">
                                        <span class="badge-status badge-inactive">غیرفعال</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-status badge-active">فعال</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/branch.do"
                                      method="post"
                                      onsubmit="return confirm('آیا از حذف شعبه «${b.name}» مطمئن هستید؟');">
                                    <input type="hidden" name="_method" value="delete"/>
                                    <input type="hidden" name="id" value="${b.id}"/>
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
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>