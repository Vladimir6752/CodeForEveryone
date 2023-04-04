package dev.vladimir.cfemain.user.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ArrayJsonConverter implements AttributeConverter<List<Integer>, String> {
    private final ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbJson) {
        try {
            return mapper.readValue(dbJson, new TypeReference<>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}