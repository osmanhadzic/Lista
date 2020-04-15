package com.company;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static String xml="\n";

    public static void main(String[] args) {
        int i = 1;

        Connection connection = null;
        connection = Connector.getConnection();
        try {
            Statement s = connection.createStatement();
            String upit = "SELECT * FROM sakila.actor";
            ResultSet rs = s.executeQuery(upit);

            while (rs.next()) {
                 xml = xml +
                        "<Actor> " +
                        "<ID> " + rs.getInt(1) + "</ID>"+
                        "<Name> " + rs.getString(2) + " </Name>"+
                        "<Surname> " + rs.getString(3) + " </Surname>"
                        +" </Actor>" + "\n" ;

            }

        }catch (Exception e){
            System.out.println(e.getMessage());

    }
        String xmlSakila = "<Sakila> " + xml +  " </Sakila>";
        System.out.println(xmlSakila);
        Document doc = convertStringToXML(xmlSakila);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("file.xml"));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }

        System.out.println("File saved!");


    }

    private static Document convertStringToXML(String xml){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            return doc;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
