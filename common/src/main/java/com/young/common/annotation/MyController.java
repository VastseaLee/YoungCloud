package com.young.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
@WebResultAnn
public @interface MyController {
    @AliasFor("path")
    String[] value() default {};

    @AliasFor("value")
    String[] path() default {};
}
