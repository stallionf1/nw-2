package com.itmg.mobilekit.ui.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.itmg.mobilekit.core.common.Config;
import com.itmg.mobilekit.core.service.MobileKitAPIService;

public class MobileKitInterceptor implements HandlerInterceptor  {
	
	@Autowired
	private MobileKitAPIService service;
	
	private static final Logger logger = Logger.getLogger(MobileKitInterceptor.class); 
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {         
    	
    	HttpSession session = request.getSession();
    	
    	if (session.getAttribute(Config.getInstance().getSessionCountry()) == null) {
    		String country = service.fetchUsersLocale(request.getRemoteAddr());
    		logger.debug(String.format("Country was not present in session. Get user's country by ip", country));
        	session.setAttribute(Config.getInstance().getSessionCountry(), country);        	
    	} else {
    		logger.debug(String.format("Session has country. Country = ", session.getAttribute(Config.getInstance().getSessionCountry())));
    	}
        return true;
    }
     
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {        
    }
     
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {        
    }
}