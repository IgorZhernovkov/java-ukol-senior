package com.etnetera.hr.utils;

import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;

import java.util.Random;

public class HypeLevelTestUtil {
    private  HypeLevelTestUtil() {}

    private static final Random random = new Random();

    public static HypeLevelBaseDto newInstanceHypeLevel() {
        int score = random.nextInt(100);
        return new HypeLevelBaseDto(score, "test_" + score);
    }

    public static boolean equals(HypeLevelBaseDto baseDtoA, HypeLevelBaseDto baseDtoB) {
        if ( baseDtoA == baseDtoB ) {
            return true;
        }

        if (baseDtoA == null ^ baseDtoB == null) {
            return false;
        }

        if ((baseDtoA.getName() == null ^ baseDtoB.getName() == null) ||!baseDtoA.getName().equals(baseDtoB.getName())) {
            return false;
        }

        if ((baseDtoA.getScore() == null ^ baseDtoB.getScore() == null) || !baseDtoA.getScore().equals(baseDtoB.getScore())) {
            return false;
        }
        return true;
    }

    public static HypeLevelBaseDto convert(HypeLevelDto fullDto) {
        if (fullDto == null) {
            return null;
        }

        return new HypeLevelBaseDto(fullDto.getScore(), fullDto.getName());
    }

}
