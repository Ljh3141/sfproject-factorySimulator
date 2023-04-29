package yonam2023.stubFactoryServer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yonam2023.stubFactoryServer.filter.FactoryActiveFilter;
import yonam2023.stubFactoryServer.service.StubRunning;

import javax.servlet.Filter;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Autowired
    StubRunning stubRunning;

    @Bean
    public FilterRegistrationBean<Filter> factoryActiveFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();

        filterRegistrationBean.setFilter(new FactoryActiveFilter(stubRunning));	// LogFilter등록
        filterRegistrationBean.setOrder(1);	// 먼저 적용할 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); //모든 url 다 적용 -> 모든 요청에 다 필터 적용
        return filterRegistrationBean;
    }
}
