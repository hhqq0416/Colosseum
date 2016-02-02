/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlImpl implements XmlInterface {
    private Document document;

    public void init() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
            this.document.setXmlStandalone(true);
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void parserXml(String movieName, String fileFrom, String fileTo) {
        List<String> infoObject = new ArrayList<String>();
        List<String> chapter = new ArrayList<String>();
        List<String> chapterBegin = new ArrayList<String>();
        List<String> chapterEnd = new ArrayList<String>();
        int timeOffset = 36000000;
        String defaultSceneImageUrl = "/iod/images/sintel/20150112041240389.png";

        Element root = this.document.createElement("movie");
        root.setAttribute("xmlns", "http://www.example.org/InfoObjectSchema");
        this.document.appendChild(root);

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileFrom);

            NodeList infoObjectFromSrc = document.getElementsByTagName("ttm:agent");
            InfoObject[] infoObjectAll = new InfoObject[infoObjectFromSrc.getLength()];
            ArrayList<String> infoObjectAllTag = new ArrayList<>();
            for (int i = 0; i < infoObjectFromSrc.getLength(); i++) {
                Element temp = (Element) infoObjectFromSrc.item(i);
                infoObject.add(infoObjectFromSrc.item(i).getTextContent().trim());
                infoObjectAll[i] = new InfoObject(infoObjectFromSrc.item(i).getTextContent().trim());
                infoObjectAllTag.add(temp.getAttribute("xml:id"));
            }
            infoObjectFromSrc = null;

            NodeList chapterFromSrc = document.getElementsByTagName("chapter");
            for (int j = 0; j < chapterFromSrc.getLength(); j++) {
                Element temp = (Element) chapterFromSrc.item(j);
                NodeList chapterFromSrcTime = temp.getElementsByTagName("tt:p");

                Element temp1 = (Element) chapterFromSrcTime.item(0);
                chapterBegin.add(formtransfer(temp1.getAttribute("begin"), timeOffset));
                Element temp2 = (Element) chapterFromSrcTime.item(chapterFromSrcTime.getLength() - 1);
                chapterEnd.add(formtransfer(temp2.getAttribute("end"), timeOffset));
                ArrayList<Integer> chapterId = new ArrayList<Integer>();
                for (int k = 0; k < infoObjectAll.length; k++) {
                    if (isInChapter(infoObjectAllTag.get(k), temp)) {
                        infoObjectAll[k].addChapter(j);
                        chapterId.add(k);
                    }
                }
                for (int l = 0; l < chapterId.size(); l++) {
                    for (int lmin = 0; lmin < l; lmin++) {
                        infoObjectAll[chapterId.get(l)].addReference(lmin);
                    }
                    for (int lmax = chapterId.size() - 1; lmax > l; lmax--) {
                        infoObjectAll[chapterId.get(l)].addReference(lmax);
                    }
                }
            }
            //System.out.println(infoObjectAll[0].)
            Element infoObjects = this.document.createElement("info-objects");
            Element scenes = this.document.createElement("scenes");

            for (InfoObject element : infoObjectAll) {
                ArrayList<Localization> local = element.getLocalization();

                Element infoObjectEle = this.document.createElement("info-object");
                Element id = this.document.createElement("id");
                id.appendChild(this.document.createTextNode(element.getId()));
                Element imageUrl = this.document.createElement("image-url");
                imageUrl.appendChild(this.document.createTextNode(element.getImageUrl()));
                Element priority = this.document.createElement("priority");
                priority.appendChild(this.document.createTextNode(Integer.toString(element.getPriority())));
                Element category = this.document.createElement("category");
                category.appendChild(this.document.createTextNode(element.getCategory()));
                Element localized = this.document.createElement("localized");
                for (Localization t : local) {
                    Element lang = this.document.createElement("lang");

                    Element code = this.document.createElement("code");
                    code.appendChild(this.document.createTextNode(t.getLang()));
                    Element name = this.document.createElement("name");
                    name.appendChild(this.document.createTextNode(t.getName()));
                    Element attr = this.document.createElement("attribute");
                    attr.appendChild(this.document.createTextNode(t.getAttr()));
                    Element description = this.document.createElement("description");
                    description.appendChild(this.document.createTextNode(t.getDes()));
                    Element data_1 = this.document.createElement("data-1");
                    Element data_2 = this.document.createElement("data-2");
                    Element data_3 = this.document.createElement("data-3");
                    lang.appendChild(code);
                    lang.appendChild(name);
                    lang.appendChild(attr);
                    lang.appendChild(description);
                    lang.appendChild(data_1);
                    lang.appendChild(data_2);
                    lang.appendChild(data_3);
                    localized.appendChild(lang);
                }
                //element.addReference(1);
                Element references = this.document.createElement("references");
                for (int p = 0; p < element.getReference().size(); p++) {
                    Element reference = this.document.createElement("reference");
                    Element referenceId = this.document.createElement("id");
                    referenceId
                            .appendChild(this.document.createTextNode(infoObjectAll[element.getReference(p)].getId()));
                    reference.appendChild(referenceId);
                    references.appendChild(reference);
                }
                infoObjectEle.appendChild(id);
                infoObjectEle.appendChild(imageUrl);
                infoObjectEle.appendChild(priority);
                infoObjectEle.appendChild(category);
                infoObjectEle.appendChild(this.document.createElement("data-1"));
                infoObjectEle.appendChild(this.document.createElement("data-2"));
                infoObjectEle.appendChild(this.document.createElement("data-3"));
                infoObjectEle.appendChild(this.document.createElement("data-4"));
                infoObjectEle.appendChild(this.document.createElement("data-5"));
                infoObjectEle.appendChild(localized);
                infoObjectEle.appendChild(references);
                infoObjects.appendChild(infoObjectEle);

                for (int l = 0; l < element.getAllChapter().size(); l++) {
                    Element scene = this.document.createElement("scene");
                    Element vodid = this.document.createElement("vod-id");
                    //vodid.appendChild(this.document.createTextNode(movieName));
                    vodid.appendChild(this.document.createTextNode("MOV_COMEDY_5"));
                    Element infoObjectId = this.document.createElement("info-object-id");
                    infoObjectId.appendChild(this.document.createTextNode(element.getId()));
                    Element startTime = this.document.createElement("start-time");
                    startTime.appendChild(this.document.createTextNode(chapterBegin.get(element.getAllChapter(l))));
                    Element endTime = this.document.createElement("stop-time");
                    endTime.appendChild(this.document.createTextNode(chapterEnd.get(element.getAllChapter(l))));
                    Element imageUrl2 = this.document.createElement("image-url"); //need to be changed!!!!
                    imageUrl2.appendChild(this.document.createTextNode(defaultSceneImageUrl));
                    Element chapter_data_1 = this.document.createElement("data-1");
                    Element chapter_data_2 = this.document.createElement("data-2");
                    Element chapter_data_3 = this.document.createElement("data-3");
                    scene.appendChild(vodid);
                    scene.appendChild(infoObjectId);
                    scene.appendChild(startTime);
                    scene.appendChild(endTime);
                    scene.appendChild(imageUrl2);
                    scene.appendChild(chapter_data_1);
                    scene.appendChild(chapter_data_2);
                    scene.appendChild(chapter_data_3);
                    scenes.appendChild(scene);
                }
            }

            root.appendChild(infoObjects);
            root.appendChild(scenes);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileTo));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }

    private String formtransfer(String time, int offset) {
        String[] seperateTime = time.split(":");
        int timeMs = 0;
        for (int i = 0; i < seperateTime.length; i++) {
            if (i == 3) {
                timeMs = (timeMs * 1000) + Integer.parseInt(seperateTime[i]);
            } else {
                timeMs = (timeMs * 60) + Integer.parseInt(seperateTime[i]);
            }
        }
        timeMs = timeMs - offset;
        return Integer.toString(timeMs);

    }

    private boolean isInChapter(String infoName, Element chapter) {
        NodeList chapterSpan = chapter.getElementsByTagName("tt:span");
        for (int i = 0; i < chapterSpan.getLength(); i++) {
            Element temp = (Element) chapterSpan.item(i);
            if (temp.getAttribute("ttm:agent").equals(infoName)) {
                return true;
            }
        }
        return false;
    }
}