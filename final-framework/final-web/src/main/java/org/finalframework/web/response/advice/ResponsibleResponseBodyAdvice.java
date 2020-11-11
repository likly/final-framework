package org.finalframework.web.response.advice;

import org.finalframework.annotation.result.Responsible;
import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:33:39
 * @see Responsible
 * @since 1.0
 */
@Order
@RestControllerAdvice
@SpringFactory(ResponseBodyAdvice.class)
public class ResponsibleResponseBodyAdvice extends RestResponseBodyAdvice<Object> {

    /**
     * 是否同步Response状态
     */
    private boolean syncStatus;

    public boolean isSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(boolean syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (syncStatus && body instanceof Responsible) {
            final HttpStatus httpStatus = HttpStatus.resolve(((Responsible) body).getStatus());
            if (httpStatus != null) {
                response.setStatusCode(httpStatus);
            }
        }

        return body;
    }
}