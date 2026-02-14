package com.qoobot.openscm.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * OpenSCM 管理后台启动类
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.qoobot.openscm")
@MapperScan("com.qoobot.openscm.*.mapper")
public class OpenScmApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenScmApplication.class, args);
        System.out.println("""

                ========================================================
                   OpenSCM 供应链管理系统启动成功！
                   访问地址: http://localhost:8080/api
                   API文档: http://localhost:8080/api/swagger-ui.html
                ========================================================
                """);
    }
}
