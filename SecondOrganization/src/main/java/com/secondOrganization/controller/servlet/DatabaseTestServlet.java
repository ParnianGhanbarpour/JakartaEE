package com.secondOrganization.controller.servlet;

import com.secondOrganization.service.UserService;
import com.secondOrganization.model.entity.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Slf4j
@WebServlet(urlPatterns = "/test-db")
public class DatabaseTestServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html dir='rtl'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Database Test</title>");
        out.println("<style>");
        out.println("body { font-family: Tahoma; padding: 20px; background: #f5f5f5; }");
        out.println(".container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; }");
        out.println("h1 { color: #667eea; }");
        out.println("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
        out.println("th, td { padding: 12px; text-align: right; border: 1px solid #ddd; }");
        out.println("th { background: #667eea; color: white; }");
        out.println(".success { color: green; }");
        out.println(".error { color: red; }");
        out.println(".info { background: #e3f2fd; padding: 15px; border-radius: 8px; margin: 10px 0; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1> تست دیتابیس و کاربران</h1>");

        try {

            out.println("<h2> لیست کاربران در دیتابیس:</h2>");
            List<User> users = userService.findAll();

            if (users.isEmpty()) {
                out.println("<p class='error'> هیچ کاربری در دیتابیس وجود ندارد!</p>");
                out.println("<div class='info'>");
                out.println("<strong>راهکار:</strong><br>");
                out.println("1. سرور رو restart کنید تا DataInitializer اجرا بشه<br>");
                out.println("2. یا از طریق SignupServlet کاربر جدید بسازید");
                out.println("</div>");
            } else {
                out.println("<p class='success'> تعداد کاربران: " + users.size() + "</p>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>ردیف</th>");
                out.println("<th>نام کاربری</th>");
                out.println("<th>رمز عبور</th>");
                out.println("<th>فعال</th>");
                out.println("<th>حذف شده</th>");
                out.println("</tr>");

                int i = 1;
                for (User user : users) {
                    out.println("<tr>");
                    out.println("<td>" + i++ + "</td>");
                    out.println("<td><strong>" + user.getUsername() + "</strong></td>");
                    out.println("<td><code>" + user.getPassword() + "</code></td>");
                    out.println("<td>" + (user.isActive() ? "✅" : "❌") + "</td>");
                    out.println("<td>" + (user.isDeleted() ? "❌" : "✅") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }

            // تست 2: تست لاگین
            out.println("<h2> تست لاگین:</h2>");
            String[] testUsers = {"admin", "manager", "user"};
            String[] testPasswords = {"admin123", "manager123", "user123"};

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>نام کاربری</th>");
            out.println("<th>رمز عبور</th>");
            out.println("<th>نتیجه</th>");
            out.println("</tr>");

            for (int j = 0; j < testUsers.length; j++) {
                String username = testUsers[j];
                String password = testPasswords[j];

                try {
                    var result = userService.findByUsernameAndPassword(username, password);
                    out.println("<tr>");
                    out.println("<td>" + username + "</td>");
                    out.println("<td>" + password + "</td>");
                    if (result.isPresent()) {
                        out.println("<td class='success'> موفق</td>");
                    } else {
                        out.println("<td class='error'> ناموفق</td>");
                    }
                    out.println("</tr>");
                } catch (Exception e) {
                    out.println("<tr>");
                    out.println("<td>" + username + "</td>");
                    out.println("<td>" + password + "</td>");
                    out.println("<td class='error'> خطا: " + e.getMessage() + "</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");

            out.println("<div class='info'>");
            out.println("<h3>ℹ اطلاعات تکمیلی:</h3>");
            out.println("<p><strong>EntityManager:</strong> " + (userService != null ? " Injected" : " NULL") + "</p>");
            out.println("<p><strong>Context Path:</strong> " + req.getContextPath() + "</p>");
            out.println("<p><strong>Server Info:</strong> " + getServletContext().getServerInfo() + "</p>");
            out.println("</div>");

        } catch (Exception e) {
            log.error("Error in database test", e);
            out.println("<p class='error'> خطا در تست دیتابیس:</p>");
            out.println("<pre style='background: #ffebee; padding: 15px; border-radius: 5px;'>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

        out.println("<hr>");
        out.println("<p style='text-align: center;'>");
        out.println("<a href='" + req.getContextPath() + "/login.do' style='color: #667eea; text-decoration: none; font-weight: bold;'>");
        out.println("← بازگشت به صفحه لاگین");
        out.println("</a>");
        out.println("</p>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}