import java.io.*;
import java.net.URL;

import org.jsoup.Jsoup;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;


public class HtmlParser {
    public static void main(String[] args) {

        String webPage = "http://www.bbc.com/";
        String fileName = webPage.split("/")[2];

        String html = null;
        try {
            html = Jsoup.connect(webPage).get().html();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File input = new File("/home/prg/Desktop/webpages/"+fileName+".html");
        try (PrintWriter out = new PrintWriter(input)) {
            out.println(html);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(webPage.toLowerCase().contains("bbc")){
            File destinationDirectory = new File("/home/prg/Desktop/webpages/bbc/offline_content/");
            if (!destinationDirectory.exists()) {
                if (destinationDirectory.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
            //File input = new File("/home/prg/Desktop/webpages/"+ "bbc_Jsoup.html");
            try {
                //Document doc = Jsoup.parse(input, "UTF-8", webPage);
                //Document doc = Jsoup.parse(new URL(webPage).openStream(), "UTF-8", webPage);

                Document doc = Jsoup.parse(html);
                //Elements jpgs = doc.select("div[data-src$=.jpg]");

                Elements imgs = doc.getElementsByAttribute("data-src");
                for(Element jpg:imgs){
                    System.out.println(jpg);
                    String imgSrc = jpg.getElementsByTag("img").attr("src");
                    String imageDataSrcVal ="";

                    if(imgSrc.contains("jpeg") || imgSrc.contains("jpg") || imgSrc.contains("png")){
                        imageDataSrcVal =imgSrc;
                    }
                    else {
                        String dataSrc = jpg.getElementsByTag("div").attr("data-src");

                        if (imgSrc.equals(dataSrc) == false) {
                            String width = "\\{width\\}";
                            String tmpImgUrl = jpg.attr("data-src").replaceFirst(width, "720");
                            if (SaveImageFromUrl.isUrlValid(tmpImgUrl) == false){
                                tmpImgUrl = jpg.attr("data-src").replaceFirst(width, "360");
                            }
                            if (SaveImageFromUrl.isUrlValid(tmpImgUrl) == false){
                                tmpImgUrl = jpg.attr("data-src").replaceFirst(width, "240");
                            }
                            if (SaveImageFromUrl.isUrlValid(tmpImgUrl) == false){
                                tmpImgUrl = jpg.attr("data-src").replaceFirst(width, "144");
                            }
                            if (SaveImageFromUrl.isUrlValid(tmpImgUrl) == true){
                                imageDataSrcVal = tmpImgUrl;
                            }
                            else {
                                imageDataSrcVal = "";
                            }
                            //imageDataSrcVal = jpg.attr("data-src").replaceFirst(width, "144");
                        }
                    }

                    int index = imageDataSrcVal.lastIndexOf("/");
                    if(index < 0){
                        continue;
                    }

                    String destUrl = imageDataSrcVal.substring(index,imageDataSrcVal.length());
                    String good = "."+destUrl;
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);
                    SaveImageFromUrl.saveImage(imageDataSrcVal,destinationDirectory+destUrl);
                }

                html = doc.toString();
                try (PrintWriter out = new PrintWriter(destinationDirectory+"/"+"bbc_Jsoup_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(webPage.toLowerCase().contains("dw")){
            try {
                //Document doc = Jsoup.parse(input, "UTF-8", webPage);
                Document doc = Jsoup.parse(html);
                File destinationDirectory = new File("/home/prg/Desktop/webpages/dw/offline_content/");
                if (!destinationDirectory.exists()) {
                    if (destinationDirectory.mkdir()) {
                        System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }

                Elements jpgs = doc.select("img[src$=.jpg]");
                for(Element jpg:jpgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");
                    int index = imageDataSrcVal.lastIndexOf("/");
                    if(index < 0){
                        continue;
                    }

                    String destUrl = imageDataSrcVal.substring(index,imageDataSrcVal.length());
                    String good = "."+destUrl;
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);
                    if(imageDataSrcVal.contains("http://") || imageDataSrcVal.contains("https://")){
                        SaveImageFromUrl.saveImage(imageDataSrcVal,destinationDirectory+destUrl);
                    }
                    else {
                        SaveImageFromUrl.saveImage(webPage+imageDataSrcVal,destinationDirectory+destUrl);
                    }

                }
                html = doc.toString();
                try (PrintWriter out = new PrintWriter(destinationDirectory+"/"+"dw_Jsoup_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(webPage.toLowerCase().contains("nytimes") || webPage.toLowerCase().contains("newyorktimes")){
            try {
                //Document doc = Jsoup.parse(input, "ISO-8859-1", webPage);
                //Document doc = Jsoup.parse(new URL(webPage).openStream(), "ISO-8859-1", webPage);
                /*Document doc = Jsoup.parse(html);
                Elements jpgs = doc.select("img[src$=.jpg]");
                for(Element jpg:jpgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");
                    int index = imageDataSrcVal.lastIndexOf("/");
                    if(index < 0){
                        continue;
                    }

                    String good = "."+imageDataSrcVal.substring(index,imageDataSrcVal.length());
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);

                    int s=0;
                }
                */
                Document doc = Jsoup.parse(html);
                File destinationDirectory = new File("/home/prg/Desktop/webpages/newyorktimes/offline_content/");
                if (!destinationDirectory.exists()) {
                    if (destinationDirectory.mkdir()) {
                        System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }

                Elements jpgs = doc.select("img[src$=.jpg]");
                for(Element jpg:jpgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");
                    int index = imageDataSrcVal.lastIndexOf("/");
                    if(index < 0){
                        continue;
                    }

                    String destUrl = imageDataSrcVal.substring(index,imageDataSrcVal.length());
                    String good = "."+destUrl;
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);
                    if(imageDataSrcVal.contains("http://") || imageDataSrcVal.contains("https://")){
                        SaveImageFromUrl.saveImage(imageDataSrcVal,destinationDirectory+destUrl);
                    }
                    else {
                        SaveImageFromUrl.saveImage(webPage+imageDataSrcVal,destinationDirectory+destUrl);
                    }

                }
                html = doc.toString();
                try (PrintWriter out = new PrintWriter(destinationDirectory+ "/"+fileName+"_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(webPage.toLowerCase().contains("guardian")){ //not good
            try {
                //Document doc = Jsoup.parse(input, "ISO-8859-1", webPage);
                //Document doc = Jsoup.parse(new URL(webPage).openStream(), "ISO-8859-1", webPage);
                Document doc = Jsoup.parse(html);
                File destinationDirectory = new File("/home/prg/Desktop/webpages/guardian/offline_content/");
                if (!destinationDirectory.exists()) {
                    if (destinationDirectory.mkdir()) {
                        System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }

                Elements jpgs = doc.select("img[src$=.jpg]");
                for(Element jpg:jpgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");
                    int index = imageDataSrcVal.lastIndexOf("/");
                    if(index < 0){
                        continue;
                    }

                    String destUrl = imageDataSrcVal.substring(index,imageDataSrcVal.length());
                    String good = "."+destUrl;
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);
                    if(imageDataSrcVal.contains("http://") || imageDataSrcVal.contains("https://")){
                        SaveImageFromUrl.saveImage(imageDataSrcVal,destinationDirectory+destUrl);
                    }
                    else {
                        SaveImageFromUrl.saveImage(webPage+imageDataSrcVal,destinationDirectory+destUrl);
                    }

                }
                html = doc.toString();
                try (PrintWriter out = new PrintWriter(destinationDirectory+ "/"+fileName+"_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(webPage.toLowerCase().contains("washingtonpost")){ //not good
            try {
                //Document doc = Jsoup.parse(input, "ISO-8859-1", webPage);
                //Document doc = Jsoup.parse(new URL(webPage).openStream(), "ISO-8859-1", webPage);
                Document doc = Jsoup.parse(html);
                Elements jpgs = doc.select("img[src$=.jpg]");
                for(Element jpg:jpgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");
                    int index = imageDataSrcVal.lastIndexOf("/");
                    if(index < 0){
                        continue;
                    }

                    String good = "."+imageDataSrcVal.substring(index,imageDataSrcVal.length());
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);

                    int s=0;
                }
                html = doc.toString();
                try (PrintWriter out = new PrintWriter("/home/prg/Desktop/webpages/"+ fileName+"_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(webPage.toLowerCase().contains("foxnews")){ //new thing used, getElementbyTAg
            try {
                //Document doc = Jsoup.parse(input, "ISO-8859-1", webPage);
                //Document doc = Jsoup.parse(new URL(webPage).openStream(), "ISO-8859-1", webPage);
                Document doc = Jsoup.parse(html);
                Elements jpgs = doc.select("img[src$=.jpg]");
                Elements imgs = doc.getElementsByTag("img");
                for(Element jpg:imgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");
                    int index = imageDataSrcVal.lastIndexOf("/");
                    if(index < 0){
                        continue;
                    }

                    String good = "."+imageDataSrcVal.substring(index,imageDataSrcVal.length());
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);

                    int s=0;
                }
                html = doc.toString();
                try (PrintWriter out = new PrintWriter("/home/prg/Desktop/webpages/"+ fileName+"_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(webPage.toLowerCase().contains("https://www.huffingtonpost.com/")){
            try {
                //Document doc = Jsoup.parse(input, "ISO-8859-1", webPage);
                //Document doc = Jsoup.parse(new URL(webPage).openStream(), "ISO-8859-1", webPage);

                Document doc = Jsoup.parse(html);
                File destinationDirectory = new File("/home/prg/Desktop/webpages/huffingtonpost/offline_content/");
                if (!destinationDirectory.exists()) {
                    if (destinationDirectory.mkdir()) {
                        System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }

                Elements imgs = doc.getElementsByTag("img");
                for(Element jpg:imgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");

                    int index = imageDataSrcVal.indexOf("?");
                    int index2 = imageDataSrcVal.lastIndexOf("/", index);

                    if(index*index2 <= 1){  //both are -1 or one of them
                        int index3 = imageDataSrcVal.lastIndexOf("/");
                        if(index3 > 0) {
                            String destUrl = imageDataSrcVal.substring(index3,imageDataSrcVal.length());
                            String good = "." + destUrl;
                            Element p1 = jpg.select("img[src]").first();
                            p1.attr("src", good);
                            if (imageDataSrcVal.contains("http://") || imageDataSrcVal.contains("https://")) {
                                SaveImageFromUrl.saveImage(imageDataSrcVal, destinationDirectory + destUrl);
                            } else {
                                SaveImageFromUrl.saveImage(webPage + imageDataSrcVal, destinationDirectory + destUrl);
                            }
                        }
                        continue;
                    }
                    //String tmp = imageDataSrcVal.substring(index2,index);

                    String destUrl = imageDataSrcVal.substring(index2,index);
                    String good = "."+destUrl;
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);
                    if(imageDataSrcVal.contains("http://") || imageDataSrcVal.contains("https://")){
                        SaveImageFromUrl.saveImage(imageDataSrcVal,destinationDirectory+destUrl);
                    }
                    else {
                        SaveImageFromUrl.saveImage(webPage+imageDataSrcVal,destinationDirectory+destUrl);
                    }

                }


                /*Document doc = Jsoup.parse(html);
                Elements jpgs = doc.select("img[src$=.jpg]");
                Elements imgs = doc.getElementsByTag("img");
                for(Element jpg:imgs){
                    System.out.println(jpg);
                    String imageDataSrcVal =jpg.attr("src");
                    String suffix = ".jpeg";
                    int index = imageDataSrcVal.indexOf("?");
                    int index2 = imageDataSrcVal.lastIndexOf("/", index);
                    if(index*index2 <= 1){  //both are -1 or one of them
                        continue;
                    }
                    //String tmp = imageDataSrcVal.substring(index2,index);


                    String good = "."+imageDataSrcVal.substring(index2,index);
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);

                    int s=0;
                }*/
                html = doc.toString();
                try (PrintWriter out = new PrintWriter(destinationDirectory+"/"+fileName+"_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(webPage.toLowerCase().contains("usatoday")) { //new thing used, getElementbyTAg
            try {
                //Document doc = Jsoup.parse(input, "ISO-8859-1", webPage);
                //Document doc = Jsoup.parse(new URL(webPage).openStream(), "ISO-8859-1", webPage);
                Document doc = Jsoup.parse(html);
                Elements jpgs = doc.select("img[src$=.jpg]");
                Elements imgs = doc.getElementsByTag("img");
                for (Element jpg : imgs) {
                    System.out.println(jpg);
                    String imageDataSrcVal = jpg.attr("src");
                    int index = imageDataSrcVal.lastIndexOf("/");
                    if (index < 0) {
                        continue;
                    }

                    String good = "." + imageDataSrcVal.substring(index, imageDataSrcVal.length());
                    Element p1 = jpg.select("img[src]").first();
                    p1.attr("src", good);

                    int s = 0;
                }
                html = doc.toString();
                try (PrintWriter out = new PrintWriter("/home/prg/Desktop/webpages/" + fileName + "_modified.html")) {
                    out.println(html);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            else if(webPage.toLowerCase().contains("reuters")){ //new thing used, getElementbyTAg
                try {
                    //Document doc = Jsoup.parse(input, "ISO-8859-1", webPage);
                    //Document doc = Jsoup.parse(new URL(webPage).openStream(), "ISO-8859-1", webPage);
                    Document doc = Jsoup.parse(html);
                    Elements jpgs = doc.select("img[src$=.jpg]");
                    Elements imgs = doc.getElementsByTag("img");
                    for(Element jpg:imgs){
                        System.out.println(jpg);
                        String imageDataSrcVal =jpg.attr("src");
                        int index = imageDataSrcVal.lastIndexOf("/");
                        if(index < 0){
                            continue;
                        }

                        String good = "."+imageDataSrcVal.substring(index,imageDataSrcVal.length());
                        Element p1 = jpg.select("img[src]").first();
                        p1.attr("src", good);

                        int s=0;
                    }
                    html = doc.toString();
                    try (PrintWriter out = new PrintWriter("/home/prg/Desktop/webpages/"+ fileName+"_modified.html")) {
                        out.println(html);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }



    }
}
