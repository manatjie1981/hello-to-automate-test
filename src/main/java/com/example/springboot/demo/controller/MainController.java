package com.example.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Controller
public class MainController {

    private  String FILENAME_OUTPUT = "D:/robot/output.xml";
    @GetMapping("/from")
    public String from(){
        return "main_from";
    }

    @GetMapping("/runtestcase")
    public String runtestcase(){
        run();
        try{Thread.sleep(60000);}catch(InterruptedException e){System.out.println(e);}
        readResultExecute();

        return "main_from";
    }

    private void run(){
        try
        {

            File dir = new File("D:/robot");
            //String cmd = "robot -v policy:"+policyNo+" -v date:"+admitDate+" -v doc:"+doctorNo+" ecs_create_claim_robot_framework.robot";
            String cmd = "robot test_script01.txt";
            System.out.println("cmd : "+cmd);
            Process process = Runtime.getRuntime().exec(cmd, null, dir);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //return null;
    }

    public Boolean readResultExecute() {

        // Instantiate the Factory
        Boolean isPass = Boolean.FALSE;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME_OUTPUT));

            doc.getDocumentElement().normalize();

            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <staff>
            NodeList list = doc.getElementsByTagName("stat");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    // get staff's attribute
                    String pass = element.getAttributes().getNamedItem("pass").getTextContent();
                    System.out.println(" pass : "+pass);
                    String fail = element.getAttributes().getNamedItem("fail").getTextContent();
                    System.out.println(" fail : "+fail);
                    String skip = element.getAttributes().getNamedItem("skip").getTextContent();
                    System.out.println(" skip : "+skip);


                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  isPass;
    }
}
