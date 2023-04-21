package kr.co.basedevice.corebase.security.common;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class AjaxWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = 8035499293583546369L;
	private  String otpKey;

    public AjaxWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        otpKey = request.getParameter("otpKey");
    }

    public String getSecretKey() {

        return otpKey;
    }
}