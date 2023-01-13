package org.tnmk.practicejson.pro05globalerrorhandler.common.error.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * Most of the time, we only need to use {@link Valid} on a Controller to validate data.<br/>
 * However, in some cases, this class can help us to validate an object in other layers (service layer, for instance).<br/>
 * Please view an example how to use this class in FakeErrorController.
 */
@Component
@RequiredArgsConstructor
public class BeanValidator {

    private final Validator validator;

    /**
     * @param bean just any object that use validation constraint annotations such as {@link NotBlank}, {@link Max}
     * @param <T>
     * @throws ConstraintViolationException if there are constraint violation.
     */
    public <T> void validate(T bean) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(bean);
        if (!constraintViolationSet.isEmpty()) {
            throw new ConstraintViolationException(constraintViolationSet);
        }
    }
}
