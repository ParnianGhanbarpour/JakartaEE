<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ورود به سیستم</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', 'Segoe UI', Tahoma, sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            position: relative;
            overflow-x: hidden;
            overflow-y: auto;
            padding: 20px 0;
        }

        /* Animated Background */
        body::before {
            content: '';
            position: absolute;
            width: 500px;
            height: 500px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
            top: -200px;
            left: -200px;
            animation: float 6s ease-in-out infinite;
        }

        body::after {
            content: '';
            position: absolute;
            width: 400px;
            height: 400px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 50%;
            bottom: -150px;
            right: -150px;
            animation: float 8s ease-in-out infinite;
        }

        @keyframes float {
            0%, 100% { transform: translate(0, 0); }
            50% { transform: translate(30px, 30px); }
        }

        .login-container {
            position: relative;
            z-index: 10;
            width: 100%;
            max-width: 450px;
            padding: 0 20px;
            margin: 20px auto;
        }

        .login-card {
            background: white;
            border-radius: 24px;
            padding: 48px 40px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            animation: slideUp 0.6s ease-out;
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login-header {
            text-align: center;
            margin-bottom: 40px;
        }

        .login-logo {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
        }

        .login-logo i {
            font-size: 40px;
            color: white;
        }

        .login-header h1 {
            font-size: 28px;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 8px;
        }

        .login-header p {
            font-size: 14px;
            color: #64748b;
        }

        .form-group {
            margin-bottom: 24px;
        }

        .form-label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            color: #334155;
            margin-bottom: 8px;
        }

        .form-input-wrapper {
            position: relative;
        }

        .form-input {
            width: 100%;
            padding: 14px 48px 14px 16px;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            font-size: 15px;
            transition: all 0.3s;
            outline: none;
        }

        .form-input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
        }

        .form-input-icon {
            position: absolute;
            left: 16px;
            top: 50%;
            transform: translateY(-50%);
            color: #94a3b8;
            font-size: 18px;
            pointer-events: none;
        }

        .form-input:focus + .form-input-icon {
            color: #667eea;
        }

        .password-toggle {
            position: absolute;
            left: 16px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #94a3b8;
            cursor: pointer;
            font-size: 18px;
            padding: 4px;
            z-index: 10;
        }

        .password-toggle:hover {
            color: #667eea;
        }

        .form-options {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
            flex-wrap: wrap;
            gap: 10px;
        }

        .form-checkbox {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .form-checkbox input[type="checkbox"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
            accent-color: #667eea;
        }

        .form-checkbox label {
            font-size: 14px;
            color: #64748b;
            cursor: pointer;
            margin: 0;
        }

        .forgot-password {
            font-size: 14px;
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.2s;
            white-space: nowrap;
        }

        .forgot-password:hover {
            color: #764ba2;
        }

        .btn-login {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.6);
        }

        .btn-login:active {
            transform: translateY(0);
        }

        .alert {
            padding: 14px 16px;
            border-radius: 12px;
            margin-bottom: 24px;
            display: flex;
            align-items: center;
            gap: 12px;
            animation: shake 0.5s;
            word-break: break-word;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-10px); }
            75% { transform: translateX(10px); }
        }

        .alert-danger {
            background: #fee2e2;
            color: #dc2626;
            border: 2px solid #fecaca;
        }

        .alert-success {
            background: #dcfce7;
            color: #16a34a;
            border: 2px solid #bbf7d0;
        }

        .login-footer {
            text-align: center;
            margin-top: 32px;
            padding-top: 24px;
            border-top: 1px solid #e2e8f0;
        }

        .login-footer p {
            font-size: 14px;
            color: #64748b;
            margin: 0 0 12px 0;
        }

        .login-footer a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }

        .login-footer a:hover {
            color: #764ba2;
        }

        .demo-accounts {
            margin-top: 24px;
            padding: 16px;
            background: #f8fafc;
            border-radius: 12px;
            border: 2px dashed #e2e8f0;
        }

        .demo-accounts h6 {
            font-size: 12px;
            font-weight: 700;
            color: #334155;
            margin-bottom: 12px;
            text-transform: uppercase;
        }

        .demo-account {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 8px 12px;
            background: white;
            border-radius: 8px;
            margin-bottom: 8px;
            font-size: 13px;
            cursor: pointer;
            transition: all 0.2s;
            gap: 10px;
        }

        .demo-account:hover {
            background: #667eea;
            color: white;
            transform: translateX(-5px);
        }

        .demo-account:last-child {
            margin-bottom: 0;
        }

        .demo-account strong {
            color: inherit;
        }

        .demo-account .demo-pass {
            font-family: 'Courier New', monospace;
            font-size: 11px;
            white-space: nowrap;
        }

        .loader {
            display: none;
            width: 20px;
            height: 20px;
            border: 3px solid rgba(255,255,255,0.3);
            border-top-color: white;
            border-radius: 50%;
            animation: spin 0.8s linear infinite;
            margin: 0 auto;
        }

        @keyframes spin {
            to { transform: rotate(360deg); }
        }

        .btn-login.loading .btn-text {
            display: none;
        }

        .btn-login.loading .loader {
            display: block;
        }

        /* ========== RESPONSIVE ========== */
        @media (max-width: 768px) {
            body::before,
            body::after {
                display: none;
            }

            .login-container {
                padding: 0 15px;
            }

            .login-card {
                padding: 32px 24px;
                border-radius: 20px;
            }

            .login-header {
                margin-bottom: 32px;
            }

            .login-logo {
                width: 70px;
                height: 70px;
                margin-bottom: 16px;
            }

            .login-logo i {
                font-size: 36px;
            }

            .login-header h1 {
                font-size: 24px;
            }

            .login-header p {
                font-size: 13px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-input {
                padding: 12px 42px 12px 14px;
                font-size: 14px;
            }

            .form-options {
                flex-direction: column;
                align-items: flex-start;
                gap: 12px;
            }

            .btn-login {
                padding: 12px;
                font-size: 15px;
            }

            .demo-accounts {
                padding: 14px;
            }

            .demo-account {
                flex-direction: column;
                align-items: flex-start;
                padding: 10px;
                gap: 6px;
            }

            .demo-account .demo-pass {
                font-size: 12px;
            }

            .login-footer {
                margin-top: 24px;
                padding-top: 20px;
            }

            .login-footer p {
                font-size: 13px;
            }
        }

        @media (max-width: 480px) {
            .login-card {
                padding: 24px 20px;
            }

            .login-logo {
                width: 60px;
                height: 60px;
            }

            .login-logo i {
                font-size: 32px;
            }

            .login-header h1 {
                font-size: 22px;
            }

            .demo-accounts h6 {
                font-size: 11px;
            }
        }

        @media (max-height: 700px) {
            .login-card {
                padding: 28px 32px;
            }

            .login-header {
                margin-bottom: 28px;
            }

            .form-group {
                margin-bottom: 18px;
            }

            .demo-accounts {
                margin-top: 20px;
            }
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-card">
        <!-- Header -->
        <div class="login-header">
            <div class="login-logo">
                <i class="bi bi-shield-lock"></i>
            </div>
            <h1>خوش آمدید</h1>
            <p>برای ادامه، لطفاً وارد شوید</p>
        </div>

        <!-- Error Message -->
        <c:if test="${not empty loginError}">
            <div class="alert alert-danger">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <span>${loginError}</span>
            </div>
        </c:if>

        <!-- Success Message -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                <i class="bi bi-check-circle-fill"></i>
                <span>${successMessage}</span>
            </div>
        </c:if>

        <!-- Login Form -->
        <form action="${pageContext.request.contextPath}/login.do" method="post" id="loginForm">
            <div class="form-group">
                <label for="username" class="form-label">
                    <i class="bi bi-person"></i> نام کاربری
                </label>
                <div class="form-input-wrapper">
                    <input type="text"
                           id="username"
                           name="username"
                           class="form-input"
                           placeholder="نام کاربری خود را وارد کنید"
                           required
                           autocomplete="username">
                    <i class="bi bi-person form-input-icon"></i>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="form-label">
                    <i class="bi bi-lock"></i> رمز عبور
                </label>
                <div class="form-input-wrapper">
                    <input type="password"
                           id="password"
                           name="password"
                           class="form-input"
                           placeholder="رمز عبور خود را وارد کنید"
                           required
                           autocomplete="current-password">
                    <button type="button" class="password-toggle" onclick="togglePassword()">
                        <i class="bi bi-eye" id="toggleIcon"></i>
                    </button>
                </div>
            </div>

            <div class="form-options">
                <div class="form-checkbox">
                    <input type="checkbox" id="remember" name="remember">
                    <label for="remember">مرا به خاطر بسپار</label>
                </div>
                <a href="#" class="forgot-password">فراموشی رمز عبور؟</a>
            </div>

            <button type="submit" class="btn-login" id="loginBtn">
                <span class="btn-text">ورود به سیستم</span>
                <div class="loader"></div>
            </button>
        </form>

        <!-- Demo Accounts -->
        <div class="demo-accounts">
            <h6>
                <i class="bi bi-info-circle"></i> حساب‌های آزمایشی
            </h6>
            <div class="demo-account" onclick="fillLogin('admin', 'admin123')">
                <div>
                    <i class="bi bi-person-fill-gear"></i>
                    <strong>مدیر:</strong> admin
                </div>
                <span class="demo-pass">admin123</span>
            </div>
            <div class="demo-account" onclick="fillLogin('manager', 'manager123')">
                <div>
                    <i class="bi bi-person-badge"></i>
                    <strong>مدیر واحد:</strong> manager
                </div>
                <span class="demo-pass">manager123</span>
            </div>
            <div class="demo-account" onclick="fillLogin('user', 'user123')">
                <div>
                    <i class="bi bi-person-fill"></i>
                    <strong>کاربر:</strong> user
                </div>
                <span class="demo-pass">user123</span>
            </div>
        </div>

        <!-- Footer -->
        <div class="login-footer">
            <p>
                حساب کاربری ندارید؟
                <a href="${pageContext.request.contextPath}/signup.do">
                    <i class="bi bi-person-plus"></i> ثبت‌نام کنید
                </a>
            </p>
            <p>
                <i class="bi bi-shield-check"></i>
                سامانه مدیریت سازمانی | نسخه 1.0.0
            </p>
        </div>
    </div>
</div>

<script>
    // Toggle password visibility
    function togglePassword() {
        const passwordInput = document.getElementById('password');
        const toggleIcon = document.getElementById('toggleIcon');

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleIcon.classList.remove('bi-eye');
            toggleIcon.classList.add('bi-eye-slash');
        } else {
            passwordInput.type = 'password';
            toggleIcon.classList.remove('bi-eye-slash');
            toggleIcon.classList.add('bi-eye');
        }
    }

    // Fill login credentials
    function fillLogin(username, password) {
        document.getElementById('username').value = username;
        document.getElementById('password').value = password;

        // Add highlight effect
        const inputs = document.querySelectorAll('.form-input');
        inputs.forEach(input => {
            input.style.borderColor = '#667eea';
            setTimeout(() => {
                input.style.borderColor = '';
            }, 1000);
        });
    }

    // Form submission with loader
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        const loginBtn = document.getElementById('loginBtn');
        loginBtn.classList.add('loading');
        loginBtn.disabled = true;
    });

    // Auto-hide alerts after 5 seconds
    setTimeout(() => {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-20px)';
            setTimeout(() => alert.remove(), 300);
        });
    }, 5000);

    // Keyboard shortcuts for demo login
    document.addEventListener('keydown', function(e) {
        // Ctrl + Shift + A = Admin
        if (e.ctrlKey && e.shiftKey && e.key === 'A') {
            e.preventDefault();
            fillLogin('admin', 'admin123');
        }
        // Ctrl + Shift + M = Manager
        if (e.ctrlKey && e.shiftKey && e.key === 'M') {
            e.preventDefault();
            fillLogin('manager', 'manager123');
        }
        // Ctrl + Shift + U = User
        if (e.ctrlKey && e.shiftKey && e.key === 'U') {
            e.preventDefault();
            fillLogin('user', 'user123');
        }
    });
</script>
</body>
</html>