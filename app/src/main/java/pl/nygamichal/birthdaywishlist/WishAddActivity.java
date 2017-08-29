package pl.nygamichal.birthdaywishlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class WishAddActivity extends AppCompatActivity {
    @BindView(R.id.editTextContent)
    EditText editTextContent;
    @BindView(R.id.editTextPerson)
    EditText editTextPerson;
    private Wish wish;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_add);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }

    @OnClick(R.id.fab)
    public void onClickFab(){
        String content = editTextContent.getText().toString();
        Person person = new Person(editTextPerson.getText().toString());
        wish = new Wish(content,person,null);
        realm.beginTransaction();
        realm.copyToRealm(wish);
        realm.commitTransaction();
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
        WishAddActivity.this.finish();
    }
}
