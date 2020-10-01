package ghanbari.sadegh.newshop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import ghanbari.sadegh.newshop.pojo.ParseResponse;
import ghanbari.sadegh.newshop.pojo.UserProfile;
import ghanbari.sadegh.newshop.pojo.UserProfileResult;
import ghanbari.sadegh.newshop.webservice.UserProfileService;
import helper.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    final static int REQ_CAMERA_PERM = 1001;
    private static final int REQ_CODE_TAKE_PHOTO = 1005;
    private static final int REQ_CODE_GALLERY = 1006;

    ImageView imgProfile;
    private ProgressBar progressbar;
    private byte[] bitmapArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgProfile = findViewById(R.id.imgProfile);
        progressbar =  findViewById(R.id.progressBar);
        final EditText txtPhone = findViewById(R.id.txtPhone);
        final EditText txtAddress = findViewById(R.id.txtAddress);

        findViewById(R.id.btnPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(ProfileActivity.this , new String[]{Manifest.permission.CAMERA},REQ_CAMERA_PERM);
            }
        });
        findViewById(R.id.btnPickGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select image") , REQ_CODE_GALLERY);
            }
        });
        setTitle(new Settings(this).GetUsername() );
        final UserProfileService service = RetrofitClientInstance.getRetrofitInstance().create(UserProfileService.class);
        Call<UserProfileResult> callGet = service.getProfile("{\"userId\" :\""+new Settings(this).GetLoginInfo()+"\"}");
        callGet.enqueue(new Callback<UserProfileResult>() {
            @Override
            public void onResponse(Call<UserProfileResult> call, Response<UserProfileResult> response) {
                List<UserProfile> list = response.body().getResults();
                if(list.size() > 0){
                    UserProfile user = list.get( list.size() -1 );
                    txtAddress.setText( user.getAddress());
                    txtPhone.setText(user.getPhone());
                    byte[] picArr = user.getPic();
                    Bitmap bmp = BitmapFactory.decodeByteArray(picArr , 0 , picArr.length);
                    imgProfile.setImageBitmap(bmp);
                }
            }

            @Override
            public void onFailure(Call<UserProfileResult> call, Throwable t) {

            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);


                UserProfile userProfile = new UserProfile();
                userProfile.setAddress(txtAddress.getText().toString());
                userProfile.setPhone(txtPhone.getText().toString());
                userProfile.setPic(bitmapArray);
                userProfile.setUserId(new Settings(ProfileActivity.this).GetLoginInfo() );

                Call<ParseResponse> call = service.save(userProfile);

                call.enqueue(new Callback<ParseResponse>() {
                    @Override
                    public void onResponse(Call<ParseResponse> call, Response<ParseResponse> response) {
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ProfileActivity.this , getString(R.string.profileSaved),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ParseResponse> call, Throwable t) {
                        progressbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    void takePhoto(){
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentCamera.resolveActivity(getPackageManager())!=null)
            startActivityForResult(intentCamera , REQ_CODE_TAKE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQ_CAMERA_PERM){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }
        }
    }

    void convertBmpToArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG ,90 ,stream);
        bitmapArray = stream.toByteArray();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_TAKE_PHOTO && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgProfile.setImageBitmap(bitmap);

            convertBmpToArray(bitmap);
        }else  if(requestCode == REQ_CODE_GALLERY && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver() , selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(bitmap!=null) {
                imgProfile.setImageBitmap(bitmap);
                convertBmpToArray(bitmap);
            }
        }
    }
}
