package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.NumberGrid;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Day13 extends Day {

    private final List<String> foldingInstructions;
    Pattern foldingPattern = Pattern.compile("fold along (?<axis>[xy])=(?<value>(\\d)+)");
    List<Point> pointList = new ArrayList<>();

    public Day13(String path) throws IOException {
        List<String> input = reader.getStringListFromFile(path);

        for (String line : reader.getStringListFromFile(path)) {
            if (line.isBlank() || line.isEmpty()) {
                break;
            }
            int x = Integer.parseInt(line.substring(0, line.indexOf(',')));
            int y = Integer.parseInt(line.substring(line.indexOf(',') + 1));
            pointList.add(new Point(x, y));
        }

        this.foldingInstructions = new ArrayList<>();
        for (int i = pointList.size() + 1; i < input.size(); i++) {
            foldingInstructions.add(input.get(i));
        }
    }


    @Override
    public String partOne() {
        NumberGrid copy = new NumberGrid(pointList, 1L);
        foldGrid(copy, foldingInstructions.get(0));
        return String.valueOf(copy.countOccurrences(1L));
    }


    @Override
    public String partTwo() {
        NumberGrid copy = new NumberGrid(pointList, 1L);
        for (String foldingInstruction : foldingInstructions) {
            foldGrid(copy, foldingInstruction);
        }

        List<Point> remainingPoints = copy.getPointStream().filter(point -> (copy.getValueAt(point).isPresent() && copy.getValueAt(point).get() > 0)).collect(Collectors.toList());
        NumberGrid printableCopy = new NumberGrid(new ArrayList<>(remainingPoints), 1L);
        printableCopy.transposeGrid();
        return "\n" + printableCopy.toString().replace('0', ' ').replace('1', '#');
    }

    private void foldGrid(NumberGrid copy, String foldingInstruction) {
        Matcher matcher = foldingPattern.matcher(foldingInstruction);
        if (matcher.find()) {
            int value = Integer.parseInt(matcher.group("value"));
            if (matcher.group("axis").equals("x")) {
                copy.getPointStream()
                        .filter(point -> point.x > (value - 1) && (copy.getValueAt(point).isPresent() && copy.getValueAt(point).get() > 0))
                        .forEach(movingPoint -> {
                            copy.setValueAt(new Point((2 * value) - movingPoint.x, movingPoint.y), 1L);
                            copy.setValueAt(new Point(movingPoint.x, movingPoint.y), 0L);
                        });
            } else {
                copy.getPointStream()
                        .filter(point -> point.y > (value - 1) && (copy.getValueAt(point).isPresent() && copy.getValueAt(point).get() > 0))
                        .forEach(movingPoint -> {
                            copy.setValueAt(new Point(movingPoint.x, (2 * value) - movingPoint.y), 1L);
                            copy.setValueAt(new Point(movingPoint.x, movingPoint.y), 0L);
                        });
            }
        }
    }

}
