package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Quiz1 {

    private static final String TARGET = "좋아";
    private static final int TARGET_LENGTH = TARGET.length();

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                .flatMap(arr -> Arrays.stream(arr[1].split(":")))
                .map(String::trim)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();
        return csvLines.stream()
                .filter(arr -> arr[0].startsWith("정"))
                .flatMap(arr -> Arrays.stream(arr[1].split(":")))
                .map(String::trim)
                .collect(Collectors.groupingBy(Function.identity(),
                    Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
                .map(arr -> arr[2])
                .mapToInt(this::countLike)
                .sum();
    }

    private int countLike(String str) {
        if (str == null || str.isEmpty()) return 0;

        int count = 0;
        int from = 0;
        while (true) {
            int idx = str.indexOf(TARGET, from);
            if (idx == -1) break;
            count++;
            from = idx + TARGET_LENGTH;
        }
        return count;
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
