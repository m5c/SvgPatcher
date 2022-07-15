package eu.kartoffelquadrat.svgpatcher;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

/**
 * Unit test for xml I/O operations.
 *
 * @author Maximilian Schiedermeier
 */

public class XmlIOUtilsTest {

    /**
     * Test the import of an XML file in disk and conversion into a in-memory tree structured document.
     *
     * @throws IOException                  in case the filesystem can not be accessed
     * @throws SAXException                 in case the provided file is not xml
     * @throws ParserConfigurationException in case the internally required document builder can not be created.
     */
    @Test
    public void testXmlImport() throws IOException, SAXException, ParserConfigurationException {
        XmlIOUtils.parseXmlToDocument("pom.xml");
    }

    /**
     * Test the import of an XML file in disk and conversion into a in-memory tree structured document.
     *
     * @throws IOException                  in case the filesystem can not be accessed
     * @throws SAXException                 in case the provided file is not xml
     * @throws ParserConfigurationException in case the provided file does not correspond to the provided DTD
     */
    @Test
    public void testXmlExport() throws IOException, SAXException, ParserConfigurationException, TransformerException {

        // File name used for the test export
        String testFileName = "pom-testexport.xml";

        // Test import followed by export
        Document document = XmlIOUtils.parseXmlToDocument("pom.xml");
        XmlIOUtils.writeXmlDocumentToDisk(document, testFileName);

        // Verify the file exists, then remove it, so this test does not clutter the repo
        File file = new File(testFileName);
        assert(file.exists());
        file.delete();
        assert(!file.exists());
    }

}
