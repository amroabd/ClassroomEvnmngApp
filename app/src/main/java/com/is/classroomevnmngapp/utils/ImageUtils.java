package com.is.classroomevnmngapp.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageUtils {

    //1-zoom photo
    public static void getZoom(ImageView imageView) {
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.canZoom();
        photoViewAttacher.update();
    }

    //2-less from size photo
    public static Bitmap getBtpOptions(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        return BitmapFactory.decodeFile(path, options);
    }

    /* return format either kb,mb,gb */
    public static String formatSize(long size) {
        String suffix = null;
        float size1 = 0.00F;
        size1 = size;
        if (size >= 1024) {
            suffix = "KB";
            size1 /= 1024;
            if (size1 >= 1024) {
                suffix = "MB";
                size1 /= 1024;
                if (size1 >= 1024) {
                    suffix = "GB";
                    size1 /= 1024;
                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Float.toString(size1));
      /*  int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }*/
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    // Retrieving the url to crop
    public static Uri getImageToCrop(String path, String nameFile, Context mContext) {
        File imageFolder = new File(Environment.getExternalStorageDirectory(), "IsApp/vm/crp");
        Uri uri = null;
        try {
            if (!imageFolder.exists())
                if (imageFolder.mkdirs()) Log.i("createFolderCrop", "SUCCESS_CREATED_FOLDER");
            File file = new File(imageFolder, "crop_image");
            //FileChannel fileChannel1 = (new FileInputStream(new File(path))).getChannel();
            //FileChannel fileChannel2 = (new FileOutputStream(file)).getChannel();
            //fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
            //fileChannel1.close();
            //fileChannel2.close();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileInputStream fileInputStream = new FileInputStream(path + nameFile);
            SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                    .generateSecret(new PBEKeySpec("IMG_MEMBER".toCharArray(), "IMG_MEMBER".getBytes(), 128, 256)).getEncoded(), "AES");
            @SuppressLint("GetInstance")
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKeySpec);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            byte[] arrayOfByte = new byte[1024];
            while (true) {
                int i = cipherInputStream.read(arrayOfByte);
                if (i != -1) {
                    fileOutputStream.write(arrayOfByte, 0, i);
                    continue;
                }
                cipherInputStream.close();
                fileOutputStream.close();
                return FileProvider.getUriForFile(mContext, "com.is.classroomevnmngapp.fileprovider", file);
            }
        } catch (Exception e) {
            Log.e("ERR_crp", "" + e.toString());
            Toast.makeText(mContext, "getImageToCrop" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    private static void decodeSecretFile(String path, String nameFile, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileInputStream fileInputStream = new FileInputStream(path + nameFile);
            SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                    .generateSecret(new PBEKeySpec("IMG_MEMBER".toCharArray(), "IMG_MEMBER".getBytes(), 128, 256)).getEncoded(), "AES");
            @SuppressLint("GetInstance")
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKeySpec);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            byte[] arrayOfByte = new byte[1024];
            while (true) {
                int i = cipherInputStream.read(arrayOfByte);
                if (i != -1) {
                    fileOutputStream.write(arrayOfByte, 0, i);
                    continue;
                }
                cipherInputStream.close();
                fileOutputStream.close();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retrieving the url to share
    public static Uri getImageToShare(String path, String nameFile, Context mContext) {
        File imageFolder = new File(mContext.getCacheDir(), "files");
        Uri uri = null;
        try {
            if (!imageFolder.exists())
                if (imageFolder.mkdirs()) Log.i("createFolderShare", "SUCCESS_CREATED_FOLDER");
            File file = new File(imageFolder, nameFile);

            FileChannel fileChannel1 = (new FileInputStream(new File(path + "/" + nameFile))).getChannel();
            FileChannel fileChannel2 = (new FileOutputStream(file)).getChannel();
            fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
            fileChannel1.close();
            fileChannel2.close();


            uri = FileProvider.getUriForFile(mContext, "com.is.classroomevnmngapp.fileprovider", file);
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    private static int calculateInSample(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int h = options.outHeight;
        final int w = options.outWidth;
        Log1.d("calculateInSample","w:"+w+",h:"+h);
        int inSampleSize = 1;
        if (h > reqHeight || w > reqWidth) {
            final int half_h = h / 2;
            final int half_w = w / 2;
            //calculate largest InSample value that is  power of 2 and keep h ,w larger the request
            while ((half_h / inSampleSize) >= reqHeight && (half_w / inSampleSize) >= reqWidth) {
                inSampleSize *= 4;//2
            }
        }
        return inSampleSize*2;
    }

    /***
     *
     * @param resources getResource()
     * @param resId (R.Drawable.img)
     * @param reqWidth exp:default 100px
     * @param reqHeight exp:default 100px
     * @return decode bitmap
     */
    public static Bitmap decodeSampleBitmapResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        //first decode with inJustDecodeBound=true check dimens
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        //calculate  InSampleSize
        options.inSampleSize = calculateInSample(options, reqWidth, reqHeight);
        //decode bit map with InSampleSize
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    public static void decodeSampleBitmapRPath(String path, int reqWidth, int reqHeight, ImageView imageView) {
        final Bitmap[] bmp = {null};
        //new Thread(new Runnable() {
        //  @Override
        //public void run() {
        //first decode with inJustDecodeBound=true check dimens
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //calculate  InSampleSize
        options.inSampleSize = calculateInSample(options, reqWidth, reqHeight);
        Log.d("inSmple", String.valueOf(calculateInSample(options, reqWidth, reqHeight)));
        //decode bit map with InSampleSize
        options.inJustDecodeBounds = false;
        bmp[0] = BitmapFactory.decodeFile(path, options);
        // }
        //}).start();
        bmp[0] = BitmapFactory.decodeFile(path, options);
        Log.d("siz", "" + bmp[0].getByteCount());
        imageView.setImageBitmap(bmp[0]);
    }
    public static void decodeSampleBitmapBytes(byte [] bytes, int reqWidth, int reqHeight, ImageView imageView) {
        final Bitmap[] bmp = {null};
        //first decode with inJustDecodeBound=true check dimens
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes,0,bytes.length, options);
        //calculate  InSampleSize
        options.inSampleSize = calculateInSample(options, reqWidth, reqHeight);
        Log.d("inSample", String.valueOf(calculateInSample(options, reqWidth, reqHeight)));
        //decode bit map with InSampleSize
        options.inJustDecodeBounds = false;
        bmp[0] = BitmapFactory.decodeByteArray(bytes,0,bytes.length, options);
        Log.d("siz", "" +formatSize(bmp[0].getByteCount()) );
        imageView.setImageBitmap(bmp[0]);
    }


    public static Bitmap getBitmapScale(Bitmap bitmap) {
        int w = 100;
        int h = 100;
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
        return bitmap;
    }

    //3-photo scale
    public static Bitmap getBitmapScale(String path, String nameFile) {
        int w = 96;
        int h = 96;
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        bitmap = BitmapFactory.decodeFile(path + nameFile, options);
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
        return bitmap;
    }

    /***
     * {date:22-10-3}
     * @param imgByte bytes img that resize
     * @param nameFile name file save result
     */
    public static void generateThumb(byte[] imgByte, String nameFile) {
        int w = 96;
        int h = 96;
        new Thread(new Runnable() {
            Bitmap bitmap;

            @Override
            public void run() {
                Log.i("G_thumb-1", "first-phrase generate thumb,that was[size: " + formatSize(imgByte.length));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
                FileUtils.saveThumb(bitmap, nameFile);
            }
        }).start();

    }

    //4-
    public static Bitmap resizeImageToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth = Math.min(height, 300);
        int newHeight = (height > width) ? height - (height - width) : 300;
        int cropW = (Math.abs(width - height)) / 2;
        cropW = Math.max(cropW, 0);
        int cropH = (Math.abs(height - width)) / 2;
        cropH = Math.max(cropH, 0);
        return Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);
    }

    //5-
    public static void setThumbImage(String imagePath, ImageView imageView, Context context) {
        try {
            //final int THUMB_SIZE = 32;
            //Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imagePath), THUMB_SIZE, THUMB_SIZE);
            imageView.setImageBitmap(loadContactPhotoThumbnail(imagePath, context));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    //6-
    public static void setThumbDrawableImage(int res_id, ImageView imageView, Context context) {
        try {
            final int THUMB_SIZE = 64;
            Bitmap thumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(context.getResources(), res_id), THUMB_SIZE, THUMB_SIZE);
            imageView.setImageBitmap(thumbImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a contact photo thumbnail and return it as a Bitmap,
     * resizing the image to the provided image dimensions as needed.
     *
     * @param photoData photo ID Prior to Honeycomb, the contact's _ID value.
     *                  For Honeycomb and later, the value of PHOTO_THUMBNAIL_URI.
     * @return A thumbnail Bitmap, sized to the provided width and height.
     * Returns null if the thumbnail is not found.
     */
    private static Bitmap loadContactPhotoThumbnail(String photoData, Context mContext) {
        // Creates an asset file descriptor for the thumbnail file.
        AssetFileDescriptor afd = null;
        // try-catch block for file not found
        try {
            // Creates a holder for the URI.
            Uri thumbUri;
            // If Android 3.0 or later
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // Sets the URI from the incoming PHOTO_THUMBNAIL_URI
                thumbUri = Uri.parse(photoData);
            } else {
                // Prior to Android 3.0, constructs a photo Uri using _ID
                /*
                 * Creates a contact URI from the Contacts content URI
                 * incoming photoData (_ID)
                 */
                final Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, photoData);
                /*
                 * Creates a photo URI by appending the content URI of
                 * Contacts.Photo.
                 */
                thumbUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }
            /*
             * Retrieves an AssetFileDescriptor object for the thumbnail
             * URI
             * using ContentResolver.openAssetFileDescriptor
             */
            afd = mContext.getContentResolver().openAssetFileDescriptor(thumbUri, "r");
            /*
             * Gets a file descriptor from the asset file descriptor.
             * This object can be used across processes.
             */
            FileDescriptor fileDescriptor = afd.getFileDescriptor();
            // Decode the photo file and return the result as a Bitmap
            // If the file descriptor is valid
            if (fileDescriptor != null) {
                // Decodes the bitmap
                return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, null);
            }
            // If the file isn't found
        } catch (FileNotFoundException e) {
            /*
             * Handle file not found errors
             */
            // In all cases, close the asset file descriptor
        } finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    //5- keep on resolution to image less scale
    public static Bitmap getResizeBitmap(Bitmap bitmap) {
        int new_w = 640;
        int new_h = 480;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scale_w = ((float) new_w) / width;
        float scale_h = ((float) new_h) / height;
        //create matrix
        Matrix matrix = new Matrix();
        //resize bit map
        matrix.postScale(scale_w, scale_h);
        //recreate new bitmap
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

    }

    /*

        public class FillSpace extends BitmapTransformation {
            private static final String ID = "com.bumptech.glide.transformations.FillSpace";
            private final byte[] ID_BYTES = ID.getBytes(StandardCharsets.UTF_8);

            @Override
            public Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                if (toTransform.getWidth() == outWidth && toTransform.getHeight() == outHeight) {
                    return toTransform;
                }
                return Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, true);
            }

            @Override
            public boolean equals(Object o) {
                return o instanceof FillSpace;
            }

            @Override
            public int hashCode() {
                return ID.hashCode();
            }

            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                messageDigest.update(ID_BYTES);
            }
        }
    */

    public static void shirkIcon(Context mContext,TextView textView, int rs) {
        @SuppressLint({"NewApi", "LocalSuppress", "UseCompatLoadingForDrawables"})
        Drawable drawable = mContext.getDrawable(rs);
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        int dif = Math.abs(w - 48) - Math.abs(h - 48);
        float scal = (dif > 0) ? 60 / (float) w : 60 / (float) h;
        Rect bounds = new Rect(0, 0, (int) (scal * w), (int) (scal * h));
        drawable.setBounds(bounds);

        textView.setPaddingRelative(0, 0, 5, 0);
        textView.setCompoundDrawablePadding(5);
        textView.setCompoundDrawablesRelative(null, null, drawable, null);
        // textView.setCloseIcon(drawable);
    }
}
