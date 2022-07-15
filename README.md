# SVG for web-use patcher

Minimal DOM transformer that patches SVG files exported by OmniGraffle.

## About

Building user interfaces with a vector program like OmniGraffle is fun. Yet if your interface is more than static you will need DOM manipulations. Unfortunatley this also means you need a reliable way to target indidiual nodes in your SVG DOM tree. Omnigraffle allows setting IDs, but for whatever reason the programmers decided to disregard the existing way of identifying svg elements (using the ```id``` tag). Instead omnigraffle adds a child node named ```title```.
This little program patches SVG files, so the set IDs are where they are supposed to be, *in the node's ```id``` attribute.*  
In addition to that the program patches the default svg dimensions to somethign so gigantic (5000x5000) that whatever browser using this graphic is forced to apply the css scaling rules you may have enabled.

## How to run

 * ```cd``` into project.
 * replace ```vectorBoard.svg``` by the svg generated with Omnigraffle.
 * Once, add this VM argument as maven default (Allows dynamic loading of xml doctype definitions. This is required to parse the provided svg file.):  
 ```export MAVEN_OPTS="-Djavax.xml.accessExternalDTD=all"```
 * Start the software (make sure ```pom.xml``` is at call location)  
```mvn clean package exec:run```
 * The patched svg lies in tmp: ```/tmp/patchedVectorBoard.svg```



## Dependencies

None, this is all built-in Java functionality  :)

## Contact / Pull Requests

 * Author: Maximilian Schiedermeier ![email](email.png)
 * Github: Kartoffelquadrat
 * Webpage: https://www.cs.mcgill.ca/~mschie3
 * License: [MIT](https://opensource.org/licenses/MIT)


