package scanpdf;

import java.io.*;
import net.sourceforge.tess4j.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import org.icepdf.core.exceptions.*;
import org.icepdf.core.pobjects.*;
import org.icepdf.core.util.GraphicsRenderingHints;

public class ScanPDF {
private static final String FILEPATH = "Tata Power Sample Electricity Bill.pdf";
    public static void main(String[] args) {
        Document document = new Document();
        try {
           document.setFile(FILEPATH);
        } catch (PDFException ex) {
           System.out.println("Error parsing PDF document " + ex);
        } catch (PDFSecurityException ex) {
           System.out.println("Error encryption not supported " + ex);
        } catch (FileNotFoundException ex) {
           System.out.println("Error file not found " + ex);
        } catch (IOException ex) {
           System.out.println("Error IOException " + ex);
        }
        float scale = 9.6f;
        float rotation = 0f;
        for (int i = 0; i < document.getNumberOfPages(); i++) {
             BufferedImage image = (BufferedImage) document.getPageImage(
                 i, GraphicsRenderingHints.PRINT, Page.BOUNDARY_CROPBOX, rotation, scale);
             RenderedImage rendImage = image;
             try {
                System.out.println(" capturing page " + i);
                File file = new File("imageCapture1_" + i + ".tif");
                ImageIO.write(rendImage, "tiff", file);
                
                File imageFile = new File("imageCapture1_0.tif");
                ITesseract instance = new Tesseract();
                instance.setDatapath("/usr/local/share/tessdata");
                try {
                String result = instance.doOCR(imageFile);

                File output = new File("SampleText.txt");
                FileWriter fw = new FileWriter(output);
                fw.write(result);
                fw.close();
                } catch (TesseractException e) {
                System.err.println(e.getMessage());
                }
                
             } catch (IOException e) {
                e.printStackTrace();
             }
             image.flush();
          }
          document.dispose();
}
}