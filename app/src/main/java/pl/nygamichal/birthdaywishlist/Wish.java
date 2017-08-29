package pl.nygamichal.birthdaywishlist;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mnyga on 29.08.2017.
 */

public class Wish extends RealmObject {
    public String content;
    public Person owner;
    @PrimaryKey
    public String id;
    public byte[] photo;
    public boolean isDone;

    public Wish() {
    }

    public Wish(String content, Person owner, byte[] photo) {
        this.content = content;
        this.owner = owner;
        this.photo = photo;
        id = UUID.randomUUID().toString();
        isDone = false;
    }
}


