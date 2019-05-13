package hkucs.comp3330.gogocoach.firebase;

import java.io.Serializable;

public class NewsFeed implements Serializable {
    public String topic;
    public String content;
    public String time;
    public String id;
    public String name;
    public String photoUrl;

    public NewsFeed(){}

    public NewsFeed(String topic,String content,String time, String id, String name , String photoUrl){
        this.topic =topic;
        this.content = content;
        this.time= time;
        this.id = id;
        this.name= name;
        this.photoUrl= photoUrl;

    }

}