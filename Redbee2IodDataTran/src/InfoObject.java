import java.util.ArrayList;
import java.util.UUID;

/*------------------------------------------------------------------------------
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/

public class InfoObject {

    private String id;
    private String imageUrl;
    private String defaultimageUrl = "/iod/images/sintel/sintelSintel.png";
    private String category;
    private ArrayList<Localization> localList = new ArrayList<Localization>();

    private int priority;
    private ArrayList<Integer> chapterList = new ArrayList<Integer>();
    private ArrayList<Integer> referenceList = new ArrayList<Integer>();

    public InfoObject(String name) {
        if (name != null) {

            if (this.id == null) {
                this.id = UUID.randomUUID().toString();
            }
            this.setCategory(null);
            this.setLocalization(new Localization(name));
            this.setPriority(0);
            this.setURL(null);
        }
    }

    public void setCategory(String category) {
        if (category != null) {
            this.category = category;
        } else {
            this.category = "Video Content";
        }
    }

    public void setPriority(int priority) {
        if ((priority > 0) && (priority < 999)) {
            this.priority = priority;
        } else {
            this.priority = 999;
        }
    }

    public void setLocalization(Localization localization) {
        this.localList.add(localization);
    }

    public void setURL(String imageUrl) {
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        } else {
            this.imageUrl = defaultimageUrl;
        }
    }

    public String getId() {
        return this.id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getCategory() {
        return this.category;
    }

    public int getPriority() {
        return this.priority;
    }

    public ArrayList<Localization> getLocalization() {
        return this.localList;
    }

    public void addChapter(int chapter) {
        this.chapterList.add(chapter);
    }

    public ArrayList getAllChapter() {
        return this.chapterList;
    }

    public int getAllChapter(int i) {
        return this.chapterList.get(i);
    }

    public void addReference(int n) {
        if (!this.referenceList.contains(n)) {
            this.referenceList.add(n);
        }
    }

    public ArrayList getReference() {
        return this.referenceList;
    }

    public int getReference(int i) {
        return this.referenceList.get(i);
    }
}
