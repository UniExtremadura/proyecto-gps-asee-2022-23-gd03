
package es.unex.parsiapp.twitterapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("edit_history_tweet_ids")
    @Expose
    private List<String> editHistoryTweetIds = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Datum withText(String text) {
        this.text = text;
        return this;
    }

    public List<String> getEditHistoryTweetIds() {
        return editHistoryTweetIds;
    }

    public void setEditHistoryTweetIds(List<String> editHistoryTweetIds) {
        this.editHistoryTweetIds = editHistoryTweetIds;
    }

    public Datum withEditHistoryTweetIds(List<String> editHistoryTweetIds) {
        this.editHistoryTweetIds = editHistoryTweetIds;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Datum withId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Datum withAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
