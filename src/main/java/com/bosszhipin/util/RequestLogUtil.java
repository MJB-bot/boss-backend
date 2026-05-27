package com.bosszhipin.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

@Slf4j
public final class RequestLogUtil {

    private RequestLogUtil() {}

    public static void logRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return;
        HttpServletRequest request = attrs.getRequest();
        StringBuilder sb = new StringBuilder("\n=== Request ===\n");
        sb.append("Method: ").append(request.getMethod()).append("\n");
        sb.append("URI: ").append(request.getRequestURI()).append("\n");
        sb.append("IP: ").append(getClientIp(request)).append("\n");
        sb.append("Headers: ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if ("authorization".equalsIgnoreCase(name)) {
                sb.append(name).append("=***, ");
            } else {
                sb.append(name).append("=").append(request.getHeader(name)).append(", ");
            }
        }
        log.info(sb.toString());
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip != null && ip.contains(",") ? ip.split(",")[0].trim() : ip;
    }
}
