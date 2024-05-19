package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;

public class GlobalData {
    public static final String EXTRA_TYPE_FRAGMENT="TypeFragment";
    public static final int ACTION_ON_UPDATE_LIST_ITEM = 1;
    public static final int ACTION_ON_VIEW_LIST_ITEM = 2;

    public static final String urlServer = "";
    //
    public static String UUID= java.util.UUID.randomUUID().toString();
    public DecimalFormat num=new DecimalFormat("100000");
    private void b(){
        num.format(1);
    }
    public static final boolean isDebug=false;

    //
    public static class F{

        public final static String storageExternal="//"+ Environment.getExternalStorageDirectory().getAbsolutePath();
        public final static String ROOT_FOLDER_APP ="/AutomateApp/";//,Download
        public static final String FILE_FOLDER_APP="FilesDirs";
        public static final String IMG_FOLDER_APP="ImgDirs";
        public static final String DB_FOLDER_APP="backupDir";
        public static final String LOG_FOLDER_APP="logsDirs";
        public static final String EVENT_log_FOLDER_APP="/event_App";
        public static final String ERROR_log_FOLDER_APP="/errors_App";
        public static final String VM_FOLDER_APP ="VM";
        //full path
        public static final String path_Full_Files_Folder=p();//storageExternal+ROOT_FOLDER_APP+FILE_FOLDER_APP;
        public static final String path_Full_Img_Folder=p();//storageExternal+ROOT_FOLDER_APP+IMG_FOLDER_APP;
        public static final String path_Full_DB_Folder=storageExternal+ROOT_FOLDER_APP+DB_FOLDER_APP;
        public static final String path_Full_vm_Folder=storageExternal+ROOT_FOLDER_APP+ VM_FOLDER_APP;
        public static final String path_Full_Log_Folder=storageExternal+ROOT_FOLDER_APP+LOG_FOLDER_APP;
        public static final String path_Full_Error_Folder=p();//storageExternal+ROOT_FOLDER_APP+LOG_FOLDER_APP+ERROR_log_FOLDER_APP;
        public static final String path_Full_Event_Folder=p();//storageExternal+ROOT_FOLDER_APP+LOG_FOLDER_APP+EVENT_log_FOLDER_APP;

        public static void generatedFoldersApp(){
            createFolder(path_Full_Files_Folder);
            createFolder(path_Full_Img_Folder);
            createFolder(path_Full_DB_Folder);

            createFolder(path_Full_Event_Folder);
            createFolder(path_Full_Error_Folder);
            createFolder(path_Full_vm_Folder);
        }

        public static void createFolder(String path) {
            try {
                File folder = new File(path);
                boolean bool1;
                if (!folder.exists()) {
                    bool1 = folder.mkdirs();
                    Log.d("IS_TAG", "createFolder:" + bool1 + ",PATH:" + path);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static String createDirInternal(){
            @SuppressLint("SdCardPath") String p= "/data/user/0/com.is.classroomevnmngapp/files";
            File folder = new File(p);
            boolean bool1;
            if (!folder.exists()) {
                bool1 = folder.mkdirs();
                Log.d("IS_TAG", "createFolder:" + bool1 + ",PATH:" + folder.getPath());
            }
            return folder.getPath();
        }

        private static String p(){
            File folder = new File("/storage/emulated/0/Android/data/com.is.classroomevnmngapp/files/");
            boolean bool1;
            if (!folder.exists()) {
                bool1 = folder.mkdirs();
                Log.d("IS_TAG", "createFolder:" + bool1 + ",PATH:" + folder.getPath());
            }
            return folder.getPath();
        }

    }


}
