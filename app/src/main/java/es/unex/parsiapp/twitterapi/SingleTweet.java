
package es.unex.parsiapp.twitterapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import es.unex.parsiapp.model.Post;


public class SingleTweet {

    @SerializedName("data")
    @Expose
    private Datum data;
    @SerializedName("includes")
    @Expose
    private Includes includes;

    public Datum getData() {
        return data;
    }

    public void setData(Datum data) {
        this.data = data;
    }

    public Includes getIncludes() {
        return includes;
    }

    public void setIncludes(Includes includes) {
        this.includes = includes;
    }

    /*
        Metodo para pasar un objeto TweetResults a una lista de Posts
     */
    public Post toPost(){
        Post p = new Post(this.data.getId());
        p.setAuthorId(this.data.getAuthorId());
        p.setContenido(this.data.getText());
        for(User u: this.includes.getUsers()){
            if (p.getAuthorId().equals(u.getId())) {
                p.setAuthorUsername(u.getUsername());
                p.setProfilePicture(u.getProfileImageUrl());
            }
        }
        String[] tiempo = this.data.getCreatedAt().split("T")[1].split(":");
        p.setTimestamp(tiempo[0]+":"+tiempo[1]);

        return p;
    }

}
