package eu.kartoffelquadrat.svgpatcher;

import org.w3c.dom.Document;

/**
 * Abstract base class for all patchers
 */
public abstract class Patcher {

    // The svg this patcher works on
    protected Document svg;

    public Patcher(Document svg) {
        this.svg = svg;
    }

    public abstract Document execute();
}
