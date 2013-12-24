package com.itmg.mobilekit.ui.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.itmg.mobilekit.core.common.Config;
import com.itmg.mobilekit.core.service.MobileKitAPIService;

public class MobileKitInterceptor implements HandlerInterceptor  {
	
	@Autowired
	private MobileKitAPIService service;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         
    	
    	HttpSession session = request.getSession();
    	if (session.getAttribute(Config.getInstance().getUsersDeviceLocale()) == null) {
    		String country = service.fetchUsersLocale(request.getRemoteAddr());
        	session.setAttribute(Config.getInstance().getUsersDeviceLocale(), country);
        	System.out.println("***** fetched locale ="+country);
            
    	}
    	
        System.out.println("Pre-handle");
         
        return true;
    }
     
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("Post-handle");
    }
     
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("After completion handle");
    }
}