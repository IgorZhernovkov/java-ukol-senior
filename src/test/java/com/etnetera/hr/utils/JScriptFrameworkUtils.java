package com.etnetera.hr.utils;

import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.data.dto.JScriptFrameworkBaseDto;
import com.etnetera.hr.data.dto.JScriptFrameworkDto;

import java.time.LocalDate;
import java.util.Random;

public class JScriptFrameworkUtils {
    private final static Random random = new Random();

    private JScriptFrameworkUtils() {}

    public static JScriptFrameworkBaseDto newInstanceJScriptFramework(Long hypeLevelId) {
        JScriptFrameworkBaseDto baseDto = new JScriptFrameworkBaseDto();
        int randomValue = random.nextInt(1000);
        baseDto.setName("NAME_" + randomValue);
        baseDto.setDeprecatedDate(LocalDate.now().minusMonths(randomValue));

        baseDto.setHypeLevelId(hypeLevelId);
        baseDto.setVersions( FrameworkVersionUtils.newInstanceVersions() );
        return baseDto;
    }

    public static boolean equals(JScriptFrameworkBaseDto baseDtoA, JScriptFrameworkBaseDto baseDtoB) {
        if ( baseDtoA == baseDtoB ) {
            return true;
        }

        if (baseDtoA == null ^ baseDtoB == null) {
            return false;
        }

        if (!baseDtoA.getName().equals(baseDtoB.getName())) {
            return false;
        }

        if (!baseDtoA.getDeprecatedDate().equals(baseDtoB.getDeprecatedDate())) {
            return false;
        }

        if (baseDtoA.getVersions() == null ^ baseDtoB.getVersions() == null) {
            return false;
        }

        if (!FrameworkVersionUtils.equals(baseDtoA.getVersions(), baseDtoB.getVersions())) {
            return false;
        }

        if (baseDtoA.getHypeLevelId() == null ^ baseDtoB.getHypeLevelId() == null) {
            return false;
        }

        if(!baseDtoA.getHypeLevelId().equals(baseDtoB.getHypeLevelId())) {
            return false;
        }
        return true;
    }

    public static JScriptFrameworkBaseDto convert(JScriptFrameworkDto fullDto) {
        return new JScriptFrameworkBaseDto(fullDto.getName(), fullDto.getVersions(), fullDto.getHypeLevelId(),
                fullDto.getDeprecatedDate());
    }
}
