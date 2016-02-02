/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/

public interface XmlInterface {

    /**
     * Resolve XML doc
     * 
     * @param fileFrom Absolute folder path of the original redbee XML
     *            file
     * @param fileTo File of the generated output
     */
    public void parserXml(String movieName, String fileFrom, String fileTo);

}