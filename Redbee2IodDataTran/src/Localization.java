/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/

public class Localization {
    private String language;
    private String name;
    private String attribute;
    private String description;

    public Localization(String name) {
        this.setLang(null);
        this.setName(name);
        this.setAttr(null);
        this.setDes(null);
    }

    public Localization(String lang, String name, String attr, String des) {
        if ((lang != null) && (name != null)) {
            this.setLang(lang);
            this.setName(name);
            this.setAttr(attr);
            this.setDes(des);
        }
    }

    //setter
    private void setDes(String des) {
        // TODO Auto-generated method stub
        if (des != null) {
            this.description = des;
        } else {
            this.description = "Description Not Set";
        }
    }

    private void setAttr(String attr) {
        // TODO Auto-generated method stub
        if (attr != null) {
            this.attribute = attr;
        } else {
            this.attribute = "Attribute Not Set";
        }
    }

    private void setName(String name) {
        // TODO Auto-generated method stub
        if (name != null) {
            this.name = name;
        } else {
            this.language = "Name Not Set";
        }
    }

    private void setLang(String lang) {
        if (lang != null) {
            this.language = lang;
        } else {
            this.language = "en";
        }
    }

    //getter
    public String getDes() {
        return this.description;
    }

    public String getAttr() {
        return this.attribute;
    }

    public String getName() {
        return this.name;
    }

    public String getLang() {
        return this.language;
    }
}
