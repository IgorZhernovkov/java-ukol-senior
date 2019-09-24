package com.etnetera.hr.utils;

import com.etnetera.hr.data.dto.FrameworkVersionBaseDto;
import com.etnetera.hr.data.dto.FrameworkVersionDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class FrameworkVersionUtils {

    private static Random random = new Random();

    private FrameworkVersionUtils() {}

    public static List<FrameworkVersionDto> newInstanceVersions() {
        int c = random.nextInt(10) + 1;
        List<FrameworkVersionDto> versions = new ArrayList<>(c);
        LocalDate releaseDate;
        for(int i = 0; i < c; i++) {
            releaseDate = LocalDate.now().minusYears(i);
            versions.add( new FrameworkVersionDto("V" + i, releaseDate) );
        }
        return versions;
    }

    public static FrameworkVersionDto newInstanceVersion() {
        int c = random.nextInt(10);
        LocalDate releaseDate = LocalDate.now().minusYears(c);
        return new FrameworkVersionDto("V" + c, releaseDate);
    }

    public static boolean equals(FrameworkVersionBaseDto baseDtoA, FrameworkVersionBaseDto baseDtoB) {
        if ( baseDtoA == baseDtoB ) {
            return true;
        }

        if (baseDtoA == null ^ baseDtoB == null) {
            return false;
        }

        if ((baseDtoA.getVersion() == null ^ baseDtoB.getVersion() == null) || !baseDtoA.getVersion().equals(baseDtoB.getVersion())) {
            return false;
        }

        if ((baseDtoA.getReleaseDate() == null ^ baseDtoA.getReleaseDate() == null) || !baseDtoA.getReleaseDate().equals(baseDtoB.getReleaseDate())) {
            return false;
        }
        return true;
    }

    public static boolean equals(List<FrameworkVersionDto> versionDtos, FrameworkVersionDto baseDtoB) {
        if (versionDtos == null || versionDtos.isEmpty() || baseDtoB == null) {
            return false;
        }

        for (FrameworkVersionDto baseDto : versionDtos) {
            if (equals(baseDto, baseDtoB)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equals(FrameworkVersionDto dtoA, FrameworkVersionDto dtoB) {

        if (dtoA.getId() != null && !dtoA.getId().equals(dtoB.getId())) {
            return false;
        }
        return equals((FrameworkVersionBaseDto) dtoA, (FrameworkVersionBaseDto) dtoB);
    }

    public static boolean equals(List<FrameworkVersionDto> listA, List<FrameworkVersionDto> listB) {
        if (listA == null ^ listB == null) {
            return false;
        }

        if (listA.size() != listB.size()) {
            return false;
        }

        listA.sort(new VersionComparator());
        listB.sort(new VersionComparator());

        for(int i = 0; i < listA.size(); i++ ) {
            if (!equals(listA.get(i), listB.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static class VersionComparator implements Comparator<FrameworkVersionBaseDto> {

        @Override
        public int compare(FrameworkVersionBaseDto o1, FrameworkVersionBaseDto o2) {
            return o1.getVersion().compareTo(o2.getVersion());
        }
    }
}
