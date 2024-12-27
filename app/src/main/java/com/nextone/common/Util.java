package com.nextone.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {

    private static final String TAG = "Util";

    public static long getTestTime(long startMs, long endMs) {
        if (startMs <= 0) {
            return 0;
        }
        if (endMs >= startMs) {
            return (endMs - startMs);
        }
        return (System.currentTimeMillis() - startMs);
    }

    public static int getGearBoxVal(boolean s1, boolean s2, boolean s3, boolean s4) {
        if (s3) {
            if (s1) {
                return 3;
            }
            if (s2) {
                return 4;
            }
            if (s4) {
                return 5;
            }
        }
        if (s1) {
            return 1;
        }
        if (s2) {
            return 2;
        }
        return 0;
    }

    public static byte[] convertBitmapToBytes(Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void copyFile(Path source, Path target, CopyOption... options) throws IOException {
        if (target.getParent() != null) {
            target.getParent().toFile().mkdirs();
        }
        Files.copy(source, target, options);
    }

    public static void deleteFolder(File root) {
        if (!root.exists()) {
            return;
        }
        File[] files = root.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                }
                file.delete();
            }
        }
        root.delete();
    }

    public static String stringToMd5(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    public static String md5File(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(bytes);
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    public static String md5File(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
            byte[] hash = digest.digest();
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("Error computing MD5", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String md5File(String filePath) {
        try (FileInputStream input = new FileInputStream(filePath)) {
            return md5File(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            return null;
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Log.e(TAG, "Delay interrupted: " + ex.getMessage());
        }
    }

}
