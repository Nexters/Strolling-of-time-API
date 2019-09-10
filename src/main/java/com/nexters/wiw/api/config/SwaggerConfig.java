package com.nexters.wiw.api.config;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.classmate.TypeResolver;
import com.nexters.wiw.api.common.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {
    @Autowired
    private TypeResolver typeResolver;

    // DeferredResult<List<T>> to List<T>
    public AlternateTypeRule getAlternateTypeRule(Type sourceType, Type sourceGenericType, Type sourceSubGenericType,
            Type targetType, Type targetGenericType) {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(sourceType, typeResolver.resolve(sourceGenericType, sourceSubGenericType)),
                typeResolver.resolve(targetType, targetGenericType));
    }

    // Collection<T> to List<T>
    public AlternateTypeRule getAlternateTypeRule(Type sourceType, Type sourceGenericType, Type targetType,
            Type targetGenericType) {
        return AlternateTypeRules.newRule(typeResolver.resolve(sourceType, sourceGenericType),
                typeResolver.resolve(targetType, targetGenericType));
    }

    // Resources<T> to Resources
    public AlternateTypeRule getAlternateTypeRule(Type sourceType, Type sourceGenericType, Type targetType) {
        return AlternateTypeRules.newRule(typeResolver.resolve(sourceType, sourceGenericType),
                typeResolver.resolve(targetType));
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).securitySchemes(securitySchemes())
                .alternateTypeRules(
                        getAlternateTypeRule(Collection.class, WildcardType.class, List.class, WildcardType.class),
                        getAlternateTypeRule(DeferredResult.class, List.class, WildcardType.class, List.class,
                                WildcardType.class))
                .ignoredParameterTypes(PagedResourcesAssembler.class, Pageable.class, Auth.class)
                .directModelSubstitute(MessageSourceResolvable.class, String.class).select()
                .apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/api/**")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("시계는 와치⏰ API").description("와치 와치").version("1.0.0").build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> schemes = new ArrayList<>();
        schemes.add(new BasicAuth("basicAuth"));
        schemes.add(new ApiKey("apiKey", "Authorization", "header"));
        return schemes;
    }
}
