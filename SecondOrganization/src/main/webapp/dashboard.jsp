<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>داشبورد سازمانی</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: "Vazirmatn", Tahoma, Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .dashboard-container {
            max-width: 1400px;
            margin: 0 auto;
        }

        .header {
            background: white;
            border-radius: 20px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            text-align: center;
            animation: slideDown 0.6s ease-out;
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-50px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .header h1 {
            color: #5a67d8;
            font-weight: bold;
            margin-bottom: 10px;
            font-size: 2.5rem;
        }

        .header p {
            color: #718096;
            font-size: 1.1rem;
        }

        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            border-radius: 15px;
            padding: 25px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            transition: all 0.3s;
            animation: fadeInUp 0.6s ease-out;
            animation-fill-mode: both;
        }

        .stat-card:nth-child(1) { animation-delay: 0.1s; }
        .stat-card:nth-child(2) { animation-delay: 0.2s; }
        .stat-card:nth-child(3) { animation-delay: 0.3s; }
        .stat-card:nth-child(4) { animation-delay: 0.4s; }

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

        .stat-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 40px rgba(102, 126, 234, 0.3);
        }

        .stat-card .icon {
            width: 60px;
            height: 60px;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            margin-bottom: 15px;
        }

        .stat-card.purple .icon {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .stat-card.pink .icon {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }

        .stat-card.blue .icon {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }

        .stat-card.green .icon {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            color: white;
        }

        .stat-card h3 {
            color: #2d3748;
            font-size: 0.9rem;
            margin-bottom: 10px;
            font-weight: 600;
        }

        .stat-card .number {
            font-size: 2rem;
            font-weight: bold;
            color: #1a202c;
        }

        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 25px;
        }

        .menu-card {
            background: white;
            border-radius: 20px;
            padding: 35px;
            text-align: center;
            text-decoration: none;
            transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            position: relative;
            overflow: hidden;
            animation: zoomIn 0.6s ease-out;
            animation-fill-mode: both;
        }

        .menu-card:nth-child(1) { animation-delay: 0.5s; }
        .menu-card:nth-child(2) { animation-delay: 0.6s; }
        .menu-card:nth-child(3) { animation-delay: 0.7s; }
        .menu-card:nth-child(4) { animation-delay: 0.8s; }
        .menu-card:nth-child(5) { animation-delay: 0.9s; }
        .menu-card:nth-child(6) { animation-delay: 1.0s; }

        @keyframes zoomIn {
            from {
                opacity: 0;
                transform: scale(0.8);
            }
            to {
                opacity: 1;
                transform: scale(1);
            }
        }

        .menu-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
            opacity: 0;
            transition: opacity 0.3s;
        }

        .menu-card:hover::before {
            opacity: 1;
        }

        .menu-card:hover {
            transform: translateY(-15px) scale(1.05);
            box-shadow: 0 20px 50px rgba(102, 126, 234, 0.4);
        }

        .menu-card .card-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            border-radius: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 40px;
            position: relative;
            z-index: 1;
        }

        .menu-card.card-org .card-icon {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .menu-card.card-dept .card-icon {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }

        .menu-card.card-branch .card-icon {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }

        .menu-card.card-group .card-icon {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            color: white;
        }

        .menu-card.card-person .card-icon {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            color: white;
        }

        .menu-card.card-project .card-icon {
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
            color: white;
        }

        .menu-card h3 {
            font-size: 1.4rem;
            font-weight: bold;
            margin-bottom: 10px;
            color: #2d3748;
            position: relative;
            z-index: 1;
        }

        .menu-card p {
            color: #718096;
            font-size: 0.95rem;
            position: relative;
            z-index: 1;
        }

        .footer {
            text-align: center;
            margin-top: 40px;
            color: white;
            font-size: 0.9rem;
            animation: fadeIn 1.5s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @media (max-width: 768px) {
            .header h1 {
                font-size: 1.8rem;
            }

            .stats-container {
                grid-template-columns: 1fr;
            }

            .menu-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <!-- Header -->
    <div class="header">
        <h1>
            <i class="bi bi-speedometer2"></i>
            داشبورد سامانه مدیریت سازمانی
        </h1>
        <p>مدیریت جامع سازمان‌ها، دپارتمان‌ها، شعب و پرسنل</p>
    </div>

    <!-- Stats Cards -->
    <div class="stats-container">
        <div class="stat-card purple">
            <div class="icon">
                <i class="bi bi-building"></i>
            </div>
            <h3>تعداد سازمان‌ها</h3>
            <div class="number">12</div>
        </div>

        <div class="stat-card pink">
            <div class="icon">
                <i class="bi bi-diagram-3"></i>
            </div>
            <h3>تعداد دپارتمان‌ها</h3>
            <div class="number">45</div>
        </div>

        <div class="stat-card blue">
            <div class="icon">
                <i class="bi bi-geo-alt"></i>
            </div>
            <h3>تعداد شعب</h3>
            <div class="number">28</div>
        </div>

        <div class="stat-card green">
            <div class="icon">
                <i class="bi bi-people"></i>
            </div>
            <h3>تعداد پرسنل</h3>
            <div class="number">156</div>
        </div>
    </div>

    <!-- Menu Cards -->
    <div class="menu-grid">
        <a href="${pageContext.request.contextPath}/organization.do" class="menu-card card-org">
            <div class="card-icon">
                <i class="bi bi-building"></i>
            </div>
            <h3>مدیریت سازمان‌ها</h3>
            <p>ثبت، ویرایش و مدیریت اطلاعات سازمان‌ها</p>
        </a>

        <a href="${pageContext.request.contextPath}/department.do" class="menu-card card-dept">
            <div class="card-icon">
                <i class="bi bi-diagram-3"></i>
            </div>
            <h3>مدیریت دپارتمان‌ها</h3>
            <p>سازماندهی و مدیریت دپارتمان‌های سازمان</p>
        </a>

        <a href="${pageContext.request.contextPath}/branch.do" class="menu-card card-branch">
            <div class="card-icon">
                <i class="bi bi-geo-alt"></i>
            </div>
            <h3>مدیریت شعب</h3>
            <p>ثبت و پیگیری شعب مختلف سازمان</p>
        </a>

        <a href="${pageContext.request.contextPath}/organizationGroup.do" class="menu-card card-group">
            <div class="card-icon">
                <i class="bi bi-collection"></i>
            </div>
            <h3>گروه‌های سازمانی</h3>
            <p>مدیریت گروه‌ها و تیم‌های کاری</p>
        </a>

        <a href="${pageContext.request.contextPath}/person.do" class="menu-card card-person">
            <div class="card-icon">
                <i class="bi bi-person-badge"></i>
            </div>
            <h3>مدیریت پرسنل</h3>
            <p>ثبت و مدیریت اطلاعات کارکنان</p>
        </a>

        <a href="${pageContext.request.contextPath}/project.do" class="menu-card card-project">
            <div class="card-icon">
                <i class="bi bi-kanban"></i>
            </div>
            <h3>مدیریت پروژه‌ها</h3>
            <p>ایجاد و پیگیری پروژه‌های سازمانی</p>
        </a>
    </div>

    <!-- Footer -->
    <div class="footer">
        <p>سامانه مدیریت سازمانی | نسخه 1.0.0 | طراحی شده با Jakarta EE</p>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>