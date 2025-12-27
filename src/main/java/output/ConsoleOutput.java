package output;

import utility.Utility;

public class ConsoleOutput implements SystemOutput {

    @Override
    public void println(String text) {
        System.out.println(text);
    }

    @Override
    public void pause() {
        Utility.enterToContinue();
    }
}
