package com.young.common.config;

import com.young.common.annotation.WebResultAnn;
import com.young.common.bean.WebResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.lang.reflect.Executable;

@RestControllerAdvice
public class ResultAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
                                           MediaType contentType, MethodParameter returnType,
                                           ServerHttpRequest request, ServerHttpResponse response) {
        Executable executable = returnType.getExecutable();
        Class declaringClass = executable.getDeclaringClass();
        if (declaringClass.isAnnotationPresent(WebResultAnn.class)) {
            bodyContainer.setValue(getWrapperResponse(bodyContainer.getValue()));
        }
    }

    private WebResult getWrapperResponse(Object data) {
        return WebResult.success(data);
    }

}
