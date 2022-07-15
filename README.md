# SVG for web-use patcher

Minimal DOM transformer that patches SVG files exported by OmniGraffle.

## About

Building user interfaces with a vector program like OmniGraffle is fun. Yet if your interface is more than static you will need DOM manipulations. Unfortunatley this also means you need a reliable way to target indidiual nodes in your SVG DOM tree. Omnigraffle allows setting IDs, but for whatever reason the programmers decided to disregard the existing way of identifying svg elements (using the ```id``` tag). Instead omnigraffle adds a child node named ```title```.
This little program patches SVG files, so the set IDs are where they are supposed to be, *in the node's ```id``` attribute.*  
In addition to that the program patches the default svg dimensions to somethign so gigantic (5000x5000) that whatever browser using this graphic is forced to apply the css scaling rules you may have enabled.

## How to run

 * ```cd``` into project.
 * replace ```vectorBoard.svg``` by the svg generated with OmniGraffle.
 * Once, add this VM argument as maven default (Allows dynamic loading of xml doctype definitions. This is required to parse the provided svg file.):  
 ```export MAVEN_OPTS="-Djavax.xml.accessExternalDTD=all"```
 * Start the software (make sure ```pom.xml``` is at call location)  
 Replace the ```vectorBoard.svg``` argument by your input svg file.
```mvn clean package exec:java "-Dexec.args=vectorBoard.svg patchedVectorBoard.svg"```
 * The patched svg is stored at: ```patchedVectorBoard.svg```
 
 OR
 
  * Compile the program once with:  
  ```mvn clean package```
  * Then call it from wherever you need it:  
  ```java -Djavax.xml.accessExternalDTD=all -jar target/svgpatcher.jar vectorBoard.svg generatedVectorBoard.svg```

## How it works:

### SVG Dimensions

Changes this line:  
```xml
<svg [...] width="211.2" height="211.5">
```

To:  
```xml
<svg [...] width="5000" height="5000">
```


### SVG IDs

Changes these patterns:  
```xml
      <g id="Graphic_3">
        <title>VID-SQUARE1</title>
        <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
        <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <text transform="translate(235.8 237.176)" fill="black">
          <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
        </text>
      </g>
```

To:  
```xml
      <g id="VID_SQUARE1">
        <rect x="230.8" y="193.9" width="105" height="105" fill="#40ff40"/>
        <rect x="230.8" y="193.9" width="105" height="105" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="1"/>
        <text transform="translate(235.8 237.176)" fill="black">
          <tspan font-family="Helvetica Neue" font-size="16" font-weight="400" fill="black" x="34.156" y="15">361</tspan>
        </text>
      </g>
```

## Dependencies

None, this is all built-in Java functionality  :)

## Contact / Pull Requests

 * Author: Maximilian Schiedermeier ![email](email.png)
 * Github: Kartoffelquadrat
 * Webpage: https://www.cs.mcgill.ca/~mschie3
 * License: [MIT](https://opensource.org/licenses/MIT)


