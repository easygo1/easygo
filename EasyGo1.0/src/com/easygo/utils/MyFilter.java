package com.easygo.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "code", value = "utf-8") })
public class MyFilter implements Filter {
	String code = "utf-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		code = filterConfig.getInitParameter("code");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(code);
		response.setCharacterEncoding(code);
		response.setContentType("text/html;charset=" + code);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
