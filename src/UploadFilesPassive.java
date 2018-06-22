import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UploadFilesPassive {
    public static void main(String[] args) {

        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();

        // load a properties file
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dir1="bbc";
        String dir2 = dir1 + "/"+"offline_content";
        Server.photoSetID = "";
        Server.photoSetID = prop.getProperty("PhotoSetID_"+dir1);
        if(Server.photoSetID.equals("")){
            System.out.println("No SET IDENTIFIED");
            return;
        }

        File folder = new File("/home/prg/Desktop/webpages/"+dir2);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> imagesPathList = new ArrayList<>();
        String htmlFilePath="";

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && (listOfFiles[i].getName().toLowerCase().endsWith("jpg") || listOfFiles[i].getName().toLowerCase().endsWith("jpeg") || listOfFiles[i].getName().toLowerCase().endsWith("png"))) {
                System.out.println("File " + listOfFiles[i].getName());
                imagesPathList.add(listOfFiles[i].getPath());
            }
            else if (listOfFiles[i].isFile() && (listOfFiles[i].getName().toLowerCase().endsWith("html"))) {
                System.out.println("File " + listOfFiles[i].getName());
                htmlFilePath = listOfFiles[i].getPath();
            }
            else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        File file = new File(htmlFilePath);
        byte[] htmlBytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(htmlBytes);

            Encoder.byte2Image(htmlBytes, htmlBytes.length, dir1+"-S");
            PostImage postImage = new PostImage();
            postImage.sendPostMulti("https://up.flickr.com/photos/upload/transfer/",imagesPathList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
