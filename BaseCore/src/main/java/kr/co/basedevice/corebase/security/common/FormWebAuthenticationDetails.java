package kr.co.basedevice.corebase.security.common;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;

public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = 8035499293583546369L;
	private String otpKey;
	private String ipAddr;

    public FormWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.otpKey = request.getParameter("otpKey");
        this.ipAddr = request.getRemoteAddr();
        
    }

    public String getSecretKey() {
        return this.otpKey;
    }
    
    public String getIpAddr() {
    	return this.ipAddr;
    }
}