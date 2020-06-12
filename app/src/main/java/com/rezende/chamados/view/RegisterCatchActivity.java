package com.rezende.chamados.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rezende.chamados.R;
import com.rezende.chamados.controller.ControllerInformationUrgency;
import com.rezende.chamados.model.InformationUrgencyBEAN;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterCatchActivity extends AppCompatActivity {

    //private static final String NAME_TABLE_REFERENCE = "catch";
    private static final String TAG = "RegisterCatchActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 101;
    private ImageView imageViewCapture;
    private VideoView videoViewCapture;

    private ControllerInformationUrgency controller;
    private InformationUrgencyBEAN info;
    private Uri videoUri;
    private String picture;
    private String video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_catch);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent i = getIntent();
        info = (InformationUrgencyBEAN) i.getSerializableExtra("descriptionsType");
        controller = new ControllerInformationUrgency();


        imageViewCapture = findViewById(R.id.image_view_capture);
        videoViewCapture = findViewById(R.id.video_view_capture);
        Button btnPhoto = findViewById(R.id.button_photo);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (image.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(image, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        Button btnVideo = findViewById(R.id.button_video);
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (video.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(video, REQUEST_VIDEO_CAPTURE);
                }
            }
        });

        Button btnFinish = findViewById(R.id.button_submit);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateMidias() == 0){
                    Toast.makeText(getApplicationContext(), "Por favor registre uma imagem ou video!", Toast.LENGTH_LONG).show();
                }else {
                    controller.addInformationUrgency(info, "urgency");
                    Toast.makeText(getApplicationContext(), "Chamado enviado!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private int validateMidias() {
        int cont = 0;
        if(imageViewCapture.getDrawable() != null) {
            saveImageInFirebase();
            //saveVideoInFirebase();
            //addmedia();
            cont++;
        }
        if(getVideoUri() != null) {
            //saveImageInFirebase();
            saveVideoInFirebase();
            //addmedia();
            cont++;
        }
        return cont;
    }

    private void saveVideoInFirebase() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference("/video/"+"MP4"+timeStamp+".mp4");

        Uri file = getVideoUri();
        storageRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setVideo(uri.toString());
                        addVideo();
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data"); //extras.get("data");

            imageViewCapture.setImageBitmap(imageBitmap);

        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            setVideoUri(data.getData());

            //videoViewCapture.stopPlayback();
            //videoViewCapture.releasePointerCapture();
            videoViewCapture.setMediaController(new MediaController(this));

            videoViewCapture.setVideoURI(getVideoUri());
            videoViewCapture.requestFocus();

            videoViewCapture.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                        //Thread.sleep(5000);
                        //mp.setLooping(true);
                        videoViewCapture.start();
                }
            });
            //videoViewCapture.setMediaController(new MediaController(this));
            //videoViewCapture.start();
            //videoViewCapture.setVideoPath(String.valueOf(getVideoUri()));
        }
    }

    private void saveImageInFirebase() {
        try {
            // Create a storage reference from our app
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            final StorageReference storageRef = FirebaseStorage.getInstance().getReference("/img/"+"IMG"+timeStamp+".jpeg");

            Bitmap bitmap = ((BitmapDrawable) imageViewCapture.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            storageRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            setPicture(uri.toString());
                            addPicture();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPicture() {
        info.setPicture(getPicture());
        controller.editInformationUrgency(info);
    }

    private void addVideo() {
        info.setVideo(getVideo());
        controller.editInformationUrgency(info);
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }

    private String getPicture() {
        return picture;
    }

    private void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
