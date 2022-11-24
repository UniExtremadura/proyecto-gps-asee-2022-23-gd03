
package es.unex.parsiapp.twitterapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("newest_id")
    @Expose
    private String newestId;
    @SerializedName("oldest_id")
    @Expose
    private String oldestId;
    @SerializedName("result_count")
    @Expose
    private Integer resultCount;
    @SerializedName("next_token")
    @Expose
    private String nextToken;

    public String getNewestId() {
        return newestId;
    }

    public void setNewestId(String newestId) {
        this.newestId = newestId;
    }

    public Meta withNewestId(String newestId) {
        this.newestId = newestId;
        return this;
    }

    public String getOldestId() {
        return oldestId;
    }

    public void setOldestId(String oldestId) {
        this.oldestId = oldestId;
    }

    public Meta withOldestId(String oldestId) {
        this.oldestId = oldestId;
        return this;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public Meta withResultCount(Integer resultCount) {
        this.resultCount = resultCount;
        return this;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public Meta withNextToken(String nextToken) {
        this.nextToken = nextToken;
        return this;
    }

}
