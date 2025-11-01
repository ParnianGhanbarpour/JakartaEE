<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ثبت‌نام در سیستم</title>
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
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
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
            right: -200px;
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
            left: -150px;
            animation: float 8s ease-in-out infinite;
        }

        @keyframes float {
            0%, 100% { transform: translate(0, 0); }
            50% { transform: translate(30px, 30px); }
        }

        .signup-container {
            position: relative;
            z-index: 10;
            width: 100%;
            max-width: 500px;
            padding: 20px;
        }

        .signup-card {
            background: white;
            border-radius: 24px;
            padding: 40px;
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

        .signup-header {
            text-align: center;
            margin-bottom: 32px;
        }

        .signup-logo {
            width: 70px;
            height: 70px;
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            border-radius: 18px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 16px;
            box-shadow: 0 10px 30px rgba(67, 233, 123, 0.3);
        }

        .signup-logo i {
            font-size: 36px;
            color: white;
        }

        .signup-header h1 {
            font-size: 26px;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 6px;
        }

        .signup-header p {
            font-size: 14px;
            color: #64748b;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: #334155;
            margin-bottom: 6px;
        }

        .form-label i {
            margin-left: 6px;
            color: #43e97b;
        }

        .form-input-wrapper {
            position: relative;
        }

        .form-input {
            width: 100%;
            padding: 12px 42px 12px 14px;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            font-size: 14px;
            transition: all 0.3s;
            outline: none;
        }

        .form-input:focus {
            border-color: #43e97b;
            box-shadow: 0 0 0 4px rgba(67, 233, 123, 0.1);
        }

        .form-input-icon {
            position: absolute;
            left: 14px;
            top: 50%;
            transform: translateY(-50%);
            color: #94a3b8;
            font-size: 16px;
            pointer-events: none;
        }

        .form-input:focus + .form-input-icon {
            color: #43e97b;
        }

        .password-toggle {
            position: absolute;
            left: 14px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #94a3b8;
            cursor: pointer;
            font-size: 16px;
            padding: 4px;
            z-index: 10;
        }

        .password-toggle:hover {
            color: #43e97b;
        }

        .password-strength {
            margin-top: 8px;
            height: 4px;
            background: #e2e8f0;
            border-radius: 4px;
            overflow: hidden;
            display: none;
        }

        .password-strength-bar {
            height: 100%;
            transition: all 0.3s;
            width: 0%;
        }

        .strength-weak {
            width: 33%;
            background: #ef4444;
        }

        .strength-medium {
            width: 66%;
            background: #f59e0b;
        }

        .strength-strong {
            width: 100%;
            background: #10b981;
        }

        .btn-signup {
            width: 100%;
            padding: 13px;
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(67, 233, 123, 0.4);
            margin-top: 8px;
        }

        .btn-signup:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(56, 249, 215, 0.6);
        }

        .btn-signup:active {
            transform: translateY(0);
        }

        .alert {
            padding: 12px 14px;
            border-radius: 12px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
            animation: shake 0.5s;
            font-size: 14px;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-8px); }
            75% { transform: translateX(8px); }
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

        .signup-footer {
            text-align: center;
            margin-top: 24px;
            padding-top: 20px;
            border-top: 1px solid #e2e8f0;
        }

        .signup-footer p {
            font-size: 14px;
            color: #64748b;
            margin: 0;
        }

        .signup-footer a {
            color: #43e97b;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.2s;
        }

        .signup-footer a:hover {
            color: #38f9d7;
        }

        .terms-checkbox {
            display: flex;
            align-items: flex-start;
            gap: 10px;
            margin: 20px 0;
        }

        .terms-checkbox input[type="checkbox"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
            accent-color: #43e97b;
            margin-top: 2px;
        }

        .terms-checkbox label {
            font-size: 13px;
            color: #64748b;
            cursor: pointer;
            margin: 0;
        }

        .terms-checkbox label a {
            color: #43e97b;
            text-decoration: none;
        }

        .loader {
            display: none;
            width: 18px;
            height: 18px;
            border: 3px solid rgba(255,255,255,0.3);
            border-top-color: white;
            border-radius: 50%;
            animation: spin 0.8s linear infinite;
            margin: 0 auto;
        }

        @keyframes spin {
            to { transform: rotate(360deg); }
        }

        .btn-signup.loading .btn-text {
            display: none;
        }

        .btn-signup.loading .loader {
            display: block;
        }

        .input-error {
            border-color: #ef4444 !important;
        }

        .error-message {
            color: #ef4444;
            font-size: 12px;
            margin-top: 4px;
            display: none;
        }

        @media (max-width: 576px) {
            .signup-card {
                padding: 28px 20px;
            }

            .signup-header h1 {
                font-size: 22px;
            }
        }
    </style>
</head>
<body>
<div class="signup-container">
    <div class="signup-card">
        <div class="signup-header">
            <div class="signup-logo">
                <i class="bi bi-person-plus-fill"></i>
            </div>
            <h1>ایجاد حساب کاربری</h1>
            <p>برای شروع، لطفاً اطلاعات خود را وارد کنید</p>
        </div>

        <c:if test="${not empty signupError}">
            <div class="alert alert-danger">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <span>${signupError}</span>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/signup.do" method="post" id="signupForm">
            <div class="form-group">
                <label for="username" class="form-label">
                    <i class="bi bi-person"></i> نام کاربری
                </label>
                <div class="form-input-wrapper">
                    <input type="text"
                           id="username"
                           name="username"
                           class="form-input"
                           placeholder="نام کاربری خود را انتخاب کنید"
                           required
                           minlength="5"
                           maxlength="20"
                           pattern="^[A-Za-z][A-Za-z0-9_]{4,19}$"
                           autocomplete="username">
                    <i class="bi bi-person form-input-icon"></i>
                </div>
                <small class="error-message" id="usernameError">
                    نام کاربری باید با حرف شروع شود و فقط شامل حروف، اعداد و _ باشد (۵-۲۰ کاراکتر)
                </small>
            </div>

            <div class="form-group">
                <label for="email" class="form-label">
                    <i class="bi bi-envelope"></i> ایمیل (اختیاری)
                </label>
                <div class="form-input-wrapper">
                    <input type="email"
                           id="email"
                           name="email"
                           class="form-input"
                           placeholder="example@email.com"
                           autocomplete="email">
                    <i class="bi bi-envelope form-input-icon"></i>
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
                           placeholder="رمز عبور قوی انتخاب کنید"
                           required
                           minlength="6"
                           autocomplete="new-password">
                    <button type="button" class="password-toggle" onclick="togglePassword('password', 'toggleIcon1')">
                        <i class="bi bi-eye" id="toggleIcon1"></i>
                    </button>
                </div>
                <div class="password-strength" id="passwordStrength">
                    <div class="password-strength-bar" id="strengthBar"></div>
                </div>
                <small class="error-message" id="passwordError">
                    رمز عبور باید حداقل ۶ کاراکتر و شامل حروف و اعداد باشد
                </small>
            </div>

            <div class="form-group">
                <label for="confirmPassword" class="form-label">
                    <i class="bi bi-lock-fill"></i> تکرار رمز عبور
                </label>
                <div class="form-input-wrapper">
                    <input type="password"
                           id="confirmPassword"
                           name="confirmPassword"
                           class="form-input"
                           placeholder="رمز عبور را دوباره وارد کنید"
                           required
                           autocomplete="new-password">
                    <button type="button" class="password-toggle" onclick="togglePassword('confirmPassword', 'toggleIcon2')">
                        <i class="bi bi-eye" id="toggleIcon2"></i>
                    </button>
                </div>
                <small class="error-message" id="confirmError">
                    رمز عبور و تکرار آن یکسان نیستند
                </small>
            </div>

            <div class="terms-checkbox">
                <input type="checkbox" id="terms" name="terms" required>
                <label for="terms">
                    با <a href="#">قوانین و مقررات</a> سایت موافقم
                </label>
            </div>

            <button type="submit" class="btn-signup" id="signupBtn">
                <span class="btn-text">ثبت‌نام</span>
                <div class="loader"></div>
            </button>
        </form>

        <div class="signup-footer">
            <p>
                قبلاً ثبت‌نام کرده‌اید؟
                <a href="${pageContext.request.contextPath}/login.do">
                    <i class="bi bi-box-arrow-in-left"></i> ورود به سیستم
                </a>
            </p>
        </div>
    </div>
</div>

<script>
    function togglePassword(inputId, iconId) {
        const input = document.getElementById(inputId);
        const icon = document.getElementById(iconId);

        if (input.type === 'password') {
            input.type = 'text';
            icon.classList.remove('bi-eye');
            icon.classList.add('bi-eye-slash');
        } else {
            input.type = 'password';
            icon.classList.remove('bi-eye-slash');
            icon.classList.add('bi-eye');
        }
    }

    const passwordInput = document.getElementById('password');
    const strengthBar = document.getElementById('strengthBar');
    const strengthContainer = document.getElementById('passwordStrength');

    passwordInput.addEventListener('input', function() {
        const password = this.value;
        let strength = 0;

        if (password.length >= 6) strength++;
        if (password.length >= 10) strength++;
        if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength++;
        if (/\d/.test(password)) strength++;
        if (/[!@#$%^&*]/.test(password)) strength++;

        strengthContainer.style.display = password.length > 0 ? 'block' : 'none';

        strengthBar.className = 'password-strength-bar';
        if (strength <= 2) {
            strengthBar.classList.add('strength-weak');
        } else if (strength <= 4) {
            strengthBar.classList.add('strength-medium');
        } else {
            strengthBar.classList.add('strength-strong');
        }
    });

    const form = document.getElementById('signupForm');
    const username = document.getElementById('username');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');

    form.addEventListener('submit', function(e) {
        let isValid = true;

        document.querySelectorAll('.form-input').forEach(input => {
            input.classList.remove('input-error');
        });
        document.querySelectorAll('.error-message').forEach(msg => {
            msg.style.display = 'none';
        });

        if (!username.validity.valid) {
            username.classList.add('input-error');
            document.getElementById('usernameError').style.display = 'block';
            isValid = false;
        }

        if (password.value.length < 6) {
            password.classList.add('input-error');
            document.getElementById('passwordError').style.display = 'block';
            isValid = false;
        }

        if (password.value !== confirmPassword.value) {
            confirmPassword.classList.add('input-error');
            document.getElementById('confirmError').style.display = 'block';
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault();
            return false;
        }

        const signupBtn = document.getElementById('signupBtn');
        signupBtn.classList.add('loading');
        signupBtn.disabled = true;
    });

    confirmPassword.addEventListener('input', function() {
        const confirmError = document.getElementById('confirmError');
        if (this.value && this.value !== password.value) {
            this.classList.add('input-error');
            confirmError.style.display = 'block';
        } else {
            this.classList.remove('input-error');
            confirmError.style.display = 'none';
        }
    });

    setTimeout(() => {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-20px)';
            setTimeout(() => alert.remove(), 300);
        });
    }, 5000);
</script>
</body>
</html>