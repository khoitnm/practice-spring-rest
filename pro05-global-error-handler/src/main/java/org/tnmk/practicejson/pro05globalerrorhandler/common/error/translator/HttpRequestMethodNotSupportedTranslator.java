package org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;

public interface HttpRequestMethodNotSupportedTranslator {
    static ErrorResponse toErrorResponse(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getClass().getSimpleName());
        errorResponse.setErrorMessage(ex.getMessage());
        return errorResponse;
    }
}
