<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Protection: Redirect to login if not authenticated -->
<c:if test="${empty sessionScope.username}">
    <c:redirect url="/login.do"/>
</c:if>

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

        /* ========== SIDEBAR ========== */
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
            overflow-y: auto;
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

        .sidebar-divider {
            margin: 20px 0;
            border-top: 1px solid rgba(255,255,255,0.1);
        }

        /* ========== MAIN CONTENT ========== */
        .main-content {
            margin-right: 280px;
            padding: 30px;
            min-height: 100vh;
        }

        /* ========== TOP BAR ========== */
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
            margin: 0 0 5px 0;
        }

        .top-bar-title p {
            color: #64748b;
            margin: 0;
            font-size: 14px;
        }

        .top-bar-user {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-avatar {
            width: 50px;
            height: 50px;
            border-radius: 12px;
            object-fit: cover;
            border: 3px solid #f1f5f9;
        }

        .user-info h4 {
            font-size: 15px;
            font-weight: 600;
            color: #1e293b;
            margin: 0 0 4px 0;
        }

        .role-badge {
            padding: 4px 12px;
            border-radius: 8px;
            font-size: 11px;
            font-weight: 700;
            text-transform: uppercase;
            display: inline-block;
        }

        .role-admin {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .role-manager {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }

        .role-user {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            color: white;
        }

        /* ========== STATS CARDS ========== */
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

        /* ========== QUICK ACTIONS ========== */
        .quick-actions {
            background: white;
            border-radius: 16px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .quick-actions h3 {
            font-size: 20px;
            font-weight: 700;
            color: #1e293b;
            margin: 0 0 24px 0;
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

        .quick-action-text h4 {
            font-size: 15px;
            font-weight: 600;
            color: #1e293b;
            margin: 0;
            text-align: center;
        }

        .quick-action-text p {
            font-size: 12px;
            color: #64748b;
            margin: 4px 0 0 0;
            text-align: center;
        }

        /* ========== RESPONSIVE ========== */
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
        }
    </style>
</head>
<body>
<!-- ========== SIDEBAR ========== -->
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
        <!-- Dashboard - همه کاربران -->
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="sidebar-nav-link active">
                <i class="bi bi-speedometer2"></i>
                <span>داشبورد</span>
            </a>
        </li>

        <!-- Admin & Manager Only -->
        <c:if test="${sessionScope.isAdmin || sessionScope.isManager}">
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
        </c:if>

        <!-- Admin Only -->
        <c:if test="${sessionScope.isAdmin}">
            <li class="sidebar-nav-item">
                <a href="${pageContext.request.contextPath}/branch.do" class="sidebar-nav-link">
                    <i class="bi bi-geo-alt"></i>
                    <span>شعب</span>
                </a>
            </li>
        </c:if>

        <div class="sidebar-divider"></div>

        <!-- All Users -->
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

        <div class="sidebar-divider"></div>

        <!-- Logout -->
        <li class="sidebar-nav-item">
            <a href="${pageContext.request.contextPath}/logout.do" class="sidebar-nav-link" style="color: #ef4444;">
                <i class="bi bi-box-arrow-left"></i>
                <span>خروج از سیستم</span>
            </a>
        </li>
    </ul>
</aside>

<!-- ========== MAIN CONTENT ========== -->
<main class="main-content">
    <!-- Top Bar -->
    <div class="top-bar">
        <div class="top-bar-title">
            <h1>
                <c:choose>
                    <c:when test="${sessionScope.isAdmin}">
                        <i class="bi bi-shield-check"></i> داشبورد مدیریت
                    </c:when>
                    <c:when test="${sessionScope.isManager}">
                        <i class="bi bi-person-gear"></i> داشبورد مدیر
                    </c:when>
                    <c:otherwise>
                        <i class="bi bi-person"></i> داشبورد کاربر
                    </c:otherwise>
                </c:choose>
            </h1>
            <p>
                خوش آمدید
                <strong>
                    <c:choose>
                        <c:when test="${not empty sessionScope.personName}">
                            ${sessionScope.personName}
                        </c:when>
                        <c:otherwise>
                            ${sessionScope.username}
                        </c:otherwise>
                    </c:choose>
                </strong>
            </p>
        </div>

        <div class="top-bar-user">
            <img src="https://ui-avatars.com/api/?name=${sessionScope.username}&background=667eea&color=fff&bold=true"
                 alt="Avatar" class="user-avatar">
            <div class="user-info">
                <h4>
                    <c:choose>
                        <c:when test="${not empty sessionScope.personName}">
                            ${sessionScope.personName}
                        </c:when>
                        <c:otherwise>
                            ${sessionScope.username}
                        </c:otherwise>
                    </c:choose>
                </h4>
                <span class="role-badge role-${sessionScope.userRole}">
                        <c:choose>
                            <c:when test="${sessionScope.isAdmin}">
                                <i class="bi bi-shield-check"></i> مدیر سیستم
                            </c:when>
                            <c:when test="${sessionScope.isManager}">
                                <i class="bi bi-person-gear"></i> مدیر
                            </c:when>
                            <c:otherwise>
                                <i class="bi bi-person"></i> کاربر
                            </c:otherwise>
                        </c:choose>
                    </span>
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
            </div>
            <div class="stat-card-body">
                <h3>پروژه‌های فعال</h3>
                <h2>89</h2>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions">
        <h3><i class="bi bi-lightning-charge"></i> دسترسی سریع</h3>
        <div class="quick-actions-grid">
            <!-- Admin & Manager Only -->
            <c:if test="${sessionScope.isAdmin || sessionScope.isManager}">
                <a href="${pageContext.request.contextPath}/organization.do" class="quick-action-btn">
                    <div class="quick-action-icon" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                        <i class="bi bi-plus-circle"></i>
                    </div>
                    <div class="quick-action-text">
                        <h4>سازمان جدید</h4>
                        <p>افزودن سازمان</p>
                    </div>
                </a>

                <a href="${pageContext.request.contextPath}/department.do" class="quick-action-btn">
                    <div class="quick-action-icon" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
                        <i class="bi bi-folder-plus"></i>
                    </div>
                    <div class="quick-action-text">
                        <h4>دپارتمان جدید</h4>
                        <p>ایجاد دپارتمان</p>
                    </div>
                </a>
            </c:if>

            <!-- All Users -->
            <a href="${pageContext.request.contextPath}/person.do" class="quick-action-btn">
                <div class="quick-action-icon" style="background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);">
                    <i class="bi bi-person-plus"></i>
                </div>
                <div class="quick-action-text">
                    <h4>پرسنل جدید</h4>
                    <p>افزودن کارمند</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/project.do" class="quick-action-btn">
                <div class="quick-action-icon" style="background: linear-gradient(135deg, #30cfd0 0%, #330867 100%);">
                    <i class="bi bi-clipboard-plus"></i>
                </div>
                <div class="quick-action-text">
                    <h4>پروژه جدید</h4>
                    <p>ثبت پروژه</p>
                </div>
            </a>

            <a href="${pageContext.request.contextPath}/organizationGroup.do" class="quick-action-btn">
                <div class="quick-action-icon" style="background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);">
                    <i class="bi bi-people"></i>
                </div>
                <div class="quick-action-text">
                    <h4>گروه جدید</h4>
                    <p>ایجاد گروه کاری</p>
                </div>
            </a>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Animate stats on load
    window.addEventListener('load', () => {
        const stats = document.querySelectorAll('.stat-card-body h2');
        stats.forEach((stat) => {
            const endValue = parseInt(stat.textContent.replace(/,/g, ''));
            let startValue = 0;
            const duration = 1500;
            const increment = endValue / (duration / 16);

            const counter = setInterval(() => {
                startValue += increment;
                if (startValue >= endValue) {
                    stat.textContent = endValue.toLocaleString('fa-IR');
                    clearInterval(counter);
                } else {
                    stat.textContent = Math.floor(startValue).toLocaleString('fa-IR');
                }
            }, 16);
        });
    });
</script>
</body>
</html>