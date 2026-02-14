package com.qoobot.openscm.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 配置
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OpenSCM API 文档")
                        .description("OpenSCM 供应链管理系统 API 文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("OpenSCM Team")
                                .email("dev@qoobot.com")
                                .url("https://github.com/qoobot-com/openscm"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
