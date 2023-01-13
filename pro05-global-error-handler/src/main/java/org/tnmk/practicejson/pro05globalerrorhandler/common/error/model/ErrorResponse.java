package org.tnmk.practicejson.pro05globalerrorhandler.common.error.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private final List<ErrorField> errorFields = new ArrayList<>();

    public void addErrorField(ErrorField errorField) {
        this.errorFields.add(errorField);
    }

    public void addAllErrorFields(List<ErrorField> errorFields) {
        this.errorFields.addAll(errorFields);
    }
}
