package org.ifinal.finalframework.web.exception.result;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.ifinal.finalframework.context.exception.result.ResultExceptionHandler;
import org.ifinal.finalframework.core.result.R;
import org.ifinal.finalframework.core.result.Result;

/**
 * 将 {@link MissingServletRequestParameterException}异常转化为{@link Result}结果，自定义描述语。
 *
 * @author likly
 * @version 1.0.0
 * @see MissingServletRequestParameterException
 * @since 1.0.0
 */
@Component
public class MissingServletParameterResultExceptionHandler implements
    ResultExceptionHandler<MissingServletRequestParameterException> {

    @Override
    public boolean supports(final Throwable throwable) {

        return throwable instanceof MissingServletRequestParameterException;
    }

    @Override
    public Result<?> handle(final MissingServletRequestParameterException throwable) {

        MissingServletRequestParameterException e = throwable;
        return R.failure(400, e.getMessage());
    }

}
