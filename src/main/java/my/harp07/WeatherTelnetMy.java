
package my.harp07;

import java.io.IOException;
import org.apache.commons.net.telnet.TelnetClient;

public final class WeatherTelnetMy {

    public static final void main(String[] args) {

        TelnetClient tc = new TelnetClient();
        try        {
            tc.connect("127.0.0.1", 23);
        }
        catch (IOException e)        {
            e.printStackTrace();
            System.exit(1);
        }
        IOUtilmy.readWrite(tc.getInputStream(), tc.getOutputStream(),
                         System.in, System.out);

        try        {
            tc.disconnect();
        }
        catch (IOException e)        {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

}


