package org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorField;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.validation.Valid;

public interface MethodArgumentMismatchTranslator {
    /**
     * Transform the {@link MethodArgumentNotValidException} to {@link ErrorResponse}
     * This error is usually caused by validation rule on {@link RestController}.
     * Those validation rules are configured by {@link Valid} combined with {@link RequestBody}
     *
     * @param ex
     * @return
     */
    static ErrorResponse toErrorResponse(MethodArgumentTypeMismatchException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        String classAndMethod = prettyFormatMethodWithArguments(ex.getParameter().getMethod());
        errorResponse.setErrorMessage("Error at method: " + classAndMethod);
        errorResponse.addErrorField(toErrorField(ex));
        return errorResponse;
    }

    private static ErrorField toErrorField(MethodArgumentTypeMismatchException ex) {
        String argumentThatGotError = ex.getName();
        ErrorField errorField = ErrorField.builder()
            .fieldPath(argumentThatGotError)
            .errorConstraints(ex.getErrorCode())
            .detailsMessage(ex.getMessage())
            .build();
        return errorField;
    }

    private static String prettyFormatMethodWithArguments(Method method) {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] paramTypes = method.getParameterTypes();
        String paramTypesAsString = Arrays.stream(paramTypes).map(paramType -> paramType.getSimpleName()).collect(Collectors.joining(", "));
        return String.format("%s.%s(%s)", className, methodName, paramTypesAsString);
    }
}
