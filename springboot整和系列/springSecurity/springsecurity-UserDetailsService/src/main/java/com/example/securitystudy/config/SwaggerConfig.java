package com.example.securitystudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        // 创建一个 swagger 的 bean 实例
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置基本信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))    //这个是重点
                .paths(PathSelectors.any())
                .build();
    }

    // 基本信息设置
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "努力的派大星", // 作者姓名
                "https://gitee.com/a13657926270", // 作者网址
                "xj19148@163.com"); // 作者邮箱
        return new ApiInfoBuilder()
                .title("接口文档") // 标题
                .description("hello") // 描述
                .termsOfServiceUrl("--") // 跳转连接
                .version("1.0") // 版本
                .license("Swagger-的使用")
                .licenseUrl("https://gitee.com/a13657926270")
                .contact(contact)
                .build();
    }
}
