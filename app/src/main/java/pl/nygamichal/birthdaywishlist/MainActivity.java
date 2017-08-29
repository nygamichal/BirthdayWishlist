package pl.nygamichal.birthdaywishlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getCanonicalName();
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.fabAdd)
    public void onClickFab(){
        Intent intent = new Intent(MainActivity.this,WishAddActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RealmResults<Wish> wishes = realm.where(Wish.class).findAll();
        Log.d(TAG, "onResume: ");
    }
}
