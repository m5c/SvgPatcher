package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;

/**
 * Maximilian Schiedermeier 2022
 */
public class IdPatcher {

    /**
     * This method does the actual DOM transformation we are intested in. If in Omnigraffle an object is marked with
     * something like "VID-XYZ", where VID is a standard prefix for "Vector Identifier" and "XYZ" is the actual unique
     * name describing the purpose of the object, then we ant to set this entire string as "id" in the dom. In the SVG
     * generated by omnigraffle we have something like:
     *
     * <g id="Graphic_3">
     * <title>VID-SQUARE1</title>
     * <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
     * <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round"
     * stroke-width="1"/>
     * <text transform="translate(235.8 237.176)" fill="black">
     * <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
     * </text>
     * </g>
     * <p>
     * What we want is:
     * <g id="VID-SQUARE1">
     * <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
     * <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round"
     * stroke-width="1"/>
     * <text transform="translate(235.8 237.176)" fill="black">
     * <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
     * </text>
     * </g>
     * <p>
     * This method handles this transformation for an entire svg document.
     *
     * @param svg
     */
    public static Document patchAllOmnigraffleIds(Document svg) {


        // TODO: find all elements that are tagged like so:
//        https://docs.oracle.com/javase/8/docs/api/index.html?org/w3c/dom/package-summary.html
        // If in OmniGraffle an object, e.g. a rectangle was provided with "Object Data Name": "ID-FOO", then exported. This string is not yet used as object id in the svg representation.
        // Running this script ensures all id strings set with omnigraffle are then likewise copied for the corresponding object id.

        svg.getElementsByTagName("");
        return svg;
    }
}
