package org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.helper.ErrorHelper;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorField;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Note: in the result of this translator, some fields are not set (deprecated).
 * View deprecated fields in {@link MethodArgumentNotValidTranslator}
 */
public interface ConstraintViolationJakartaTranslator {
    /**
     * Transform the {@link ConstraintViolationException} to {@link ErrorResponse}
     * This error is usually caused by validation rule on {@link RestController}.
     * Those validation rules are configured by {@link Validated} combined with
     * {@link RequestParam}, or {@link PathVariable}, or {@link RequestHeader}
     *
     * @param ex
     * @return
     */
    static ErrorResponse toErrorResponse(ConstraintViolationException ex) {
        List<ErrorField> errorFields = ex.getConstraintViolations().stream()
            .map(constraintViolation -> toErrorField(constraintViolation))
            .collect(Collectors.toList());
        ErrorResponse errorResponse = ErrorHelper.createErrorsResponse(errorFields);
        errorResponse.setErrorCode(ex.getClass().getSimpleName());
        errorResponse.setErrorMessage(ex.getMessage());
        return errorResponse;
    }

    /**
     * @param constraintViolation
     * @return
     */
    private static ErrorField toErrorField(ConstraintViolation<?> constraintViolation) {
        ErrorField errorField = ErrorField.builder()
            .errorConstraints(constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
            .fieldPath(String.valueOf(constraintViolation.getPropertyPath()))
            .detailsMessage(constraintViolation.getMessage())
            .build();
        return errorField;
    }
}
