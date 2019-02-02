package cn.eastseven.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.annotation.Configuration;

/**
 * @author d7
 * @see http://springfox.github.io/springfox/
 * @see http://springfox.github.io/springfox/docs/current/
 * @see https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * @see https://github.com/SpringForAll/spring-boot-starter-swagger/blob/master/README.md
 */
@Configuration
//@EnableSwagger2
@EnableSwagger2Doc
public class Swagger2Config {

    /*@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }*/


}
