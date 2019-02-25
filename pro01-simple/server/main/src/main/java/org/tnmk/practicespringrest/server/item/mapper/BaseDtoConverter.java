package org.tnmk.practicespringrest.server.item.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <DTO> the type of Entity
 * @param <MODEL> the type fo Model
 */
public abstract class BaseDtoConverter<MODEL, DTO> {
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseDtoConverter.class);
    private final Class<DTO> dtoClass;
    private final Class<MODEL> modelClass;

    @SuppressWarnings("unchecked")
    public BaseDtoConverter() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        dtoClass = (Class<DTO>) types[0];
        modelClass = (Class<MODEL>) types[1];
    }

    public void toModel(DTO dto, MODEL model) {
        BeanUtils.copyProperties(dto, model);
    }

    public MODEL toModel(DTO dto) {
        if (dto == null) {
            return null;
        }
        try {
            MODEL target = modelClass.newInstance();
            toModel(dto, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MapperException(
                "Error when convert entity to model, cannot create new instance of class<"
                    + modelClass.getSimpleName() + ">, please check the default constructor of that class.", e);
        }
    }

    public DTO toDTO(MODEL model) {
        if (model == null) {
            return null;
        }
        try {
            DTO target = dtoClass.newInstance();
            BeanUtils.copyProperties(model, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MapperException(
                "Error when convert model to entity, cannot create new instance of class<"
                    + dtoClass.getSimpleName() + ">, please check the default constructor of that class.", e);
        }

    }

    public List<MODEL> toModels(Iterable<DTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<MODEL> result = new ArrayList<>();
        for (DTO entity : dtoList) {
            MODEL model = toModel(entity);
            result.add(model);
        }
        return result;
    }

    public List<DTO> toDTOs(List<MODEL> models) {
        if (models == null) {
            return null;
        }
        List<DTO> result = new ArrayList<>();
        for (MODEL model : models) {
            DTO entity = toDTO(model);
            result.add(entity);
        }
        return result;
    }
}
