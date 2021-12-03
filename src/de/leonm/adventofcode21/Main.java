package de.leonm.adventofcode21;

import de.leonm.adventofcode21.days.Day;

public class Main {

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            String dayNumberCode = String.format("%02d", i);
            System.out.println("-------- Day " + dayNumberCode + " --------");
            try {
                Class[] cArg = {String.class};
                String[] path = {"src/de/leonm/adventofcode21/inputs/Day" + dayNumberCode +".txt"};
                Day day =
                    (Day)
                        Class.forName("de.leonm.adventofcode21.days.Day" + dayNumberCode)
                            .getDeclaredConstructor(cArg)
                            .newInstance(path);
                day.printSolutions();
            } catch (ReflectiveOperationException roe) {
                roe.printStackTrace();
            }
        }
    }
}
