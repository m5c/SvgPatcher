package eu.kartoffelquadrat.svgpatcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Util class to handle import of xml filed from disk. Based on this tutorial:
 * https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public class XmlInputOutputUtils {

  /**
   * Helper method to parse a given xml file.
   *
   * @param fileLocation as the location of the target xml file on disk.
   * @return document object representing the in-ram tree structure of the parsed file.
   * @throws ParserConfigurationException in case the provided parser arguments cannot be applied.
   * @throws IOException                  in case the provided input file location cannot be
   *                                      accessed.
   * @throws SAXException                 in case the provided svg file cannot be parsed.
   */
  public static Document parseXmlToDocument(String fileLocation)
      throws ParserConfigurationException, IOException, SAXException {

    // The document builder incrementally is able to read in / parse an XML file and converts it to
    // an object structure.
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    // optional, but recommended
    // process XML securely, avoid attacks like XML External Entities (XXE)
    dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

    // parse XML file
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(new File(fileLocation));

    // optional, but recommended
    // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    doc.getDocumentElement().normalize();
    return doc;
  }

  /**
   * Based on this tutorial: https://www.baeldung.com/java-write-xml-document-file
   *
   * @param xmlDocument  the in-memory xml tree.
   * @param fileLocation the file location on disk as string.
   * @throws TransformerException in case the modified file cannot be persisted to disk
   * @throws IOException          in case the modified file cannot be persisted to disk
   */
  public static void writeXmlDocumentToDisk(Document xmlDocument, String fileLocation)
      throws TransformerException, IOException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    // Specify input file
    DOMSource source = new DOMSource(xmlDocument);

    // Create an output stream the transformer can write to
    FileWriter writer = new FileWriter(new File(fileLocation));
    StreamResult result = new StreamResult(writer);

    // Actually convert the xml tree to text and write it into the file
    transformer.transform(source, result);
  }
}
