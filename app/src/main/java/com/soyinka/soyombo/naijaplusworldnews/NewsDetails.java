package com.soyinka.soyombo.naijaplusworldnews;

/**
 * Created by SHOW on 12/26/2018.
 */

public class NewsDetails {

    public String website;
    public String names;
    public int resourceId;

    public NewsDetails(String website, String names, int resourceId) {
        this.website = website;
        this.names = names;
        this.resourceId = resourceId;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

}
