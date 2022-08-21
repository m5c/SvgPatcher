import eu.kartoffelquadrat.svgpatcher.CssPatcher;
import eu.kartoffelquadrat.svgpatcher.IdPatcher;
import eu.kartoffelquadrat.svgpatcher.XmlIOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class SvGPatcherTest {

    public static final File TEST_RESOURCE_FOLDER = new File("src/test/resources");
    public static final File TEST_INPUT_GRAPHIC = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/testgraphic.svg");
    public static final File TEST_IDPATCHED_OUTPUT_LOCATION = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/idpatched.svg");
    public static final File TEST_IDPATCHED_REFERENCE_LOCATION = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/idpatchedreference.svg");
    public static final File TEST_CSSPATCHED_OUTPUT_LOCATION = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/csspatched.svg");
    public static final File TEST_CSSPATCHED_REFERENCE_LOCATION = new File(TEST_RESOURCE_FOLDER.getAbsolutePath() + "/csspatchedreference.svg");


    /**
     * Tests Loading of test resource file form disk and interpretation as SVG file
     */
    @Test
    public void testLoadFromTestResources() throws IOException, SAXException, ParserConfigurationException {
        Document svg = XmlIOUtils.parseXmlToDocument(TEST_INPUT_GRAPHIC.getAbsolutePath());
    }

    /**
     * Test if the string (not persisted to disk) when sample svg is only ID patched complies to what we expect.
     */
    @Test
    public void testIdPatch() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        Document svg = XmlIOUtils.parseXmlToDocument(TEST_INPUT_GRAPHIC.getAbsolutePath());

        // Patch all IDs. This is the transformation we want to test
        IdPatcher.patchAllOmnigraffleIds(svg);

        // Verify the persisted svg content is as expected (verifies the above patch worked as expected)
        exportAndCompareToExpected(svg, TEST_IDPATCHED_OUTPUT_LOCATION, TEST_IDPATCHED_REFERENCE_LOCATION);
    }

    /**
     * Test if the string (not persisted to disk) when sample svg is only ID patched complies to what we expect.
     */
    @Test
    public void testCssPatch() throws IOException, SAXException, ParserConfigurationException, TransformerException, NoSuchAlgorithmException {
        Document svg = XmlIOUtils.parseXmlToDocument(TEST_INPUT_GRAPHIC.getAbsolutePath());

        // Patch all IDs. This is the transformation we want to test
        CssPatcher.patchSvgCss(svg);

        // Verify the persisted svg content is as expected (verifies the above patch worked as expected)
        exportAndCompareToExpected(svg, TEST_CSSPATCHED_OUTPUT_LOCATION, TEST_CSSPATCHED_REFERENCE_LOCATION);
    }

    /**
     * Helper method to export a patched svg to disk and test its content corresponds to the expected reference file.
     *
     * @param svg       as a modified svg
     * @param output    as the location where to store the patched file
     * @param reference as the location of the reference file
     */
    private void exportAndCompareToExpected(Document svg, File output, File reference) throws IOException, TransformerException {

        // Remove any trace of exported file from a previous test run
        File legacyExportFile = new File(output.getAbsolutePath());
        if (legacyExportFile.exists())
            legacyExportFile.delete();

        // Write the outcome to disk
        XmlIOUtils.writeXmlDocumentToDisk(svg, output.getAbsolutePath());

        // Verify this corresponds to the expected outcome
        String generatedContent = new Scanner(output).useDelimiter("\\Z").next();
        String expectedContent = new Scanner(reference).useDelimiter("\\Z").next();

        Assert.assertTrue("Generated file with patched ids differs from expected output. Run this command to" +
                        " inspect how they differ: \nicdiff " + output.getAbsolutePath() + " " + reference.getAbsolutePath(),
                generatedContent.equals(expectedContent));
    }


}
