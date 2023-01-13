package org.tnmk.practicejson.pro05globalerrorhandler.common.error.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.exception.BadRequestException;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.helper.ErrorHelper;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator.ConstraintViolationTranslator;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator.GeneralTranslator;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator.MethodArgumentMismatchTranslator;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator.MethodArgumentNotValidTranslator;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator.NullPointerTranslator;

import java.lang.invoke.MethodHandles;
import java.util.NoSuchElementException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * <b>Introduction:</b><br/>
 * This class provides a global ways to transform exceptions to {@link ErrorResponse}
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * View more at href="http://www.slf4j.org/faq.html#logging_performance
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler( {
        MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpClientErrorException(MethodArgumentTypeMismatchException ex) {
        ErrorResponse errors = MethodArgumentMismatchTranslator.toErrorResponse(ex);
        logError(errors, ex);
        return errors;
    }

    /**
     * Handles bad requests exceptions.
     * <p/>
     * Note: please don't consider {@link IllegalArgumentException} as BadRequest
     * because that problem could be caused by some unexpected internal logic.<br/>
     * For example: after receiving a string from some storage, you may want to convert it to a number, but it could cause
     * {@link NumberFormatException}
     * And that {@link NumberFormatException} extends from {@link IllegalArgumentException}.
     * That's an InternalError, not BadRequest.<p/>
     * It means that we <b>should not use {@link org.apache.commons.lang3.Validate}</b> because its methods just throw
     * {@link IllegalArgumentException}.<br/>
     * If you want to validate data, just use annotation in {@link javax.validation.constraints} such as {@link Max}, {@link NotBlank}.<br/>
     * Or you can just throws {@link BadRequestException} after some specific validation logic.<br/>
     * Please view more about different validation ways in `FakeErrorController` in testing.
     *
     * @param ex The exception that was thrown.
     * @return The error response.
     */
    @ExceptionHandler( {
        BadRequestException.class,
        HttpRequestMethodNotSupportedException.class,
        ServletRequestBindingException.class,
        TypeMismatchException.class,
        HttpMessageNotReadableException.class,
        HttpClientErrorException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpClientErrorException(Exception ex) {
        ErrorResponse errors = GeneralTranslator.toErrorResponse(ex);
        logError(errors, ex);
        return errors;
    }

    /**
     * @param ex The exception that was thrown.
     * @return The error response.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(ConstraintViolationException ex) {
        ErrorResponse errorResponse = ConstraintViolationTranslator.toErrorResponse(ex);
        logError(errorResponse, ex);
        return errorResponse;
    }

    /**
     * @param ex The exception that was thrown.
     * @return The error response.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = MethodArgumentNotValidTranslator.toErrorResponse(ex);
        logError(errorResponse, ex);
        return errorResponse;
    }

    @ExceptionHandler( {NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchElementException(Exception ex) {
        ErrorResponse errors = GeneralTranslator.toErrorResponse(ex);
        logError(errors, ex);
        return errors;
    }

    // TODO Enable AccessDeniedException handler after adding Spring Security
//    /**
//     * The name of {@link HttpStatus#UNAUTHORIZED} is misleading. Its actual meaning is Unauthenticated.
//     * https://stackoverflow.com/questions/3297048/403-forbidden-vs-401-unauthorized-http-responses
//     * For the real unauthorized, please use {@link HttpStatus#FORBIDDEN}.
//     *
//     * @param ex
//     * @return
//     */
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler( {AuthenticationException.class})
//    public ErrorResponse handleAccessDeniedException(AuthenticationException ex) {
//        ErrorResponse errors = GeneralTranslator.toErrorResponse(ex);
//        logError(errors, ex);
//        return errors;
//    }
//
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler( {AccessDeniedException.class})
//    public ErrorResponse handleAuthorizationException(AccessDeniedException ex) {
//        ErrorResponse errors = GeneralTranslator.toErrorResponse(ex);
//        logError(errors, ex);
//        return errors;
//    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNullPointerException(final NullPointerException ex) {
        ErrorResponse errors = NullPointerTranslator.toErrorResponse(ex);
        logError(errors, ex);
        return errors;
    }

    /**
     * Any unhandled exception will be considered as Internal error
     *
     * @param ex The exception that was thrown.
     * @return The error response.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleUnknownInternalException(Exception ex) {
        ErrorResponse errors = GeneralTranslator.toErrorResponse(ex);
        logError(errors, ex);
        return errors;
    }

    private void logError(ErrorResponse errorResponse, Throwable ex) {
        String errorMessage = String.join("",
            ex.getLocalizedMessage(), "\n ErrorResponse: ", ErrorHelper.toString(errorResponse));
        logger.error(errorMessage, ex);
    }
}
