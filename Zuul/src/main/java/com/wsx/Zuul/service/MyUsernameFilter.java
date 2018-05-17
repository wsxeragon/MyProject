package com.wsx.Zuul.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class MyUsernameFilter extends ZuulFilter {

	@Override
	public Object run() {
		// 过滤器没有直接的方式来访问对方。 它们可以使用RequestContext共享状态，这是一个类似Map的结构
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		System.out.println("MyUsernameFilter");
		String username = request.getParameter("username");
		if (username != null && username.equals("admin")) {
			requestContext.setSendZuulResponse(true);// 是否进行路由
			requestContext.setResponseStatusCode(200);
			requestContext.set("isSuccess", true);
		} else {
			requestContext.setSendZuulResponse(false);
			// requestContext.setResponseStatusCode(403);
			requestContext.setResponseBody("{\"result\":\"username is not correct!\"}");
			requestContext.set("isSuccess", false);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}
