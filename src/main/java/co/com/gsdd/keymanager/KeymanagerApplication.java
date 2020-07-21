package co.com.gsdd.keymanager;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@ComponentScan({ KeymanagerApplication.COMPONENT, KeymanagerApplication.COMPONENT, KeymanagerApplication.CONFIG,
        KeymanagerApplication.CONTROLLER, KeymanagerApplication.REPO, KeymanagerApplication.SERVICES })
public class KeymanagerApplication {

    public static final String BASE = "co.com.gsdd.keymanager";
    public static final String COMPONENT = BASE + ".components";
    public static final String CONFIG = BASE + ".configs";
    public static final String CONTROLLER = BASE + ".controllers";
    public static final String REPO = BASE + ".repositories";
    public static final String SERVICES = BASE + ".services";

    public static void main(String[] args) {
        SpringApplication.run(KeymanagerApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.TRUE);
        // *** URL below needs to match the client URL and port ***
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
