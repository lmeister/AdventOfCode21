package de.leonm.adventofcode21;

import de.leonm.adventofcode21.days.Day;

public class Main {

    public static void main(String[] args) {
        for (int i = 1; i <= 1; i++) {
            String dayNumberCode = String.format("%02d", i);
            System.out.println("-------- Day " + dayNumberCode + " --------");
            try {
                Day day =
                    (Day)
                        Class.forName("de.leonm.adventofcode21.days.Day" + dayNumberCode)
                            .getDeclaredConstructor()
                            .newInstance();
                day.printSolutions();
            } catch (ReflectiveOperationException roe) {
                roe.printStackTrace();
            }
        }
    }
}
