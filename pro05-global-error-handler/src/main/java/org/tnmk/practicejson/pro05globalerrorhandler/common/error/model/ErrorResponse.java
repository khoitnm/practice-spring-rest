package org.tnmk.practicejson.pro05globalerrorhandler.common.error.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private final List<ErrorField> errorFields = new ArrayList<>();

    public void addErrorField(ErrorField errorField) {
        errorFields.add(errorField);
    }

    public void addAllErrorFields(List<ErrorField> errorFields) {
        errorFields.addAll(errorFields);
    }
}
