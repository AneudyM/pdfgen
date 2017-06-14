import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javax.xml.stream.*;
import java.io.*;

/**
 * Created by aneudy on 08/06/17.
 */
public class ResumeGenerator {
    private static String xmlRelativePath = "resources/resume.xml";
    private static String pdfRelativePath = "results/pdfgenerated/resume.pdf" ;
    private static String xmlResumeFile = null;

    public static void main(String[] args) {
        try {
            xmlResumeFile = convertToFileURL(xmlRelativePath);
        } catch (FileNotFoundException fnfe){
            System.out.println("XML file not found.");
        }
        Resume resume = new Resume(xmlResumeFile);
        try {
            createResumePDF(resume);
        } catch (DocumentException de){
        } catch (FileNotFoundException fnfe){
            System.out.println("ERROR[createResumePDF]: File " + "\'" + pdfRelativePath + "\'" +
                    " cannot be opened.");

        }
    }

    // Converts the file's relative path to absolute
    private static String convertToFileURL(String xmlRelativePath) throws FileNotFoundException{
        String path = new File(xmlRelativePath).getAbsolutePath();
        if(File.separatorChar != '/'){
            path = path.replace(File.separatorChar, '/');
        }
        if(!path.startsWith("/")){
            path = "/" + path;
        }
        return path;
    }

    public static void createResumePDF(Resume resume) throws DocumentException, FileNotFoundException {
        // DataStructure with resume information parsed from XML file
        String candidateName = "Aneudy Mota";
        Paragraph header = new Paragraph(candidateName + "\n" + "Hello there");
        header.setAlignment(Element.ALIGN_CENTER);
        Document document = new Document();
        // Output file to which the PDF Resume file will be saved.
        PdfWriter.getInstance(document, new FileOutputStream(pdfRelativePath));

        document.open();
        document.add(header);
        document.close();
    }
}
