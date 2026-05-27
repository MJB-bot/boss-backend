package com.bosszhipin.util;

public final class SensitiveDataUtil {

    private SensitiveDataUtil() {}

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) return phone;
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        int at = email.indexOf('@');
        String prefix = email.substring(0, at);
        if (prefix.length() <= 2) return "*" + email.substring(at);
        return prefix.substring(0, 2) + "***" + email.substring(at);
    }

    public static String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 8) return idCard;
        return idCard.substring(0, 4) + "**********" + idCard.substring(idCard.length() - 4);
    }

    public static String maskRealName(String name) {
        if (name == null || name.isEmpty()) return name;
        if (name.length() == 1) return "*";
        return name.charAt(0) + "*".repeat(name.length() - 1);
    }
}
