package org.tnmk.practicejson.pro05globalerrorhandler.common.error.translator;


import org.tnmk.practicejson.pro05globalerrorhandler.common.error.helper.ErrorHelper;
import org.tnmk.practicejson.pro05globalerrorhandler.common.error.model.ErrorResponse;

public interface NullPointerTranslator {

    /**
     * @see ErrorHelper#getNullPointerExceptionRoot(Exception)
     */
    static ErrorResponse toErrorResponse(NullPointerException ex) {
        String message = String.join("", ex.getMessage(), ". ", ErrorHelper.getNullPointerExceptionRoot(ex));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ex.getClass().getSimpleName());
        errorResponse.setErrorMessage(message);
        return errorResponse;
    }
}
