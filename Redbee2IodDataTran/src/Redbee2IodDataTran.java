import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/

public class Redbee2IodDataTran {

    public static void main(String[] args) {
        long tic = System.nanoTime();
        // TODO Auto-generated method stub
        XmlImpl dd = new XmlImpl();
        String strFrom = "../Redbee2IodDataTran/xml/input";
        String strTo = "../Redbee2IodDataTran/xml/output";
        List<File> strXMLFrom = new ArrayList<File>();

        File file = new File(strFrom);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (!file2.isDirectory() && file2.getName().endsWith(".xml")) {
                    strXMLFrom.add(file2);
                }
            }
        }
        if (strXMLFrom.size() == 0) {
            System.out.println("There's no available Redbee data(.xml)");
        } else {
            for (int i = 0; i < strXMLFrom.size(); i++) {
                String name = strXMLFrom.get(i).getName();
                int pos = name.lastIndexOf(".");
                if (pos > 0) {
                    name = name.substring(0, pos);
                }

                dd.init();
                dd.parserXml(name, strFrom + "/" + strXMLFrom.get(i).getName(), strTo + "/IOD_"
                        + strXMLFrom.get(i).getName()); //创建XML
                //dd.upload(str);

                System.out.println("IOD_" + strXMLFrom.get(i).getName() + ".XML is created");

            }
        }
        long toc = System.nanoTime();
        long duration = (toc - tic) / 1000000;
        System.out.println("total time spended (in ms): " + duration);
    }
}
