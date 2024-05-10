package com.is.classroomevnmngapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.ui.AdminMainActivity;
import com.is.classroomevnmngapp.utils.schedule.AlarmManagers;

import org.jetbrains.annotations.NotNull;


public class NotificationUtils {
    private static final String TAG = NotificationUtils.class.getSimpleName();

    abstract static class NotificationAbstract {
        protected String mChannel_id;
        //protected String mNotification_id="0x0001";
        protected String mLabel;
        protected String mLabel_progress;
        protected int[] mListDrawableRes;
        protected Context mContext;
        protected NotificationManagerCompat mNotificationManager;
        protected NotificationChannel mChannel;
        public NotificationCompat.Builder mNotification;

        public NotificationAbstract(Context mContext,String Channel_id) {
            this.mContext = mContext;
            this.mNotificationManager = NotificationManagerCompat.from(mContext);
            this.mListDrawableRes = new int[]{R.drawable.ic_file_download, R.drawable.ic_file_upload, R.drawable.ic_done_orange};
            this.mChannel_id =Channel_id==null? "all_notify_channel":Channel_id;
        }

        abstract void startNotification(String title, String notification_id);

        protected void progressNotification(double percentage, String notification_id) {
        }

        protected void finishNotification(String title, double percentage, String notification_id) {
        }

        /**
         * Creates a Notification channel, for VERSION_CODES 26 or higher.
         */
        protected void createNotificationChannel(String nameChannel, String desc, String notification_id) {
            Log1.d(TAG, "createNotificationChannel");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // Create the NotificationChannel with all the parameters.
                mChannel = new NotificationChannel(mChannel_id + notification_id, nameChannel + mChannel_id, NotificationManager.IMPORTANCE_DEFAULT);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setDescription(desc);
                mNotificationManager.createNotificationChannel(this.mChannel);
            }
        }

    }

    /***
     * 1-low level class
     */
    public static class DownNotification extends NotificationAbstract {

        public DownNotification(Context context) {
            super(context,"down_notify_channel");
            mLabel = context.getString(R.string.label_text_notify_down);
            mLabel_progress = context.getString(R.string.label_text_progress_notify);
        }


        @Override
        void startNotification(String title, String notification_id) {
            Log.i("DownNotification", "showNotification");
            //contentIntent
            Intent activityIntent = new Intent(mContext, AdminMainActivity.class);
            //this PendingIntent is using for do with app other
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //NotificationChannel
            createNotificationChannel(" جميع بيانات التحميل  ", "Notifies at start down data", notification_id);

            mNotification = new NotificationCompat.Builder(mContext, mChannel_id + notification_id)
                    .setContentTitle((mLabel + title))
                    .setContentText(mLabel_progress)
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setColor(Color.GREEN)
                    .setLargeIcon(BitmapUtils.getBitmapDrawable(mContext, mListDrawableRes[0]))
                    .setProgress(100, 0, true)
                    .setLights(Color.rgb(72, 72, 72), 1000, 800);
            mNotificationManager.notify(Integer.parseInt(notification_id), mNotification.build());

        }

        @Override
        protected void progressNotification(double percentage, String notification_id) {
            Log.i("DownNotification", "progressNotification");
            int t = (int) percentage;
            mNotification.setOngoing(true).setAutoCancel(false)//.setContentInfo(t + "%")
                    .setStyle(new NotificationCompat.InboxStyle().addLine("down data... percentage :" + t + "%"))
                    .setProgress(100, t, false);
            if (percentage < 99)
                mNotificationManager.notify(ConvertData.str2Integer(notification_id), mNotification.build());
        }

        @Override
        protected void finishNotification(String title, double percentage, String notification_id) {
            // When the loop is finished, updates the notification
            if (percentage > 90) {
                mNotification.setContentTitle((mLabel + title))
                        .setContentText("اكتمل " + mLabel + title).setContentInfo("100%")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(mLabel + title).setBigContentTitle("اكتمل " + mLabel + title))
                        .setSmallIcon(android.R.drawable.stat_sys_download_done)
                        //android.R.drawable.ic_popup_sync
                        .setColor(Color.GREEN).setOngoing(false)
                        .setLargeIcon(BitmapUtils.getBitmapDrawable(mContext, R.drawable.ic_done_orange))
                        // Removes the progress bar
                        .setProgress(0, 0, false)
                        .setLights(Color.rgb(72, 72, 72), 1000, 800);
                mNotificationManager.cancel(Integer.parseInt(notification_id));
            } else {
                mNotification.setContentText("فشل في " + mLabel + title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(mLabel + title).setBigContentTitle("فشل في " + mLabel + title))
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setColor(Color.RED).setOngoing(false)
                        .setLargeIcon(BitmapUtils.getBitmapDrawable(mContext, R.drawable.ic_false))
                        // Removes the progress bar
                        .setProgress(0, 0, false);
            }
            //if (notificationManager.areNotificationsEnabled())
            mNotificationManager.notify(ConvertData.str2Integer(notification_id), mNotification.build());

        }


    }

    /***
     * 2-low level class
     */
    public static class UploadNotification extends NotificationAbstract {

        public UploadNotification(Context context) {
            super(context,"upload_notify_channel");
            mLabel = "ترفيع بيانات الـ ";
            mLabel_progress = "جاري ترفيع ....";
        }

        @Override
        void startNotification(String title, String notification_id) {
            //NotificationChannel
            createNotificationChannel(" جميع بيانات ترفيع  ", "Notifies at start upload data", notification_id);

            mNotification = new NotificationCompat.Builder(mContext, mChannel_id + notification_id)
                    .setContentTitle((mLabel + title))
                    .setContentText(mLabel_progress)
                    .setSmallIcon(android.R.drawable.stat_sys_upload)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setColor(Color.GREEN)
                    .setLargeIcon(BitmapUtils.getBitmapDrawable(mContext, mListDrawableRes[1]))
                    .setProgress(100, 0, true)
                    .setLights(Color.rgb(72, 72, 72), 1000, 800);
            mNotificationManager.notify(Integer.parseInt(notification_id), mNotification.build());
        }

        @Override
        protected void progressNotification(double percentage, String notification_id) {
            int t = (int) percentage;
            mNotification.setOngoing(true).setAutoCancel(false)//.setContentInfo(t + "%")
                    .setStyle(new NotificationCompat.InboxStyle().addLine("upload data... percentage :" + t + "%"))
                    .setProgress(100, t, false);
            if (percentage < 99)
                mNotificationManager.notify(ConvertData.str2Integer(notification_id), mNotification.build());
        }

        @Override
        protected void finishNotification(String title, double percentage, String notification_id) {
            // When the loop is finished, updates the notification
            if (percentage > 90) {
                mNotification.setContentTitle((mLabel + title))
                        .setContentText("تم " + mLabel + title).setContentInfo("100%")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(mLabel + title).setBigContentTitle("تم " + mLabel + title))
                        .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                        //android.R.drawable.ic_popup_sync
                        .setColor(Color.GREEN).setOngoing(false)
                        .setLargeIcon(BitmapUtils.getBitmapDrawable(mContext, R.drawable.ic_done_orange))
                        // Removes the progress bar
                        .setProgress(0, 0, false)
                        .setLights(Color.rgb(72, 72, 72), 1000, 800);
                mNotificationManager.cancel(Integer.parseInt(notification_id));
            } else {
                mNotification.setContentText("فشل في " + mLabel + title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(mLabel + title).setBigContentTitle("فشل في " + mLabel + title))
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setColor(Color.RED).setOngoing(false)
                        .setLargeIcon(BitmapUtils.getBitmapDrawable(mContext, R.drawable.ic_false))
                        // Removes the progress bar
                        .setProgress(0, 0, false);
            }
            //if (notificationManager.areNotificationsEnabled())
            mNotificationManager.notify(ConvertData.str2Integer(notification_id), mNotification.build());

        }
    }

    /***
     * 3-low level class
     */
    public static class AlarmNotification extends NotificationAbstract {
        //public Notification mNotification;
        public AlarmNotification(Context mContext) {
            super(mContext,"alarm_notify_channel");
        }

        @Override
        void startNotification(String title, String notification_id) {
            Log1.d(TAG, "startNotification : Alarm  Class");
            //NotificationChannel
            createNotificationChannel(" Signed Fp  Notification  ", "Notifies every 15 minutes to stand up and walk", notification_id);
            Intent contentIntent = new Intent(mContext, AlarmManagers.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotification = new NotificationCompat.Builder(mContext, mChannel_id + notification_id)
                    .setSmallIcon(R.drawable.ic_event_note)
                    .setContentTitle("start signed Attend" + title)
                    .setContentText("you registration Attend")
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
            //mNotificationManager.notify(Integer.parseInt(notification_id), mNotification.build());
        }

    }


    public static void processShowNotification(@NotNull NotificationAbstract notificationAbstract, String title, String notification_id) {
        notificationAbstract.startNotification(title, notification_id);
    }

    public static void processProgressNotification(@NotNull NotificationAbstract notificationAbstract, double percentage, String notification_id) {
        notificationAbstract.progressNotification(percentage, notification_id);
    }

    public static void processFinishNotification(@NotNull NotificationAbstract notificationAbstract, String title, double percentage, String notification_id) {
        notificationAbstract.finishNotification(title, percentage, notification_id);
    }

    void test(Context context) {
        //Down
        DownNotification downNotification = new DownNotification(context);
        NotificationUtils.processShowNotification(downNotification, " data user ", "1");
        NotificationUtils.processProgressNotification(downNotification, 25.0, "1");
        NotificationUtils.processFinishNotification(downNotification, "", 100.00, "1");
        //up
        UploadNotification uploadNotification = new UploadNotification(context);
        NotificationUtils.processShowNotification(uploadNotification, " data user ", "1010");
        NotificationUtils.processProgressNotification(uploadNotification, 25.0, "1010");
        NotificationUtils.processFinishNotification(uploadNotification, "", 100.00, "1010");
    }


}
