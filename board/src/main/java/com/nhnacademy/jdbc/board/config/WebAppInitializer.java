package com.nhnacademy.jdbc.board.config;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static int MAX_FILE_SIZE = 10 * 1024 * 1024;

    @Override
    public void onStartup (ServletContext servletContext) throws ServletException
    {
        super.onStartup(servletContext);
        servletContext.addFilter("name", XssEscapeServletFilter.class)
            .addMappingForUrlPatterns(null, false, "/*");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{com.nhnacademy.jdbc.board.config.RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement
            multipartConfig = new MultipartConfigElement("", MAX_FILE_SIZE, MAX_FILE_SIZE, 0);
        registration.setMultipartConfig(multipartConfig);
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        XssEscapeServletFilter xssEscapeServletFilter= new XssEscapeServletFilter();
        MultipartFilter multipartFilter =new MultipartFilter();

        return new Filter[]{characterEncodingFilter, hiddenHttpMethodFilter, xssEscapeServletFilter, multipartFilter};
    }
}
