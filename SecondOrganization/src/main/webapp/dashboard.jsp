<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>داشبورد مدیریت سازمان</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', 'Segoe UI', Tahoma, sans-serif;
            background: #f8f9fa;
            overflow-x: hidden;
        }

        /* Sidebar */
        .sidebar {
            position: fixed;
            right: 0;
            top: 0;
            height: 100vh;
            width: 280px;
            background: linear-gradient(180deg, #1e293b 0%, #0f172a 100%);
            padding: 20px;
            transition: all 0.3s ease;
            z-index: 1000;
            box-shadow: -5px 0 30px rgba(0,0,0,0.1);
        }

        .sidebar-header {
            padding: 20px 0;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            margin-bottom: 30px;
        }

        .sidebar-logo {
            display: flex;
            align-items: center;
            gap: 12px;
            color: white;
            text-decoration: none;
        }

        .sidebar-logo i {
            font-size: 32px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            width: 48px;
            height: 48px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 12px;
        }

        .sidebar-logo-text h3 {
            font-size: 18px;
            font-weight: 700;
            margin: 0;
        }

        .sidebar-logo-text p {
            font-size: 12px;
            color: #94a3b8;
            margin: 0;
        }

        .sidebar-nav {
            list-style: none;
            padding: 0;
        }

        .sidebar-nav-item {
            margin-bottom: 8px;
        }

        .sidebar-nav-link {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 16px;
            color: #cbd5e1;
            text-decoration: none;
            border-radius: 10px;
            transition: all 0.2s;
            font-weight: 500;
        }

        .sidebar-nav-link:hover {
            background: rgba(255,255,255,0.1);
            color: white;
            transform: translateX(-5px);
        }

        .sidebar-nav-link.active {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }

        .sidebar-nav-link i {
            font-size: 20px;
            width: 24px;
        }

        /* Main Content */
        .main-content {
            margin-right: 280px;
            padding: 30px;
            min-height: 100vh;
        }

        /* Top Bar */
        .top-bar {
            background: white;
            border-radius: 16px;
            padding: 20px 30px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .top-bar-title h1 {
            font-size: 28px;
            font-weight: 700;
            color: #1e293b;
            margin: 0;
        }

        .top-bar-title p {
            color: #64748b;
            margin: 5px 0 0 0;
            font-size: 14px;
        }

        .top-bar-actions {
            display: flex;
            gap: 15px;
            align-items: center;
        }

        .top-bar-search {
            position: relative;
        }

        .top-bar-search input {
            width: 300px;
            padding: 10px 40px 10px 20px;
            border: 2px solid #e2e8f0;
            border-radius: 10px;
            outline: none;
            transition: all 0.3s;
        }

        .top-bar-search input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
        }

        .top-bar-search i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #94a3b8;
        }

        .top-bar-profile {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 8px 12px;
            background: #f8fafc;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.2s;
        }

        .top-bar-profile:hover {
            background: #f1f5f9;
        }

        .top-bar-profile img {
            width: 40px;
            height: 40px;
            border-radius: 10px;
            object-fit: cover;
        }

        .top-bar-profile-info h4 {
            font-size: 14px;
            font-weight: 600;
            margin: 0;
            color: #1e293b;
        }

        .top-bar-profile-info p {
            font-size: 12px;
            color: #64748b;
            margin: 0;
        }

        /* Stats Cards */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            border-radius: 16px;
            padding: 24px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            transition: all 0.3s;
            position: relative;
            overflow: hidden;
        }

        .stat-card::before {
            content: '';
            position: absolute;
            top: 0;
            right: 0;
            width: 100%;
            height: 4px;
            background: linear-gradient(90deg, var(--card-gradient));
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        .stat-card.purple::before {
            --card-gradient: #667eea, #764ba2;
        }

        .stat-card.blue::before {
            --card-gradient: #4facfe, #00f2fe;
        }

        .stat-card.green::before {
            --card-gradient: #43e97b, #38f9d7;
        }

        .stat-card.orange::before {
            --card-gradient: #fa709a, #fee140;
        }

        .stat-card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 20px;
        }

        .stat-card-icon {
            width: 56px;
            height: 56px;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            color: white;
        }

        .stat-card.purple .stat-card-icon {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .stat-card.blue .stat-card-icon {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        .stat-card.green .stat-card-icon {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
        }

        .stat-card.orange .stat-card-icon {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
        }

        .stat-card-change {
            display: flex;
            align-items: center;
            gap: 4px;
            padding: 6px 12px;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
        }

        .stat-card-change.up {
            background: #dcfce7;
            color: #16a34a;
        }

        .stat-card-change.down {
            background: #fee2e2;
            color: #dc2626;
        }

        .stat-card-body h3 {
            font-size: 14px;
            font-weight: 500;
            color: #64748b;
            margin-bottom: 8px;
        }

        .stat-card-body h2 {
            font-size: 32px;
            font-weight: 700;
            color: #1e293b;
            margin: 0;
        }

        /* Quick Actions */
        .quick-actions {
            background: white;
            border-radius: 16px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .quick-actions-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
        }

        .quick-actions-header h3 {
            font-size: 20px;
            font-weight: 700;
            color: #1e293b;
            margin: 0;
        }

        .quick-actions-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 16px;
        }

        .quick-action-btn {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 12px;
            padding: 24px;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            text-decoration: none;
            transition: all 0.3s;
            background: white;
        }

        .quick-action-btn:hover {
            border-color: #667eea;
            background: #f8fafc;
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.15);
        }

        .quick-action-icon {
            width: 60px;
            height: 60px;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
            color: white;
        }

        .quick-action-btn:nth-child(1) .quick-action-icon {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .quick-action-btn:nth-child(2) .quick-action-icon {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }

        .quick-action-btn:nth-child(3) .quick-action-icon {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        .quick-action-btn:nth-child(4) .quick-action-icon {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
        }

        .quick-action-btn:nth-child(5) .quick-action-icon {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
        }

        .quick-action-btn:nth-child(6) .quick-action-icon {
            background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);
        }

        .quick-action-text h4 {
            font-size: 15px;
            font-weight: 600;
            color: #1e293b;
            margin: 0;
        }

        .quick-action-text p {
            font-size: 12px;
            color: #64748b;
            margin: 4px 0 0 0;
        }

        /* Recent Activity */
        .recent-activity {
            background: white;
            border-radius: 16px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .recent-activity-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
        }

        .recent-activity-header h3 {
            font-size: 20px;
            font-weight: 700;
            color: #1e293b;
            margin: 0;
        }

        .activity-item {
            display: flex;
            gap: 16px;
            padding: 16px;
            border-radius: 12px;
            transition: all 0.2s;
            margin-bottom: 8px;
        }

        .activity-item:hover {
            background: #f8fafc;
        }

        .activity-icon {
            width: 48px;
            height: 48px;
            min-width: 48px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
            color: white;
        }

        .activity-icon.purple {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .activity-icon.green {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
        }

        .activity-icon.blue {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        .activity-icon.orange {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
        }

        .activity-content {
            flex: 1;
        }

        .activity-content h4 {
            font-size: 14px;
            font-weight: 600;
            color: #1e293b;
            margin: 0 0 4px 0;
        }

        .activity-content p {
            font-size: 13px;
            color: #64748b;
            margin: 0;
        }

        .activity-time {
            font-size: 12px;
            color: #94a3b8;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .sidebar {
                width: 70px;
                padding: 20px 10px;
            }

            .sidebar-logo-text,
            .sidebar-nav-link span {
                display: none;
            }

            .main-content {
                margin-right: 70px;
            }

            .top-bar {
                flex-direction: column;
                gap: 15px;
            }

            .top-bar-search input {
                width: 100%;
            }
        }
    </style>
</head>
<body>
<!-- Sidebar -->
<aside class="sidebar">
    <div class="sidebar-header">
        <a href="#" class="sidebar-logo">
            <i class="bi bi-building"></i>
            <div class="sidebar-logo-text">
                <h3>سازمان من</h3>
                <p>پنل مدیریت</p>
            </div>
        </a>
    </div>

    <ul class="sidebar-nav">
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="sidebar-nav-link active">
                <i class="bi bi-speedometer2"></i>
                <span>داشبورد</span>
            </a>
        </li>
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/organization.do" class="sidebar-nav-link">
                <i class="bi bi-building"></i>
                <span>سازمان‌ها</span>
            </a>
        </li>
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/department.do" class="sidebar-nav-link">
                <i class="bi bi-diagram-3"></i>
                <span>دپارتمان‌ها</span>
            </a>
        </li>
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/branch.do" class="sidebar-nav-link">
                <i class="bi bi-geo-alt"></i>
                <span>شعب</span>
            </a>
        </li>
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/organizationGroup.do" class="sidebar-nav-link">
                <i class="bi bi-collection"></i>
                <span>گروه‌های سازمانی</span>
            </a>
        </li>
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/person.do" class="sidebar-nav-link">
                <i class="bi bi-person-badge"></i>
                <span>پرسنل</span>
            </a>
        </li>
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/project.do" class="sidebar-nav-link">
                <i class="bi bi-kanban"></i>
                <span>پروژه‌ها</span>
            </a>
        </li>
    </ul>
</aside>

<!-- Main Content -->
<main class="main-content">
    <!-- Top Bar -->
    <div class="top-bar">
        <div class="top-bar-title">
            <h1>داشبورد</h1>
            <p>خوش آمدید! امروز جمعه، 30 آبان 1403</p>
        </div>
        <div class="top-bar-actions">
            <div class="top-bar-search">
                <input type="text" placeholder="جستجو...">
                <i class="bi bi-search"></i>
            </div>
            <div class="top-bar-profile">
                <img src="https://ui-avatars.com/api/?name=Admin&background=667eea&color=fff" alt="Profile">
                <div class="top-bar-profile-info">
                    <h4>مدیر سیستم</h4>
                    <p>ادمین</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Stats Cards -->
    <div class="stats-grid">
        <div class="stat-card purple">
            <div class="stat-card-header">
                <div class="stat-card-icon">
                    <i class="bi bi-building"></i>
                </div>
                <div class="stat-card-change up">
                    <i class="bi bi-arrow-up"></i>
                    12%
                </div>
            </div>
            <div class="stat-card-body">
                <h3>تعداد سازمان‌ها</h3>
                <h2>24</h2>
            </div>
        </div>

        <div class="stat-card blue">
            <div class="stat-card-header">
                <div class="stat-card-icon">
                    <i class="bi bi-diagram-3"></i>
                </div>
                <div class="stat-card-change up">
                    <i class="bi bi-arrow-up"></i>
                    8%
                </div>
            </div>
            <div class="stat-card-body">
                <h3>دپارتمان‌های فعال</h3>
                <h2>156</h2>
            </div>
        </div>

        <div class="stat-card green">
            <div class="stat-card-header">
                <div class="stat-card-icon">
                    <i class="bi bi-people"></i>
                </div>
                <div class="stat-card-change up">
                    <i class="bi bi-arrow-up"></i>
                    23%
                </div>
            </div>
            <div class="stat-card-body">
                <h3>تعداد پرسنل</h3>
                <h2>2,847</h2>
            </div>
        </div>

        <div class="stat-card orange">
            <div class="stat-card-header">
                <div class="stat-card-icon">
                    <i class="bi bi-kanban"></i>
                </div>
                <div class="stat-card-change down">
                    <i class="bi bi-arrow-down"></i>
                    5%
                </div>
            </div>
            <div class="stat-card-body">
                <h3>پروژه‌های فعال</h3>
                <h2>89</h2>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions">
        <div class="quick-actions-header">
            <h3>دسترسی سریع</h3>
        </div>
        <div class="quick-actions-grid">
            <a href="${pageContext.request.contextPath}/organization.do" class="quick-action-btn">
                <div class="quick-action-icon">
                    <i class="bi bi-plus-circle"></i>
                </div>
                <div class="quick-action-text">
                    <h4>سازمان جدید</h4>
                    <p>افزودن سازمان</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/department.do" class="quick-action-btn">
                <div class="quick-action-icon">
                    <i class="bi bi-folder-plus"></i>
                </div>
                <div class="quick-action-text">
                    <h4>دپارتمان جدید</h4>
                    <p>ایجاد دپارتمان</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/branch.do" class="quick-action-btn">
                <div class="quick-action-icon">
                    <i class="bi bi-pin-map"></i>
                </div>
                <div class="quick-action-text">
                    <h4>شعبه جدید</h4>
                    <p>ثبت شعبه</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/organizationGroup.do" class="quick-action-btn">
                <div class="quick-action-icon">
                    <i class="bi bi-people"></i>
                </div>
                <div class="quick-action-text">
                    <h4>گروه جدید</h4>
                    <p>ایجاد گروه کاری</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/person.do" class="quick-action-btn">
                <div class="quick-action-icon">
                    <i class="bi bi-person-plus"></i>
                </div>
                <div class="quick-action-text">
                    <h4>پرسنل جدید</h4>
                    <p>افزودن کارمند</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/project.do" class="quick-action-btn">
                <div class="quick-action-icon">
                    <i class="bi bi-clipboard-plus"></i>
                </div>
                <div class="quick-action-text">
                    <h4>پروژه جدید</h4>
                    <p>ثبت پروژه</p>
                </div>
            </a>
        </div>
    </div>

    <!-- Recent Activity -->
    <div class="recent-activity">
        <div class="recent-activity-header">
            <h3>فعالیت‌های اخیر</h3>
            <a href="#" style="color: #667eea; text-decoration: none; font-weight: 600; font-size: 14px;">
                مشاهده همه
                <i class="bi bi-arrow-left"></i>
            </a>
        </div>

        <div class="activity-item">
            <div class="activity-icon purple">
                <i class="bi bi-building"></i>
            </div>
            <div class="activity-content">
                <h4>سازمان جدید ثبت شد</h4>
                <p>شرکت فناوری پارس در سیستم ثبت شد</p>
            </div>
            <div class="activity-time">
                2 ساعت پیش
            </div>
        </div>

        <div class="activity-item">
            <div class="activity-icon green">
                <i class="bi bi-person-check"></i>
            </div>
            <div class="activity-content">
                <h4>پرسنل جدید اضافه شد</h4>
                <p>علی احمدی به دپارتمان IT اضافه شد</p>
            </div>
            <div class="activity-time">
                5 ساعت پیش
            </div>
        </div>

        <div class="activity-item">
            <div class="activity-icon blue">
                <i class="bi bi-kanban"></i>
            </div>
            <div class="activity-content">
                <h4>پروژه به‌روزرسانی شد</h4>
                <p>وضعیت پروژه طراحی سایت به "تکمیل شده" تغییر کرد</p>
            </div>
            <div class="activity-time">
                1 روز پیش
            </div>
        </div>

        <div class="activity-item">
            <div class="activity-icon orange">
                <i class="bi bi-geo-alt"></i>
            </div>
            <div class="activity-content">
                <h4>شعبه جدید افتتاح شد</h4>
                <p>شعبه تهران - ونک به سیستم اضافه شد</p>
            </div>
            <div class="activity-time">
                2 روز پیش
            </div>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Active link highlighting
    document.querySelectorAll('.sidebar-nav-link').forEach(link => {
        link.addEventListener('click', function() {
            document.querySelectorAll('.sidebar-nav-link').forEach(l => l.classList.remove('active'));
            this.classList.add('active');
        });
    });

    // Animate numbers on page load
    function animateValue(element, start, end, duration) {
        let startTimestamp = null;
        const step = (timestamp) => {
            if (!startTimestamp) startTimestamp = timestamp;
            const progress = Math.min((timestamp - startTimestamp) / duration, 1);
            element.textContent = Math.floor(progress * (end - start) + start).toLocaleString('fa-IR');
            if (progress < 1) {
                window.requestAnimationFrame(step);
            }
        };
        window.requestAnimationFrame(step);
    }

    window.addEventListener('load', () => {
        const stats = document.querySelectorAll('.stat-card-body h2');
        stats.forEach((stat, index) => {
            const endValue = parseInt(stat.textContent.replace(/,/g, ''));
            animateValue(stat, 0, endValue, 1500);
        });
    });
</script>
</body>
</html>