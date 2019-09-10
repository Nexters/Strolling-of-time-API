package com.nexters.wiw.api.common;

import org.modelmapper.ModelMapper;

// TODO: GROUP 관련 DTO에서 static 함수를 쓰고 있어서 DI 불가능, 추후 config 방법과 util방법에 대해서 논의가 더 필요함ㅁ
public class ModelMapperUtil {
    public static ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
