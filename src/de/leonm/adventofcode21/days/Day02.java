package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 extends Day {

    private final List<String> input;
    private final Pattern pattern = Pattern.compile("(?<direction>[a-z]+) (?<value>\\d)");
    private Matcher matcher;

    public Day02(String path) throws IOException {
        this.input = reader.getStringListFromFile(path);
    }

    @Override
    int partOne() {
        int horizontal = 0;
        int vertical = 0;

        for (String direction : input) {
            matcher = pattern.matcher(direction);
            if (matcher.find()) {
                switch (matcher.group("direction")) {
                    case "forward":
                        horizontal += Integer.parseInt(matcher.group("value"));
                        break;
                    case "up":
                        vertical -= Integer.parseInt(matcher.group("value"));
                        break;
                    case "down":
                        vertical += Integer.parseInt(matcher.group("value"));
                        break;
                    default:
                        break;
                }
            }
        }
        return horizontal * vertical;
    }

    @Override
    int partTwo() {
        int horizontal = 0;
        int vertical = 0;
        int aim = 0;

        for (String direction : input) {
            matcher = pattern.matcher(direction);
            if (matcher.find()) {
                switch (matcher.group("direction")) {
                    case "forward":
                        horizontal += Integer.parseInt(matcher.group("value"));
                        vertical += (aim * Integer.parseInt(matcher.group("value")));
                        break;
                    case "up":
                        aim -= Integer.parseInt(matcher.group("value"));
                        break;
                    case "down":
                        aim += Integer.parseInt(matcher.group("value"));
                        break;
                    default:
                        break;
                }
            }
        }
        return horizontal * vertical;
    }
}
