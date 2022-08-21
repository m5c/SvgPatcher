import eu.kartoffelquadrat.svgpatcher.XmlIOUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class LoadTest {

    private static final String TEST_GRAPHIC = "src/test/resources/testgraphic.svg";

    @Test
    public void testLoadFromTestResources() throws IOException, SAXException, ParserConfigurationException {
        Document svg = XmlIOUtils.parseXmlToDocument(TEST_GRAPHIC);

    }
}
