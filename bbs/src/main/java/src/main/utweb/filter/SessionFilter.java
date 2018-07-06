package src.main.utweb.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionFilter implements Filter {

    protected static List<Pattern> patterns = new ArrayList<Pattern>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        System.out.println(url);
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        if (isInclude(url)){
            chain.doFilter(httpRequest, httpResponse);
            return;
        } else {
        	System.out.println("此url被拦截");
            HttpSession session = httpRequest.getSession();
            if (session.getAttribute("userName") != null){
                // session存在
            	System.out.println("session存在");
                chain.doFilter(httpRequest, httpResponse);
                return;
            } else {
            	System.out.println("session不存在");
                // session不存在 准备跳转失败
            	httpResponse.sendRedirect("http://localhost:8080/login");
//                chain.doFilter(httpRequest, httpResponse);
                return;
            }
        }


    }

    @Override
    public void destroy() {

    }


    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

}