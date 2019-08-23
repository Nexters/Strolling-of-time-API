package com.nexters.wiw.api.common;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {
    public static ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
