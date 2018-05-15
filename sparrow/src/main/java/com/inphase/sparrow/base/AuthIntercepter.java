package com.inphase.sparrow.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.inphase.sparrow.base.util.CommonUtils;

public class AuthIntercepter extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(CommonUtils.getCookie(request, "loginuser") == null){
			request.getRequestDispatcher("/WEB-INF/login_jump.jsp").forward(request, response);
			return false;
		}
		return true;
	}
}
