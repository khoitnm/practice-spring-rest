package org.tnmk.practicejson.pro05globalerrorhandler.common.error.helper;

import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorField;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;


public interface ErrorHelper {


    /**
     * This method will return detail information of the line which cause NullPointerException including:
     * class name, method name, line number
     *
     * @param exception
     * @return
     */
    static String getNullPointerExceptionRoot(Exception exception) {
        return getErrorLine(exception, 0);
    }

    private static String getErrorLine(Exception exception, int stackTraceIndex) {
        StackTraceElement first = exception.getStackTrace()[stackTraceIndex];
        String fileName = first.getFileName();
        String methodName = first.getMethodName();
        int lineNumber = first.getLineNumber();
        String errorRootCause = String.format("%s#%s():%s", fileName, methodName, lineNumber);
        return "Root: " + errorRootCause;
    }

    /**
     * We don't use {@link ErrorResponse#toString()} because that method has a minor issue that makes it doesn't display all details value.
     * So we use this method to make sure all details are loged.
     */
    static String toString(ErrorResponse errorResponse) {
        String message = errorResponse.getErrorFields().stream()
            .map(ErrorField -> toString(ErrorField))
            .collect(Collectors.joining(", "));
        return message;
    }

    private static String toString(ErrorField errorField) {
        if (errorField == null) {
            return null;
        }
        return errorField.toString();
    }

    static ErrorResponse createErrorsResponse(List<ErrorField> errorFields) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addAllErrorFields(errorFields);
        return errorResponse;
    }
}
