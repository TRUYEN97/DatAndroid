package com.nextone.input.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.util.Range;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;

public class CameraModule extends Fragment {
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private static final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    private ImageCapture imageCapture;
    private Preview preview;
    private Context context;
    private PreviewView previewView;
    private LifecycleOwner lifecycle;
    private CameraSelector cameraSelector;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private boolean isStreaming = false;

    public CameraModule() {
    }

    public void init(Context ct, LifecycleOwner lc, PreviewView pv) {
        if (isStreaming) {
            return;
        }
        context = ct;
        lifecycle = lc;
        previewView = pv;
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions((Activity) context, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        // Cấu hình Preview
        preview = new Preview
                .Builder()
                .setTargetResolution(new Size(320, 240))
                .setTargetFrameRate(new Range<>(5, 10))
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;

        imageCapture = new ImageCapture.Builder()
                .setTargetResolution(new Size(1280, 720)) // Đặt độ phân giải chụp ảnh
                .build();
    }


    public void stop() {
        if (isStreaming) {
            // Giải phóng camera, unbind tất cả các use case khỏi lifecycle
            cameraProviderFuture = ProcessCameraProvider.getInstance(context);
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    // Unbind tất cả các use case khỏi lifecycle
                    cameraProvider.unbindAll();
                    isStreaming = false;
                    Log.d("CAMERA_MODULE", "Camera stream stopped.");
                } catch (Exception e) {
                    Log.d("CAMERA_MODULE", "Failed to stop camera stream: " + e.getMessage());
                }
            }, ContextCompat.getMainExecutor(context));
        }
    }

    public void start() {
        if (!isStreaming) {
            cameraProviderFuture = ProcessCameraProvider.getInstance(context);

            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                    Camera camera = cameraProvider.bindToLifecycle(
                            lifecycle,
                            cameraSelector,
                            imageCapture,
                            preview);
                    Log.i("Camera", "Camera started with resolution : " + preview.getResolutionInfo().toString());
                    isStreaming = true;

                } catch (Exception e) {
                    Log.d("CAMERA_MODULE", "can't start camera stream: " + e.getMessage());
                }
            }, ContextCompat.getMainExecutor(context));
        } else {
            Log.d("CAMERA_MODULE", "Camera stream already running.");
        }

    }

    public void takePhoto() {
        // Lấy đối tượng OutputFileOptions để chỉ định nơi lưu ảnh
        File photoFile = new File(context.getExternalFilesDir(null), "photo_" + System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        // Gọi phương thức takePicture để chụp ảnh và lưu vào file
        imageCapture.takePicture(
                options,
                ContextCompat.getMainExecutor(context),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        // Ảnh đã được lưu thành công
                        String msg = "Photo saved successfully: " + photoFile.getAbsolutePath();
                        Log.d("CAMERA_MODULE", msg);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        // Xử lý lỗi khi chụp ảnh
                        Log.d("CAMERA_MODULE", "Error saving photo: " + exception.getMessage());
                    }
                });
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Log.d("CAMERA_MODULE", "Camera permission denied");
                requireActivity().finish();
            }
        }
    }
}

