package com.netcracker.adlitsov.newsproject.authserver.converter;

import com.netcracker.adlitsov.newsproject.authserver.model.Profile;
import org.springframework.security.core.parameters.P;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Profile.Gender, String> {

    @Override
    public String convertToDatabaseColumn(Profile.Gender attribute) {
        if (attribute == null) {
            return null;
        }
        switch (attribute) {
            case MALE:
                return "Мужской";
            case FEMALE:
                return "Женский";
            default:
                throw new IllegalArgumentException("Unknown gender enum constant: " + attribute);
        }
    }

    @Override
    public Profile.Gender convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "Мужской":
                return Profile.Gender.MALE;
            case "Женский":
                return Profile.Gender.FEMALE;
            default:
                throw new IllegalArgumentException("Unknown gender db string: " + dbData);
        }
    }
}
