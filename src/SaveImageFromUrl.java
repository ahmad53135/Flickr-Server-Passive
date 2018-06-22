import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SaveImageFromUrl {

    public static void main(String[] args) throws Exception {
        String imageUrl = "https://www.gannett-cdn.com/-mm-/1341f6bfa8d82bec402276f7a67188978233aeb2/c=13-0-290-277&r=x63&c=60x60/local/-/media/2018/05/16/USATODAY/USATODAY/636621020806143823-Capture.JPG";
        String destinationFile = "image.jpg";

        saveImage(imageUrl, destinationFile);
        saveImage("http://lafllsf.com/",destinationFile);
    }
    public static boolean isUrlValid(String imageUrl) throws MalformedURLException {
        URL url = new URL(imageUrl);
        InputStream is = null;
        try {
            is = url.openStream();
            return true;
        }
        catch (Exception e){
            System.out.println("URL: "+imageUrl+ " is not Valid");
            return false;
        }
    }
    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = null;
        try {
            is = url.openStream();
        }
        catch (Exception e){
            System.out.println("URL: "+imageUrl+ " is not Valid");
            return;
        }
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

}
