import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

public class ConsoleRedirector {
    private static PrintStream originalOut = System.out;
    private static PrintStream redirectedOut;

    public static void redirectOutput(JTextArea textArea) {
        redirectedOut = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                textArea.append(String.valueOf((char) b));
            }
        });

        System.setOut(redirectedOut);
    }

    public static void restoreOutput() {
        System.setOut(originalOut);
    }
}
