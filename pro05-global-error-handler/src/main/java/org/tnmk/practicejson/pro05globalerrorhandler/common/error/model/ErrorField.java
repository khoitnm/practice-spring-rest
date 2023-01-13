package org.tnmk.practicejson.pro05globalerrorhandler.common.error.model;

import lombok.Builder;

@Builder
public class ErrorField {
    private String fieldPath;
    private Object fieldValue;
    private String errorConstraints;
    private String detailsMessage;
}
