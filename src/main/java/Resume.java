import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aneudy on 12/06/17.
 */
public class Resume {
    Header header;
    Education education;
    Experience experience;

    Resume(String xmlResumeFile){
        this.header = new Header();
        this.education = new Education();
        this.experience = new Experience();
        parseXMLDocument(xmlResumeFile);
    }

    private static String convertToFileURL(String xmlRelativePath){
        String path = new File(xmlRelativePath).getAbsolutePath();
        if(File.separatorChar != '/'){
            path = path.replace(File.separatorChar, '/');
        }
        if(!path.startsWith("/")){
            path = "/" + path;
        }
        return path;
    }

    private static void parseXMLDocument(String xmlFile){
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream input = new FileInputStream(xmlFile);
            XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(input);
            while(xmlStreamReader.hasNext()){
                int event = xmlStreamReader.next();
                // Parse the tags of the XML Document here.
                
            }
        } catch (FileNotFoundException fnfe){
            System.out.println("The file " + xmlFile.toString() + " was not found.");
        } catch (XMLStreamException xse){
        } catch (FactoryConfigurationError fce){
        }
    }

    protected static class Header {
        private String firstname;
        private String lastname;
        private String email;
        private String phone;
        private ArrayList<String> urls;

        Header(){}

        public void setFirstname(String fn){
            firstname = fn;
        }

        public void setLastname(String ln){
            lastname = ln;
        }

        public void setEmail(String email){
            this.email = email;
        }

        public void setPhone(String phone){
            this.phone = phone;
        }

        public void setUrls(String url){
            urls.add(url);
        }

        public String getFirstname(){
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public ArrayList<String> getUrls(){
            return urls;
        }
    }

    private class Education {
        private String school;
        private Date startDate;
        private Date endDate;
        private String degree;
        private String major;

        Education(){};

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }
    }

    private class Experience {
        private String jobTitle;
        private String company;
        private String city;
        private String country;
        private String startDate;
        private String endDate;

        Experience(){};

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}

