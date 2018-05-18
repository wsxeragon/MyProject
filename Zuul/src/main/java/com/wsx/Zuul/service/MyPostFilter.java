package com.wsx.Zuul.service;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class MyPostFilter extends ZuulFilter {

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		// requestContext.setResponseBody("{\"result\":\"CCCCCC\"}");
		if ((boolean) requestContext.get("isSuccess")) {
			requestContext.setResponseStatusCode(200);
			// 设置返回值
			// requestContext.setResponseBody("{\"msg\":\"hello world\"}");
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
		return 3;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

}
