# 🏢 سیستم مدیریت سازمانی - SecondOrganization

<div align="center">

**سیستم جامع مدیریت سازمانی سطح Enterprise با Jakarta EE**

[ویژگی‌ها](#-ویژگیها) • [نصب](#-نصب-و-راهاندازی) • [استفاده](#-استفاده) • [API](#-مستندات-api) • [مشارکت](#-مشارکت)



</div>

## 📑 فهرست

- [درباره پروژه](#-درباره-پروژه)
- [ویژگی‌ها](#-ویژگیها)
- [تکنولوژی‌ها](#-تکنولوژیها)
- [پیش‌نیازها](#-پیشنیازها)
- [نصب و راه‌اندازی](#-نصب-و-راهاندازی)
- [پیکربندی](#-پیکربندی)
- [استفاده](#-استفاده)
- [ساختار پروژه](#-ساختار-پروژه)
- [مستندات API](#-مستندات-api)
- [مشارکت](#-مشارکت)

## 🎯 درباره پروژه

یک سیستم مدیریت سازمانی جامع که با استفاده از Jakarta EE پیاده‌سازی شده است. این سیستم شامل:

- ✅ مدیریت سازمان‌ها و شعب
- ✅ مدیریت پرسنل و دپارتمان‌ها
- ✅ مدیریت پروژه‌ها و تیم‌ها
- ✅ احراز هویت و کنترل دسترسی مبتنی بر نقش
- ✅ REST API کامل
- ✅ رابط کاربری واکنش‌گرا و فارسی

## ✨ ویژگی‌ها

### 🔐 احراز هویت و امنیت
- ✓ سیستم ورود و ثبت‌نام امن
- ✓ سه سطح دسترسی: Admin, Manager, User
- ✓ مدیریت نشست با timeout خودکار
- ✓ کنترل دسترسی مبتنی بر نقش (RBAC)

### 🏢 مدیریت سازمانی
- ✓ ایجاد و مدیریت چند سازمان
- ✓ شعب و واحدهای سازمانی
- ✓ دپارتمان‌ها با بودجه‌بندی
- ✓ گروه‌های کاری

### 👥 مدیریت پرسنل
- ✓ پروفایل کامل کارمندان
- ✓ اطلاعات شخصی و استخدامی
- ✓ تخصیص به گروه‌های سازمانی
- ✓ پیگیری حقوق و مزایا
- ✓ جستجوی پیشرفته

### 📊 مدیریت پروژه
- ✓ ایجاد و پیگیری پروژه‌ها
- ✓ وضعیت‌های مختلف پروژه
- ✓ مدیریت بودجه و زمان‌بندی
- ✓ تخصیص تیم به پروژه
- ✓ داشبورد آماری

### 🔧 قابلیت‌های فنی
- ✓ REST API کامل
- ✓ اعتبارسنجی داده در Client & Server
- ✓ Soft Delete
- ✓ Audit Trail
- ✓ Responsive Design
- ✓ پشتیبانی کامل از زبان فارسی (RTL)

## 🛠 تکنولوژی‌ها

### Backend
| تکنولوژی | نسخه | کاربرد |
|----------|------|---------|
| Java | 11 | زبان برنامه‌نویسی |
| Jakarta EE | 9.1 | فریمورک Enterprise |
| Hibernate | 6.2 | ORM Framework |
| Oracle DB | 11g+ | پایگاه داده |
| Maven | 3.8+ | Build Tool |
| Lombok | 1.18 | کاهش Boilerplate |

### Frontend
| تکنولوژی | نسخه | کاربرد |
|----------|------|---------|
| JSP/JSTL | 3.0 | Server-side Rendering |
| Bootstrap | 5.3 | UI Framework (RTL) |
| JavaScript | ES6+ | تعاملات کاربری |
| CSS3 | - | استایل و انیمیشن |

### Application Server
- ✅ Apache TomEE 9.x (پیشنهادی)
- ✅ WildFly 26+
- ✅ Payara Server 6+

## 📦 پیش‌نیازها

قبل از شروع، موارد زیر را نصب کنید:
قبل از شروع، موارد زیر را نصب کنید:

### 1. Java Development Kit
```bash
# نصب JDK 11
# بررسی نسخه
java -version
# خروجی: java version "11.x.x"
```

### 2. Apache Maven
```bash
# بررسی نسخه Maven
mvn -version
# خروجی: Apache Maven 3.8.x
```

###  3. پایگاه داده
   Oracle Database 11g+ یا MySQL 8.0+

```sql
-- ایجاد دیتابیس
CREATE DATABASE organization;
```
### 4. Application Server
   Apache TomEE 9.x (توصیه می‌شود)
```bash
# دانلود از
https://tomee.apache.org/download-ng.html
```
## 🚀 نصب و راه‌اندازی

### **مرحله 1: دریافت پروژه**

```bash
git clone https://github.com/yourusername/jakarta.git
cd jakarta/secondOrganization
```
### مرحله 2: راه‌اندازی دیتابیس

برای Oracle:
```sql
-- اتصال به Oracle به عنوان SYSDBA
sqlplus / as sysdba

-- ایجاد کاربر
CREATE USER Javaee IDENTIFIED BY java123;
GRANT CONNECT, RESOURCE, DBA TO Javaee;
GRANT UNLIMITED TABLESPACE TO Javaee;
EXIT;
```
برای  MySQL:
```sql
-- اتصال به MySQL
mysql -u root -p

-- ایجاد دیتابیس و کاربر
CREATE DATABASE organization
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE USER 'Javaee'@'localhost'
IDENTIFIED BY 'Java123';

GRANT ALL PRIVILEGES ON organization.*
TO 'Javaee'@'localhost';

FLUSH PRIVILEGES;
EXIT;
```

### مرحله 3: پیکربندی اتصال

فایل src/main/webapp/WEB-INF/resources.xml را ویرایش کنید:

برای Oracle:
```xml
<Resource id="organizationDataSource" type="DataSource">
    JdbcDriver = oracle.jdbc.OracleDriver
    JdbcUrl = jdbc:oracle:thin:@localhost:1521:XE
    UserName = Javaee
    Password = java123
    JtaManaged = true
    MaxActive = 20
    MaxIdle = 10
    MinIdle = 2
</Resource>
```

برای MySQL:
```xml
<Resource id="organizationDataSource" type="DataSource">
    JdbcDriver = com.mysql.cj.jdbc.Driver
    JdbcUrl = jdbc:mysql://localhost:3306/organization?useSSL=false&amp;serverTimezone=UTC
    UserName = javaee
    Password = java123
    JtaManaged = true
    MaxActive = 20
    MaxIdle = 10
    MinIdle = 2
</Resource>
```
### مرحله 4: Build پروژه
```bash
# پاک‌سازی و Build
mvn clean package

# خروجی:
# [INFO] Building war: .../target/SecondOrganization.war
# [INFO] BUILD SUCCESS
```
### مرحله 5: Deploy
با TomEE:
```bash
# کپی فایل WAR
cp target/SecondOrganization.war $TOMEE_HOME/webapps/

# راه‌اندازی سرور
cd $TOMEE_HOME/bin
./startup.sh    # Linux/Mac
# یا
startup.bat     # Windows
```
### مرحله 6: دسترسی به برنامه
مرورگر را باز کرده و به آدرس زیر بروید:
```text
http://localhost:80/SecondOrganization/
```
## 🔧 پیکربندی

### حساب‌های پیش‌فرض
سیستم با 3 حساب آزمایشی راه‌اندازی می‌شود:

| نقش | نام کاربری | رمز عبور | دسترسی‌ها |
|-----|------------|----------|-----------|
| 👑 Admin | admin | admin123 | دسترسی کامل به سیستم |
| 👔 Manager | manager | manager123 | مدیریت دپارتمان |
| 👤 User | user | user123 | عملیات پایه |

### 📖 استفاده
سناریوی کاری استاندارد

#### 1️⃣ ورود به سیستم
به صفحه login.jsp بروید

از حساب‌های آزمایشی استفاده کنید یا ثبت‌نام کنید

پس از ورود، به داشبورد منتقل می‌شوید

#### 2️⃣ ایجاد سازمان
``` text
داشبورد → مدیریت سازمان‌ها → افزودن سازمان جدید

- نام سازمان: شرکت فناوری پارس
- نوع: خصوصی
- ذخیره
```

#### 3️⃣ افزودن شعبه
```text
مدیریت سازمان‌ها → شعب → افزودن شعبه

- نام شعبه: شعبه مرکزی
- شهر: تهران
- سازمان: شرکت فناوری پارس
- ذخیره
```
#### 4️⃣ ایجاد دپارتمان
```text
دپارتمان‌ها → افزودن دپارتمان جدید

- نام: دپارتمان IT
- رشته فعالیت: فناوری اطلاعات
- بودجه: 50,000,000
- شعبه: شعبه مرکزی
- ذخیره
```

#### 5️⃣ ثبت پرسنل
```text
پرسنل → افزودن پرسنل جدید

- نام و نام خانوادگی
- کد ملی (یکتا)
- جنسیت
- حقوق
- گروه سازمانی
- ذخیره
```

#### 6️⃣ ایجاد پروژه
```text
پروژه‌ها → افزودن پروژه جدید

- عنوان: پروژه توسعه سایت
- بودجه: 100,000,000
- تاریخ شروع و پایان
- وضعیت: فعال
- اعضای تیم: انتخاب از لیست پرسنل
- ذخیره
```
## استفاده از REST API
دریافت لیست سازمان‌ها
```bash
curl -X GET http://localhost:8080/SecondOrganization/api/organization \
  -H "Content-Type: application/json"
  ```
به‌روزرسانی پروژه
```bash
curl -X PUT http://localhost:8080/SecondOrganization/api/project/1 \
-H "Content-Type: application/json" \
-d '{
"status": "COMPLETED"
}'
```
حذف پرسنل
```curl -X DELETE http://localhost:8080/SecondOrganization/api/persons/5
```

---

## 📁 ساختار پروژه
```
SecondOrganization/
│
├── 📂 src/main/
│   ├── 📂 java/com/secondOrganization/
│   │   ├── 📂 config/                    # پیکربندی‌ها
│   │   │   ├── DataInitializer.java      # داده‌های اولیه
│   │   │   └── LoggingInitializer.java   # تنظیمات لاگ
│   │   │
│   │   ├── 📂 controller/                # کنترلرها
│   │   │   ├── 📂 api/                   # REST APIs
│   │   │   │   ├── OrganizationApi.java
│   │   │   │   ├── PersonApi.java
│   │   │   │   ├── ProjectApi.java
│   │   │   │   ├── DepartmentApi.java
│   │   │   │   └── BranchApi.java
│   │   │   │
│   │   │   ├── 📂 servlet/               # Web Servlets
│   │   │   │   ├── LoginServlet.java
│   │   │   │   ├── SignupServlet.java
│   │   │   │   ├── OrganizationServlet.java
│   │   │   │   └── PersonServlet.java
│   │   │   │
│   │   │   ├── 📂 filter/                # فیلترها
│   │   │   └── 📂 exception/             # مدیریت خطا
│   │   │
│   │   ├── 📂 model/                     # مدل‌ها
│   │   │   ├── 📂 entity/                # Entity Classes
│   │   │   │   ├── Base.java
│   │   │   │   ├── Organization.java
│   │   │   │   ├── Branch.java
│   │   │   │   ├── Department.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── Project.java
│   │   │   │   ├── User.java
│   │   │   │   └── Role.java
│   │   │   │
│   │   │   └── 📂 enums/                 # Enumerations
│   │   │       ├── Gender.java
│   │   │       ├── ProjectStatus.java
│   │   │       └── Role.java
│   │   │
│   │   ├── 📂 repository/                # لایه داده
│   │   │   ├── BaseRepository.java
│   │   │   ├── OrganizationRepository.java
│   │   │   └── PersonRepository.java
│   │   │
│   │   ├── 📂 service/                   # Business Logic
│   │   │   ├── OrganizationService.java
│   │   │   ├── PersonService.java
│   │   │   └── 📂 impl/
│   │   │       ├── OrganizationServiceImpl.java
│   │   │       └── PersonServiceImpl.java
│   │   │
│   │   └── 📂 utils/                     # ابزارهای کمکی
│   │
│   ├── 📂 resources/
│   │   ├── 📂 META-INF/
│   │   │   ├── beans.xml
│   │   │   └── persistence.xml
│   │   └── logback.xml
│   │
│   └── 📂 webapp/
│       ├── 📂 WEB-INF/
│       │   ├── web.xml
│       │   └── resources.xml
│       │
│       ├── 📂 jsp/                       # صفحات JSP
│       │   ├── organization.jsp
│       │   ├── department.jsp
│       │   ├── branch.jsp
│       │   ├── person.jsp
│       │   ├── project.jsp
│       │   └── organizationGroup.jsp
│       │
│       ├── 📂 assets/
│       │   ├── 📂 css/
│       │   └── 📂 js/
│       │
│       ├── login.jsp
│       ├── signup.jsp
│       ├── dashboard.jsp
│       └── index.jsp
│
└── 📄 pom.xml                            # Maven Config

```

## 🌐 مستندات API
### Organizations API
| Method | Endpoint | توضیحات |
|--------|----------|----------|
| GET | `/api/organization` | دریافت تمام سازمان‌ها |
| GET | `/api/organization/{id}` | دریافت سازمان با ID |
| POST | `/api/organization` | ایجاد سازمان جدید |
| PUT | `/api/organization` | به‌روزرسانی سازمان |
| DELETE | `/api/organization/{id}` | حذف سازمان |

## 🤝 مشارکت

مشارکت شما در بهبود این پروژه بسیار ارزشمند است!

### راهنمای مشارکت

1. Fork کردن پروژه
2. کلون کردن Fork
```bash
git clone https://github.com/YOUR-USERNAME/jakarta.git
cd jakarta/secondOrganization
````
3. ایجاد Branch جدید
```bash
git checkout -b feature/amazing-feature
````
4. Commit و Push تغییرات

```bash
git add .
git commit -m "✨ Add amazing feature"
git push origin feature/amazing-feature
ایجاد Pull Request
```
5. ایجاد Pull Request



## 👨‍💻 توسعه‌دهنده
<div align="center">
Parnian Ghanbarpour 

</div>

[⬆ بازگشت به بالا
](#-سیستم-مدیریت-سازمانی---secondorganization)

© 2024 Organization Management System. All rights reserved.






