package my.harp07;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.io.Util;

public final class IOUtilmy {

    public static void readWrite(
             InputStream farInput, 
            OutputStream farOutput,
             InputStream tutInput, 
            OutputStream tutOutput) {
        
        Thread tutInput_to_farOutput,
               farInput_to_tutOutput;
        
        // write may be only for output
        // read  may be only for input

        tutInput_to_farOutput = new Thread() {
            @Override
            public void run() {
                int ch;
                try {
                    while (!interrupted() && (ch = tutInput.read()) != -1) {
                        farOutput.write(ch);
                        farOutput.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        farInput_to_tutOutput = new Thread() {
            @Override
            public void run() {
                try {
                    Util.copyStream(farInput, tutOutput);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        };

        farInput_to_tutOutput.setPriority(Thread.currentThread().getPriority() + 1);
        farInput_to_tutOutput.start();
        tutInput_to_farOutput.setDaemon(true);
        tutInput_to_farOutput.start();
        try {
            farInput_to_tutOutput.join();
            tutInput_to_farOutput.interrupt();
        } catch (InterruptedException e) {
            // Ignored
        }
    }

}
