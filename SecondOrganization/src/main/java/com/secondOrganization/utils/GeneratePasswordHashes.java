package com.secondOrganization.utils;

import com.secondOrganization.utils.PasswordUtil;

public class GeneratePasswordHashes {
    public static void main(String[] args) {
        System.out.println(" تولید هش BCrypt برای کاربران قدیمی\n");
        System.out.println("=== کپی کنید و در SQL Developer اجرا کنید ===\n");

        String adminHash = PasswordUtil.hashPassword("admin123");
        System.out.println("-- برای کاربر admin:");
        System.out.println("UPDATE user_tbl SET user_password = '" + adminHash + "' WHERE user_username = 'admin';");
        System.out.println();

        String managerHash = PasswordUtil.hashPassword("manager123");
        System.out.println("-- برای کاربر manager:");
        System.out.println("UPDATE user_tbl SET user_password = '" + managerHash + "' WHERE user_username = 'manager';");
        System.out.println();

        String userHash = PasswordUtil.hashPassword("user123");
        System.out.println("-- برای کاربر user:");
        System.out.println("UPDATE user_tbl SET user_password = '" + userHash + "' WHERE user_username = 'user';");
        System.out.println();

        System.out.println("COMMIT;");
        System.out.println("\n  توجه: مطمئن شوید ستون USER_PASSWORD حداقل VARCHAR2(100) باشد!");

        System.out.println("تولید هش BCrypt برای کاربران باقی‌مانده\n");

        String[][] remainingUsers = {
                {"parnian", "Parnian123"},
                {"fateme12", "fateme12123"},
                {"amir123", "amir123123"},
                {"user", "user123"}
        };

        for (String[] user : remainingUsers) {
            String username = user[0];
            String password = user[1];
            String hashed = PasswordUtil.hashPassword(password);

            System.out.println("-- برای کاربر " + username + ":");
            System.out.println("UPDATE user_tbl SET user_password = '" + hashed +
                    "' WHERE user_username = '" + username + "';");
            System.out.println();
        }

        System.out.println("COMMIT;");
    }

}