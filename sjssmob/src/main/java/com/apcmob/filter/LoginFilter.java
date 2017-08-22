package com.apcmob.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(false);

		String URL     = "/sjssmob/login.do";
				
		String reqUrl = ((HttpServletRequest) request).getRequestURI();
		//System.out.println("reqUrl :: " + reqUrl);
		
		//로그인 체크 안하는것들
		if(
				reqUrl.toUpperCase().indexOf("LOGIN") > -1
			   || reqUrl.toUpperCase().indexOf("SELECTBRANCHCODE") > -1 
//			   || reqUrl.toUpperCase().indexOf("MAIN") > -1
		){
			chain.doFilter(request, response);
		}else{//로그인 체크 필요한것들
			if(session ==null || session.getAttribute("ACCOUNT") == null){
				if ("XMLHttpRequest".equals( ((HttpServletRequest) request).getHeader("X-Requested-With"))){ //Ajax SessionTimeOut
				    HttpServletResponse resp = (HttpServletResponse) response;
	                resp.sendError(901,"Session Time Out!!");
				}else{
					((HttpServletResponse) response).sendRedirect(URL);//기본 로그인 페이지
				}
			}else {
				chain.doFilter(request, response);
			}
		}
	}

	public void destroy() {
		filterConfig = null;
	}
}