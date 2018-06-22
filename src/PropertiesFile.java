import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesFile {
    public static void main(String[] args) {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("./config.properties");

            // set the properties value
            prop.setProperty("apiKey", "a4b2a5ed04bb6bee75ba4d989d175d22");
            prop.setProperty("sharedSecret","a1fe6dce56b3404c");


            prop.setProperty("PhotoSetID_bbc", "72157696612477184");
            prop.setProperty("PhotoSetID_cnn", "72157696619702534");
            prop.setProperty("PhotoSetID_foxnews", "72157667327393847");
            prop.setProperty("PhotoSetID_washingtonpost", "72157691533466980");
            prop.setProperty("PhotoSetID_reuters", "72157667327335747");
            prop.setProperty("PhotoSetID_huffingtonpost", "72157697221047725");
            prop.setProperty("PhotoSetID_newyorktimes", "72157694074569362");
            prop.setProperty("PhotoSetID_dw", "72157691437437660");
            prop.setProperty("PhotoSetID_guardian", "72157696619713414");
            prop.setProperty("PhotoSetID_usatoday", "72157669368025708");



            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}