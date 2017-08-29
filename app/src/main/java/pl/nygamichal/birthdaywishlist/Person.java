package pl.nygamichal.birthdaywishlist;

import io.realm.RealmObject;

/**
 * Created by mnyga on 29.08.2017.
 */

public class Person extends RealmObject{
    public String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }
}
