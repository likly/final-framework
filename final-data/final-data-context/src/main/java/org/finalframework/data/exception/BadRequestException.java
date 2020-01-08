package org.finalframework.data.exception;

import org.finalframework.data.response.ResponseStatus;

/**
 * 错误的请求异常
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @see ForbiddenException
 * @see NotFoundException
 * @since 1.0
 */
public class BadRequestException extends ServiceException {

    public static final BadRequestException DEFAULT = new BadRequestException(ResponseStatus.BAD_REQUEST.getMessage());

    public BadRequestException(String message, Object... args) {
        this(ResponseStatus.BAD_REQUEST.getCode(), message, args);
    }

    public BadRequestException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public BadRequestException(Integer code, String message, Object... args) {
        super(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST.getMessage(), code, message, args);
    }

}
