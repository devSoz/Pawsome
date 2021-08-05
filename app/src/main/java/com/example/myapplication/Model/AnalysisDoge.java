
package com.example.myapplication.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnalysisDoge {

    @SerializedName("labels")
    @Expose
    private List<Label> labels = null;
    @SerializedName("moderation_labels")
    @Expose
    private List<Object> moderationLabels = null;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Object> getModerationLabels() {
        return moderationLabels;
    }

    public void setModerationLabels(List<Object> moderationLabels) {
        this.moderationLabels = moderationLabels;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
