package kr.co.basedevice.corebase.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    final static public String getClientIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null) ip = req.getRemoteAddr();
        return ip;
    }
}
