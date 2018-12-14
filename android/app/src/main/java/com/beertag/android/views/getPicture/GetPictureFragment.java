package com.beertag.android.views.getPicture;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.beertag.android.BuildConfig;
import com.beertag.android.R;
import com.beertag.android.utils.ImageEncoder;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetPictureFragment extends Fragment {

    private static final int TAKE_PICTURE_REQUEST_CODE = 3;


    @BindView(R.id.btn_camera)
    Button pictureButton;

    @BindView(R.id.iv_picture_view)
    ImageView pictureImageView;


    ImageEncoder mImageEncoder;

    @Inject
    public GetPictureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_picture, container, false);

        ButterKnife.bind(this, view);

        mImageEncoder = new ImageEncoder();
        Context context = getActivity();

        PackageManager packageManager = Objects.requireNonNull(context).getPackageManager();

        // checking if camera exist
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
        }

        // checking if camera is restricted to wark with the app and ask to change restricted permissions
        if (!checkPermissions(context)) {
            showPermissionsAlert(context);
        } else {
            Toast.makeText(getActivity(), "The device camera has all necessary permissions.", Toast.LENGTH_SHORT)
                    .show();
        }

        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePicture();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void makePicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Objects.requireNonNull(getActivity()).startActivityFromFragment(GetPictureFragment.this, cameraIntent, TAKE_PICTURE_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == TAKE_PICTURE_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null) {

                    Bitmap bmp = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if (bmp != null) {

                        //for saving
                        byte[] byteArray = stream.toByteArray();
                        String pictureString = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        //for viewing
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    }
                    pictureImageView.setImageBitmap(bmp);
                }

                Bitmap bmp = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (bmp != null) {


                    //for viewing
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                }
            }

        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e + "Getting picture from camera fails. Make picture again!", Toast.LENGTH_LONG).show();
        }
    }


    static boolean checkPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void showPermissionsAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", (dialog, which) -> openSettings(Objects.requireNonNull(getActivity())))
                .setNegativeButton("CANCEL", (dialog, which) -> {
                }).show();
    }

    static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

