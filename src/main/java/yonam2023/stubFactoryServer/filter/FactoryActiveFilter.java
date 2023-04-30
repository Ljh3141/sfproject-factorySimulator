package yonam2023.stubFactoryServer.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import yonam2023.stubFactoryServer.service.StubRunning;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FactoryActiveFilter implements Filter {
    private static final String[] whitelist = {"/pause/","/shutdown/","/checkMcState/*","/runMachine/*","/stopMachine/*"};

    private StubRunning st;
    private Logger logger = LoggerFactory.getLogger(FactoryActiveFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("FactoryActiveFilter:Request received");

        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        logger.info("requestURI : "+requestURI);
        if(isFactoryRunCheckPath(requestURI)){
            logger.info("FactoryActiveFilter:check factory state");
            logger.info("FactoryActiveFilter:factory state:"+st.getFactoryState());
            if(!st.getFactoryState()){
                httpResponse.sendRedirect("/serverNotActive");
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
    private boolean isFactoryRunCheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    public FactoryActiveFilter(StubRunning stubRunning){
        this.st = stubRunning;
    }
}
