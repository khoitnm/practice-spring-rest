package org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator;


import org.tnmk.practicejson.pro05globalerrorhandler.common.error.helper.ErrorHelper;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;

/**
 * This class is used to transform an exception to the {@link ErrorResponse} in a general way.<br/>
 * If you want to transform an exception in a more specific way,
 * please create another translator class for that specific Exception.
 */
public interface GeneralTranslator {
    static ErrorResponse toErrorResponse(Throwable ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getClass().getSimpleName());
        errorResponse.setErrorMessage(ex.getMessage());
        return errorResponse;
    }
}
