package org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.helper.ErrorHelper;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorField;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;


public interface MethodArgumentNotValidTranslator {
    /**
     * Transform the {@link MethodArgumentNotValidException} to {@link ErrorResponse}
     * This error is usually caused by validation rule on {@link RestController}.
     * Those validation rules are configured by {@link Valid} combined with {@link RequestBody}
     *
     * @param ex
     * @return
     */
    static ErrorResponse toErrorResponse(MethodArgumentNotValidException ex) {
        List<ErrorField> errorFields = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> toErrorField(error))
            .collect(Collectors.toList());
        ErrorResponse errorResponse = ErrorHelper.createErrorsResponse(errorFields);
        errorResponse.setErrorCode(ex.getClass().getSimpleName());
        return errorResponse;
    }

    /**
     * @param error the error of a specific field
     * @return
     */
    private static ErrorField toErrorField(FieldError error) {
        ErrorField errorField = ErrorField.builder()
            .errorConstraints(error.getCode())
            .fieldPath(error.getObjectName() + "." + error.getField())
            .detailsMessage(error.getDefaultMessage())
            .build();
        return errorField;
    }
}
