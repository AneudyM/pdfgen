import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.xml.stream.*;
import java.io.*;
import java.lang.String;

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
        try {
            FileReader file = new FileReader(xmlRelativePath);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader xmlsr = factory.createXMLStreamReader(file);
            while(xmlsr.hasNext()) {
                printEvents(xmlsr);
                xmlsr.next();
            }
            xmlsr.close();
        } catch (FileNotFoundException fnfe){
            System.out.println("XML file not found.");
        } catch (XMLStreamException xmlsre){
            System.out.println("XMLStreamReaderException.");
        }
        /*
        Resume resume = new Resume(xmlResumeFile);
        try {
            createResumePDF(resume);
        } catch (DocumentException de){
        } catch (FileNotFoundException fnfe){
            System.out.println("ERROR[createResumePDF]: File " + "\'" + pdfRelativePath + "\'" +
                    " cannot be opened.");

        }
        */
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

    // Test method
    public static void printEvents(XMLStreamReader xmlsr) throws FileNotFoundException, XMLStreamException{
        System.out.print("EVENT:["+xmlsr.getLocation().getLineNumber()+"]["+xmlsr.getLocation().getColumnNumber()+"] ");
        System.out.print(" [");
        switch (xmlsr.getEventType()){
            case XMLStreamConstants.START_ELEMENT:
                System.out.print("<");
                printName(xmlsr);
                printNamespaces(xmlsr);
                printAttributes(xmlsr);
                System.out.print(">");
                break;
            case XMLStreamConstants.END_ELEMENT:
                System.out.print("</");
                printName(xmlsr);
                System.out.print(">");
                break;


            case XMLStreamConstants.CHARACTERS:
                int start = xmlsr.getTextStart();
                int length = xmlsr.getTextLength();
                System.out.print(new String(xmlsr.getTextCharacters(), start, length));
                break;

            case XMLStreamConstants.PROCESSING_INSTRUCTION:
                System.out.print("<?");
                if (xmlsr.hasNext()){
                    System.out.print(xmlsr.getText());
                }
                System.out.print("?>");
                break;

            case XMLStreamConstants.CDATA:
                System.out.print("<![CDATA[");
                start = xmlsr.getTextStart();
                length = xmlsr.getTextLength();
                System.out.print(new String(xmlsr.getTextCharacters(), start, length));
                System.out.print("]]>");
                break;

            case XMLStreamConstants.COMMENT:
                System.out.print("<!--");
                if (xmlsr.hasNext()){
                    System.out.print(xmlsr.getText());
                }
                System.out.print("-->");
                break;

            case XMLStreamConstants.ENTITY_REFERENCE:
                System.out.print(xmlsr.getLocalName()+"=");
                if (xmlsr.hasNext()){
                    System.out.print("["+xmlsr.getText()+"]");
                }
                break;

            case XMLStreamConstants.START_DOCUMENT:
                System.out.print("<?xml");
                System.out.print(" verison='"+xmlsr.getVersion()+"'");
                System.out.print(" encoding='"+xmlsr.getCharacterEncodingScheme()+"'");
                if (xmlsr.isStandalone()){
                    System.out.print(" standalone='yes'");
                }else{
                    System.out.print(" standalone='no'");
                }
                System.out.print("?>");
                break;
        }
        System.out.println("]");
    }

    private static void printName(XMLStreamReader xmlsr) throws XMLStreamException{
        if(xmlsr.hasNext()){
            String prefix = xmlsr.getPrefix();
            String uri = xmlsr.getNamespaceURI();
            String localName = xmlsr.getLocalName();
            printName(prefix, uri, localName);
        }
    }

    private static void printName(String prefix, String uri, String localName){
        if (uri != null && !("".equals(uri))){
            System.out.print("['"+uri+"']:");
        }
        if (prefix != null){
            System.out.print(prefix+":");
        }
        if (localName != null){
            System.out.print(localName);
        }
    }

    private static void printAttributes(XMLStreamReader xmlsr){
        for (int i = 0; i < xmlsr.getAttributeCount(); i++){
            printAttribute(xmlsr,i);
        }
    }

    private static void printAttribute(XMLStreamReader xmlsr, int index){
        String prefix = xmlsr.getAttributePrefix(index);
        String namespace = xmlsr.getAttributeNamespace(index);
        String localName = xmlsr.getAttributeLocalName(index);
        String value = xmlsr.getAttributeValue(index);
        System.out.print(" ");
        printName(prefix,namespace,localName);
        System.out.print("='"+value+"'");
    }

    private static void printNamespaces(XMLStreamReader xmlsr){
        for (int i = 0; i < xmlsr.getNamespaceCount(); i++){
            printNamespace(xmlsr,i);
        }
    }

    private static void printNamespace(XMLStreamReader xmlsr, int index){
        String prefix = xmlsr.getNamespacePrefix(index);
        String uri = xmlsr.getNamespaceURI(index);
        System.out.print(" ");
        if (prefix == null){
            System.out.print("xmlns='"+uri+"'");
        }else{
            System.out.print("xmlns='"+prefix+"='"+uri+"'");
        }
    }

    public static void createResumePDF(Resume resume) throws DocumentException, FileNotFoundException {
        // DataStructure with resume information parsed from XML file
        String candidateName = "Aneudy Mota";
        Paragraph header = new Paragraph(candidateName + "\n" + "aneudy.motacatalino@gmail.com");
        header.setAlignment(Element.ALIGN_CENTER);
        Document document = new Document();
        // Output file to which the PDF Resume file will be saved.
        PdfWriter.getInstance(document, new FileOutputStream(pdfRelativePath));

        document.open();
        document.add(header);
        document.close();
    }
}
