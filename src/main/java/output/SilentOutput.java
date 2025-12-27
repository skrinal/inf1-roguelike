package output;

public class SilentOutput implements SystemOutput {

    @Override
    public void println(String text) {
        // Nothing so Test class can test
    }

    @Override
    public void pause() {
        // Nothing so Test class can test
    }
}
