
package my.harp07;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
        
        IOUtilmy.readWrite(
                new BufferedInputStream(tc.getInputStream()), 
                new BufferedOutputStream(tc.getOutputStream()),
                new BufferedInputStream(System.in), 
                new BufferedOutputStream(System.out));
        //StringUtils.
        IOUtils.toInputStream("rrr", Charset.defaultCharset());

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


