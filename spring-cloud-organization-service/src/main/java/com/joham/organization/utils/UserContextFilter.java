package com.joham.organization.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author joham
 */
@Component
public class UserContextFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        logger.info("Entering the UserContextFilter for the organization service");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        logger.info("I am entering the organization service id with auth token: ", httpServletRequest.getHeader("Authorization"));


        String correlationId = httpServletRequest.getHeader(UserContext.CORRELATION_ID);
        String userId = httpServletRequest.getHeader(UserContext.USER_ID);
        String authToken = httpServletRequest.getHeader(UserContext.AUTH_TOKEN);
        String orgId = httpServletRequest.getHeader(UserContext.ORG_ID);

        logger.info("***** I am entering the organization service id with correlation id: {}", correlationId);
        UserContextHolder.getContext();
        UserContext.setCorrelationId(correlationId);
        UserContextHolder.getContext();
        UserContext.setUserId(userId);
        UserContextHolder.getContext();
        UserContext.setAuthToken(authToken);
        UserContextHolder.getContext();
        UserContext.setOrgId(orgId);

        logger.info("Exiting the UserContextFilter");
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}