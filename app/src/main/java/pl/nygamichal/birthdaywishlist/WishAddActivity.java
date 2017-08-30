package pl.nygamichal.birthdaywishlist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class WishAddActivity extends AppCompatActivity {
    @BindView(R.id.editTextContent) EditText editTextContent;
    @BindView(R.id.editTextPerson) EditText editTextPerson;
    @BindView(R.id.imageView) ImageView imageView;

    private Wish wish;
    private Realm realm;
    private byte[] arrPhoto;

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
        wish = new Wish(content,person,arrPhoto);
        realm.beginTransaction();
        realm.copyToRealm(wish);
        realm.commitTransaction();
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
        WishAddActivity.this.finish();
    }

    @OnClick(R.id.buttonAddPhoto)
    public void onClickAddPhoto(){
        if (ContextCompat.checkSelfPermission(WishAddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(WishAddActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
        }
        else
        {
            EasyImage.openChooserWithDocuments(WishAddActivity.this, "Select source", 0);//wybor galerii, zdjecia, itp
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Toast.makeText(WishAddActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,70,stream);
                arrPhoto = stream.toByteArray();
                if (imageView != null)
                {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
    }

}
