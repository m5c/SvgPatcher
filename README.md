# SVG for web-use patcher

Minimal DOM transformer that patches SVG files exported by OmniGraffle. Use this to prep your SVG files for integration
in a webapp.

## About

 > This software patches Omnigraffle-exported svg for convenient use as interactive web-UIs.

Like most of us, I favour designing a good game UI in a graphics program rather coding pure HTML and CSS.
That being said, the interest of this repo is to support embedding SVGs into an HTML page, i.e., export of a fancy
OmniGraffle SVG for display in a board game web implementation?

However, there are some caveats, which is why I set up this repo:

* Game elements (the disposition of game material on a board) changes throughout gameplay. That is, you'll have to
  modify the UI's DOM tree. Unfortunately most SVG editors will not let you place element ids, so it's not trivial to
  get hold of the objects to modify. This patcher converts textual tags (set within onmigraffle) into standard svg/xml
  element IDs.
* Game UIs should be centered and optimally use space. This can be done with css, but requires some patching (meta
  attributes in your css). This patcher automatically sets those. That is, the generated svg is decorated with css that
  ensures your UI always optimally adjusts to the available space.
* Game UIs should not have selectable text, that is, you don't want text to respond to mouse highlighting. This patcher
  embeds a tiny CSS in your svg that disables manual text selection.
* Identifiable game elements may need to trigger game actions. You can optionally pass javascript files as command line
  arguments, to embed within the svg file. The svg can only trigger embedded functions, which is why it may make sense
  to register proxy calls within the svg.


## Usage

* Export your SVG with Omnigraffle (see placement of [tag descriptions](#patcher-details))
* Launch the patcher:  
  ```mvn clean package exec:java "-Dexec.args=vectorBoard.svg patchedVectorBoard.svg /gvg/uiactions.js /foo/baz.js" "-Djavax.xml.accessExternalDTD=all"```  
  Explanation of arguments:
    * ```vectorBoard.svg```: Input SVG
    * ```patchedVectorBoard.svg```: Output SVG
    * ```/gvg/uiactions.js```: First javascript function to reference
    * ```/foo/baz.js```: Second javascript function to reference
    * ```-Djavax.xml.accessExternalDTD=all```: See [DTD Troubleshoot](#dtd-troubleshoot)
* Use patched SVG (exported to ```patchedVectorBoard.svg```) in webapp.  
  Sample [```patchedVectorBoard.svg```](patchedVectorBoard.svg), appears in a demo
  webapp, [here](https://github.com/kartoffelquadrat/GenericVectorGame).

List of applied patches:

* [ID Transformation](#svg-ids)
* [Dimension Patch](#svg-dimensions)
* [JS Function Reference Embed](#functional-reference-patcher)
* [CSS No Text Select Embed](#no-selectable-text)

### DTD Troubleshoot

The SVG patcher interprets the provided svg as xml file and hence runs a DTD verification. This is by default restricted by the JVM and must be allowed manually.

* When launched from IDE (IntelliJ): Add this option is in the run
  configuration: ```Runner``` -> ```VM Options``` -> ```"-Djavax.xml.accessExternalDTD=all"```
* When launching form command line, provide this runtime JVM argument: e.g.

```bash
mvn clean package exec:java "-Djavax.xml.accessExternalDTD=all" "-Dexec.args=vectorBoard.svg patchedVectorBoard.svg /gvg/uiactions.js /foo/baz.js"
```  

(Note: For testing this option is auto enabled and not required. ```mvn clean test``` can be run as is. I was simply not
able to auto-default the required JVM options for non-test launches.)

## Patcher Details

Suppose you have tagged an element in Omnigraffle like so:  
![omnigraffle.png](markdown/omnigraffle.png)

You can export it to an svg file, but unfortunately you still need a mechanism to store the ID you typed in the
corresponding DOM id attributes:
![omnigraffel.png](markdown/export.png)

Otherwise the IDs you set are in the target file, but not where you want them...

### SVG IDs

Calling this program changes all these patterns:

```xml

<g id="Graphic_3">
    <title>VID-SQUARE1</title>
    <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
    <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round"
          stroke-width="1"/>
    <text transform="translate(235.8 237.176)" fill="black">
        <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
    </text>
</g>
```

to:

```xml

<g id="VID_SQUARE1">
    <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
    <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round"
          stroke-width="1"/>
    <text transform="translate(235.8 237.176)" fill="black">
        <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
    </text>
</g>
```

### SVG Dimensions

In addition it boosts the svg dimensions to something gigantic:

```xml

<svg [...] width="211.2" height="211.5">
```

is changed to:

```xml

<svg [...] width="5000" height="5000">
```

### Functional Reference Patcher

Adds as many auto-loaded JavaScript function references (you may select the target locations) to your svg.
Added code is e.g.:

```svg

<script xlink:actuate="onLoad" xlink:href="/gvg/uiactions.js" xlink:show="other" xlink:type="simple"/>
<script xlink:actuate="onLoad" xlink:href="/foo/baz.js" xlink:show="other" xlink:type="simple"/>
```

(See [usage section](#usage), for input arguments.)

### No selectable text

Adds these css tags to your svg:

```css
svg text {
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}

svg text::selection {
    background: none;
}
```

As a result all text in your SVG is no longer manually selectable when displayed in a browser.

## Dependencies

None, this is all built-in Java functionality  :)

## Documentation

[API Doc on GitHub Pages](https://kartoffelquadrat.github.io/SvgPatcher/)

## Contact / Pull Requests

* Author: Maximilian Schiedermeier ![email](markdown/email.png)
* Github: Kartoffelquadrat
* Webpage: https://www.cs.mcgill.ca/~mschie3
* License: [MIT](https://opensource.org/licenses/MIT)


