package com.wsx.Zuul.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class MyPasswordFilter extends ZuulFilter {

	@Override
	public Object run() {
		// 过滤器没有直接的方式来访问对方。 它们可以使用RequestContext共享状态，这是一个类似Map的结构
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		System.out.println("MyUPasswordFilter");
		String username = request.getParameter("password");
		if (username != null && username.equals("123456")) {
			requestContext.setSendZuulResponse(true);// 是否进行路由
			requestContext.setResponseStatusCode(200);
			requestContext.set("isSuccess", true);
		} else {
			requestContext.setSendZuulResponse(false);
			// requestContext.setResponseStatusCode(403);
			requestContext.setResponseBody("{\"result\":\"password is not correct!\"}");
			requestContext.set("isSuccess", false);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		if ((boolean) requestContext.get("isSuccess")) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}
