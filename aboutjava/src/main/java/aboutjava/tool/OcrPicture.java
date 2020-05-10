package aboutjava.tool;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-05-10 16:44
 **/
public class OcrPicture {

    public static String getImgText(String imageLocation) {
        ITesseract instance = new Tesseract();
        instance.setDatapath("/Users/hupengfei/tesseract/tessdata");
        try
        {
            String imgText = instance.doOCR(new File(imageLocation));
            return imgText;
        }
        catch (TesseractException e)
        {
            e.getMessage();
            return "Error while reading image";
        }
    }

    public static void main(String[] args) {

        System.out.println(getImgText("/Users/hupengfei/Downloads/1111.jpg"));
    }

}
