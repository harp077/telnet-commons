package my.harp07;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.telnet.TelnetClient;

public class TelnetClientExampleMy implements Runnable {

    static TelnetClient tc = null;

    public static void main(String[] args) throws Exception {

        String remoteip = "10.73.250.43"
                + "";
        int remoteport = 23;
        tc = new TelnetClient("VT100");
        while (true) {
            boolean end_loop = false;
            try {
                tc.connect(remoteip, remoteport);
                Thread reader = new Thread(new TelnetClientExampleMy());
                reader.start();
                OutputStream outstr = tc.getOutputStream();
                byte[] buff = new byte[1024];
                int ret_read = 0;
                
                do {
                    try {
                        ret_read = System.in.read(buff);
                        if (new String(buff).equals("exit")) end_loop = true;
                        if (ret_read > 0) {
                            try {
                                outstr.write(buff, 0, ret_read);
                                outstr.flush();
                            } catch (IOException e) {
                                end_loop = true;
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Exception while reading keyboard:" + e.getMessage());
                        end_loop = true;
                    }
                } while ((ret_read > 0) && (end_loop == false));
                
                try {
                    tc.disconnect();
                } catch (IOException e) {
                    System.err.println("Exception while connecting:" + e.getMessage());
                }
            } catch (IOException e) {
                System.err.println("Exception while connecting:" + e.getMessage());
                System.exit(1);
            }
        }
    }

    /* Reader thread. Reads lines from the TelnetClient and echoes them on the
     * screen. */
    @Override
    public void run() {
        System.out.print("thread = " + Thread.currentThread().getName());
        InputStream instr = tc.getInputStream();
        try {
            byte[] buff = new byte[1024];
            int ret_read;
            do {
                ret_read = instr.read(buff);
                if (ret_read > 0) {
                    System.out.print(new String(buff, 0, ret_read));
                }
            } while (ret_read >= 0);
        } catch (IOException e) {
            System.err.println("Exception while reading socket:" + e.getMessage());
        }
        try {
            tc.disconnect();
        } catch (IOException e) {
            System.err.println("Exception while closing telnet:" + e.getMessage());
        }
    }

}
