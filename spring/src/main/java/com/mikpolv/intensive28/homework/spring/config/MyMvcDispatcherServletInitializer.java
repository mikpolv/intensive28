package com.mikpolv.intensive28.homework.spring.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyMvcDispatcherServletInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[0];
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {MySpringConfig.class};
  }

  @Override
  protected String @NotNull [] getServletMappings() {
    return new String[] {"/"};
  }

  @Override
  public void onStartup(@NotNull ServletContext aServletContext) throws ServletException {
    super.onStartup(aServletContext);
    registerHiddenFieldFilter(aServletContext);
  }

  private void registerHiddenFieldFilter(ServletContext aContext) {
    aContext
        .addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
        .addMappingForUrlPatterns(null, true, "/*");
  }
}
