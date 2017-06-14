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

    public static void main(String[] args) {
        // First, instantiate a resume
        // Resume initialization should automatically parse the XML you pass to it.
        Resume resume = new Resume(xmlResumeFile);

        String xmlFile = convertToFileURL(xmlRelativePath);
        parseXMLDocument(xmlFile);
        try {
            createResume(pdfRelativePath);
        } catch (DocumentException de){
            System.out.println("iText could not instantiate Document");
        } catch (FileNotFoundException fnfe){
            System.out.println("Could not create resume file.");
        }

    }

    // Converts the file's relative path to absolute
    public static void createPDFResume(Resume resume) throws DocumentException, FileNotFoundException {
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
