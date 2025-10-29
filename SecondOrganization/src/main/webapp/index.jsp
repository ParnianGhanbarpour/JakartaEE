<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>سامانه مدیریت سازمانی</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: "Vazirmatn", Tahoma, Arial, sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .main-container {
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            padding: 50px;
            max-width: 800px;
            animation: fadeInScale 0.6s ease-out;
        }
        @keyframes fadeInScale {
            from {
                opacity: 0;
                transform: scale(0.9);
            }
            to {
                opacity: 1;
                transform: scale(1);
            }
        }
        h1 {
            color: #5a67d8;
            font-weight: bold;
            margin-bottom: 15px;
            text-align: center;
        }
        .subtitle {
            text-align: center;
            color: #718096;
            margin-bottom: 40px;
            font-size: 18px;
        }
        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .menu-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 15px;
            padding: 30px;
            text-align: center;
            color: white;
            text-decoration: none;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        .menu-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.6);
            color: white;
        }
        .menu-card i {
            font-size: 48px;
            margin-bottom: 15px;
        }
        .menu-card h3 {
            font-size: 20px;
            font-weight: bold;
            margin: 0;
        }
        .menu-card p {
            font-size: 14px;
            margin-top: 10px;
            opacity: 0.9;
        }
        .card-org {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .card-dept {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
        .card-branch {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }
    </style>
</head>
<body>
<div class="main-container">
    <h1>
        <i class="bi bi-building"></i>
        سامانه مدیریت سازمانی
    </h1>
    <p class="subtitle">به سامانه جامع مدیریت سازمان‌ها خوش آمدید</p>

    <div class="menu-grid">
        <a href="${pageContext.request.contextPath}/organization.do" class="menu-card card-org">
            <i class="bi bi-building"></i>
            <h3>مدیریت سازمان‌ها</h3>
            <p>ثبت و مدیریت اطلاعات سازمان‌ها</p>
        </a>

        <a href="${pageContext.request.contextPath}/department.do" class="menu-card card-dept">
            <i class="bi bi-diagram-3"></i>
            <h3>مدیریت دپارتمان‌ها</h3>
            <p>سازماندهی و مدیریت دپارتمان‌ها</p>
        </a>

        <a href="${pageContext.request.contextPath}/branch.do" class="menu-card card-branch">
            <i class="bi bi-geo-alt"></i>
            <h3>مدیریت شعب</h3>
            <p>ثبت و پیگیری شعب سازمان</p>
        </a>
    </div>

    <div class="text-center mt-4">
        <small class="text-muted">نسخه 1.0 - طراحی شده با Jakarta EE</small>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>