package com.beertag.android.views.image;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.beertag.android.BuildConfig;
import com.beertag.android.R;
import com.beertag.android.utils.ImageEncoder;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ImageViewFragment extends Fragment implements ImageViewContracts.View {

    @BindView(R.id.iv_image)
    ImageView mImageView;

    private ImageViewContracts.Presenter mPresenter;

    @Inject
    public ImageViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_view, container, false);
        ButterKnife.bind(this, view);

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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadImage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(ImageViewContracts.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showImage(Bitmap image) {
        mImageView.setImageBitmap(image);
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
