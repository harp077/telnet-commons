package my.harp07;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.net.telnet.TelnetClient;

public class NewMain {

    public static void main(String[] args) {
        TelnetClient tc = new TelnetClient("VT100");
        tc.setCharset(Charset.defaultCharset());
        try {
            tc.connect("127.0.0.1", 23);
            System.out.println("tc.isConnected()= " + tc.isConnected());
            System.out.println(receive(tc));
            send(tc,"romka");
            System.out.println(receive(tc));
            send(tc,"romka");
            System.out.println(receive(tc));
            send(tc,"df -m");
            System.out.println(receive(tc));
            send(tc,"free -m");
            System.out.println(receive(tc));            
        } catch (IOException ex) {
            System.out.println("IOException = " + ex.getMessage());
        }

    }

    private static String receive(TelnetClient client) {
        StringBuffer strBuffer;
        try {
            strBuffer = new StringBuffer();
            byte[] buf = new byte[8192];
            int len = 0;
            Thread.sleep(999L);
            while ((len = client.getInputStream().read(buf)) != 0) {
                strBuffer.append(new String(buf, 0, len));
                Thread.sleep(999L);
                if (client.getInputStream().available() == 0) {
                    break;
                }
            }
            return strBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error receiving data = " + e.getMessage());
        }
        return "";
    }

    private static void send(TelnetClient client, String data) {
        try {
            data = data + "\r\n";
            client.getOutputStream().write(data.getBytes());
            client.getOutputStream().flush();
        } catch (IOException e) {
            System.out.println("Error sending data"+ e.getMessage());
        }
    }

}
