package com.example.generator.demo.common;

import io.swagger.models.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Title: Swagger2
 * @Description: Swagger2接口文档配置类
 * @Company: example
 * @Author: liuhf
 * @CreateTime: 2020/06/24 18:29:01
 */
@Configuration
@EnableSwagger2 //启用Swagger2
@EnableWebMvc //springMVC环境使用
@Profile(value = {"dev","test"})//启用环境
public class Swagger2 extends WebMvcConfigurerAdapter {

    @Value("${swagger.ui.title}")
    private String title = "Swagger Apis";
    @Value("${swagger.ui.version}")
    private String version = "Swagger Apis Version";
    @Value("${swagger.ui.description}")
    private String description = "Swagger Apis Description";
    //联系人
    @Value("${swagger.ui.contact.name}")
    private String contactName = "Swagger Apis Contact Name";
    @Value("${swagger.ui.contact.email}")
    private String contactEmail = "Swagger Apis Contact Email";
    @Value("${swagger.ui.contact.url}")
    private String contactUrl = "Swagger Apis Contact Url";
    //许可人
    @Value("${swagger.ui.license}")
    private String license = "Swagger Apis License";
    @Value("${swagger.ui.licenseUrl}")
    private String licenseUrl = "Swagger Apis LicenseUrl";
    // 服务条款
    @Value("${swagger.ui.termsOfServiceUrl}")
    private String termsOfServiceUrl = "Swagger Apis TermsOfServiceUrl";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.exmaple.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .description(description)
                //联系人
                .contact(contactInfo())
                //许可人
//                .license(license)
//                .licenseUrl(licenseUrl)
                //服务条款URL
                .termsOfServiceUrl(termsOfServiceUrl)
                .build();
    }

    private String contactInfo() {
        return new Contact()
                .name(contactName)
                .email(contactEmail)
//                .url(contactUrl)
                .toString();
    }

    /**
     * 防止@EnableMvc把默认的静态资源路径覆盖了，手动设置的方式
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
