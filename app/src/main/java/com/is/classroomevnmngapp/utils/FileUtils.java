package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileUtils {
    private final Context mContext;
    private final static String name_folder_camera = "camera";
    private static final String nam_folder_Thumbs = "Thumbs";
    private final static String name_file_camera = "camera_image1";
    private static final String authorityFileProvider = "com.is.classroomevnmngapp.fileprovider";
    @SuppressLint("StaticFieldLeak")
    private static FileUtils mInstance;

    private FileUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static FileUtils getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new FileUtils(mContext);
        }
        return mInstance;
    }

    private static void isDirExitsOrCreate(File folder) {
        if (!folder.exists()) {
            Log1.v("CND","isCreateDir :"+folder.mkdirs());
        }
    }

    //return uri for
    public static Uri getUri(Context context) {
        File imageFolder = new File(GlobalData.F.path_Full_vm_Folder, name_folder_camera);
        isDirExitsOrCreate(imageFolder);
        File file = new File(imageFolder, name_file_camera);
        Uri uri;
        uri = FileProvider.getUriForFile(context, authorityFileProvider, file);
        return uri;
    }

    //return path gallery
    private static String getPath(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range")
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    //set result image select from gallery
    public static void setGlideImageFromGallery(Context context, Uri uri, ImageView imageView) {
        //Glide.with(context).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imageView);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            byte[] img = byteArrayOutputStream.toByteArray();
            //imageView.setImageBitmap(ImageUtils.resizeImageToSquare(bitmap));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(img, 0, img.length, options);
            imageView.setImageBitmap(bitmap1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set result image capture from camera
    public static void setGlideImageFromCamera(Context context, ImageView imageView) {
        //Glide.with(context).load(getUri(context)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imageView);
        String p1;
        p1 = GlobalData.F.path_Full_vm_Folder + "/" + name_folder_camera + "/" + name_file_camera;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(p1, options);
        imageView.setImageBitmap(bitmap);
    }

    /***
     * at take photo from camera
     * @return return imageBitMap
     */
    private static Bitmap getImageBitMap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        File imageFolder = new File(GlobalData.F.path_Full_vm_Folder, name_folder_camera);
        File file = new File(imageFolder, name_file_camera);
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static byte[] getBtp2Bytes(Bitmap paramBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /***
     * specific for camera
     * @return bytes image
     */
    public static byte[] get2Bytes70() {
        Bitmap paramBitmap = getImageBitMap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /***
     * specific for gallery
     * @return bytes image
     */
    public static byte[] get2Bytes70(Bitmap paramBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    /***
     * {date:22-10-3}
     * at each take photo generate thumb to photo
     * @param bitmap bitmap that will compress  resize
     * @param nameFile name file that will save result thumb img
     */
    public static void saveThumb(Bitmap bitmap, String nameFile) {
        try {
            detailImg(bitmap);
            File fold = new File(GlobalData.F.path_Full_vm_Folder , nam_folder_Thumbs);
            isDirExitsOrCreate(fold);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fold, nameFile));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Log1.i("thumb-2", "second ph-generate thumb compress and save in file [new size :" + ImageUtils.formatSize(bitmap.getByteCount()));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private static void detailImg(Bitmap bitmap) {
        Log.i("detailImg", "[getDensity :" + bitmap.getDensity() + "],[getHeight :" + bitmap.getHeight() +
                "],[getWidth :" + bitmap.getWidth());
    }

    /***
     * {date:22-10-3}
     * @param imageView img that will assign its thumb for display
     * @param path path folder that found thumbs in external storage
     * @param nameFile name thumb
     */
    public static void showThumb(ImageView imageView, String path, String nameFile) {
        File fold = new File(GlobalData.F.path_Full_vm_Folder + "/" + nam_folder_Thumbs);
        File file = new File(fold, nameFile);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Log.i("showThumb", file.getAbsolutePath());
            if (bitmap != null) {
                Log.i("2-showThumb", "yes");
                imageView.setImageBitmap(bitmap);
            } else {
                //  EncryptUtils.returnInFileWithSecret(path, nameFile, imageView);
            }
        } else {

            // EncryptUtils.returnInFileWithSecret(path, nameFile, imageView);
        }
    }

    public static void savePhotoDefault(byte[] imgBytes) throws IOException {
        File imageFolder = new File(GlobalData.F.path_Full_vm_Folder, name_folder_camera);
        //if (!imageFolder.exists())imageFolder.mkdirs();
        File file = new File(imageFolder, "camera_default.jpeg");
        FileOutputStream out = new FileOutputStream(file);
        out.write(imgBytes);
        out.close();
        out.flush();
    }

    public static void saveInfo2FileWithData(String nameFile, String data) {
        File folder = new File(GlobalData.F.path_Full_DB_Folder);
        isDirExitsOrCreate(folder);
        File file = new File(folder, nameFile + ".txt");
        try {
            data = nameFile + ":" + data;
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //
    public static void writeFileInternal(Context context) {
        // Activity app = (Activity) context;
        StringBuilder data = new StringBuilder("   in storage extranal ");
        try {
            //1
            FileOutputStream outputStream = context.openFileOutput("am1.text", Context.MODE_PRIVATE);
            outputStream.write(data.toString().getBytes());
            outputStream.close();
            //2
            //@SuppressLint("SdCardPath")
            File fold = new File(context.getFilesDir(), "subDir");
            isDirExitsOrCreate(fold);
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(new File(fold, "am22.text")));
            writer1.write("mmmmmmmgggggggg");
            writer1.close();
            //3
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput("am3.text", Context.MODE_PRIVATE)));
            writer.write("hillo miss  alkamali in storage");
            writer.close();

        } catch (Exception ioe) {
            ioe.printStackTrace();
            // ...
        }
    }

    public static void readFileInternal(Context context) {
        storageExternalPrivate(context);
        // Activity app = (Activity) context;
        StringBuilder data = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput("am3.text")));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
            Log.i("textFile", data.toString() + "null");
        } catch (Exception ioe) {
            ioe.printStackTrace();
            // ...
        }
    }

    public static void storageExternalPrivate(Context context) {
        // /storage/emulated/0/Android/data/com.is.classroomevnmngapp/files/
        File fold = new File(/*context.getExternalFilesDir(null)*/"/storage/emulated/0/Android", "subDir");

        try {
            if (!fold.exists()) {
                fold.mkdirs();
            }
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(new File(fold, "am22.text")));
            writer1.write("mmmmmmmgggggggg aaaaaaaaaaaaaaa");
            writer1.close();


            BufferedReader reader = new BufferedReader(new FileReader(new File(fold, "am22.text")));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("textFile2", line + "null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFile(File file, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(context, authorityFileProvider, file);
            intent.setData(uri);//Uri.fromFile(file)
            // String exe=file.getName().substring(file.getName().lastIndexOf(".")+1);
            //intent.setDataAndType(uri,"application/*");

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Can Not Open :" + e.toString(), Toast.LENGTH_LONG).show();
        }
       /*     File outFile=new File(getCacheDir(),"def");
        try {
        InputStream inputStream=new FileInputStream(file);
            FileOutputStream outputStream=new FileOutputStream(outFile);
            byte[]buffers=new byte[1024];
            int size;
            while ((size=inputStream.read(buffers))!=-1){
                outputStream.write(buffers,0,size);
            }
            inputStream.close();
            outputStream.close();

            ParcelFileDescriptor mFileDescriptor=ParcelFileDescriptor.open(outFile,ParcelFileDescriptor.MODE_READ_ONLY);
            if (mFileDescriptor!=null){
                Log.i("open","file");
                @SuppressLint({"NewApi", "LocalSuppress"})
                PdfRenderer mPdfRenderer=new PdfRenderer(mFileDescriptor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mPdfRenderer.openPage(0);
                }
                // mPdfRenderer.shouldScaleForPrinting()
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


}
