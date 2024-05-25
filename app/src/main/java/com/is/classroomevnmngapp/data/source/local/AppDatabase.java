package com.is.classroomevnmngapp.data.source.local;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.is.classroomevnmngapp.data.source.local.dao.ControllerDao;
import com.is.classroomevnmngapp.data.source.local.dao.DepartmentDao;
import com.is.classroomevnmngapp.data.source.local.dao.DeviceDao;
import com.is.classroomevnmngapp.data.source.local.dao.HelperDao;
import com.is.classroomevnmngapp.data.source.local.dao.LectureHallDao;
import com.is.classroomevnmngapp.data.source.local.dao.ProfessorDao;
import com.is.classroomevnmngapp.data.source.local.dao.ReservationDao;
import com.is.classroomevnmngapp.data.source.local.dao.ScheduleDao;
import com.is.classroomevnmngapp.data.source.local.dao.UniversityDao;
import com.is.classroomevnmngapp.data.source.local.dao.UserDao;
import com.is.classroomevnmngapp.data.source.local.dao.UserTypeDao;
import com.is.classroomevnmngapp.data.source.local.entities.ControllerEntity;
import com.is.classroomevnmngapp.data.source.local.entities.DepartmentEntity;
import com.is.classroomevnmngapp.data.source.local.entities.DeviceEntity;
import com.is.classroomevnmngapp.data.source.local.entities.HelperEntity;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.data.source.local.entities.ProfessorEntity;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.data.source.local.entities.ScheduleEntity;
import com.is.classroomevnmngapp.data.source.local.entities.UniversityEntity;
import com.is.classroomevnmngapp.data.source.local.entities.UserEntity;
import com.is.classroomevnmngapp.data.source.local.entities.UserTypeEntity;

import org.jetbrains.annotations.NotNull;

@TypeConverters(DateConverter.class)
@Database(entities =
        {
                HelperEntity.MainMenu.class,
                HelperEntity.SubMenu.class,
                ControllerEntity.class,
                DepartmentEntity.class,
                DeviceEntity.class,
                LectureHallEntity.class,
                ProfessorEntity.class,
                ReservationEntity.class,
                ScheduleEntity.class,
                UniversityEntity.class,
                UserEntity.class,
                UserTypeEntity.class

        }, version = AppDatabase.END_VERSION,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    protected static final int START_VERSION = 1;
    protected static final int END_VERSION = 5;
    //------------------
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    protected static final String DATABASE_NAME = "UniAutomate_db";
    private static volatile AppDatabase instance;


    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance:Creating new database instance");
                instance = createDatabase(context);
            }
        }
        Log.d(TAG, "getInstance:Getting  database instance no need recreated it");
        return instance;
    }

    @NotNull
    private static AppDatabase createDatabase(@NotNull Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                //.setJournalMode(JournalMode.TRUNCATE)
                .fallbackToDestructiveMigration()/*.addMigrations(migration)*/.addCallback(mCallback).build();
    }

    //--here add abstract methods----------------------------------
    //public abstract BaseDao baseDao();

    public abstract HelperDao.MainMenu mainMenuDao();

    public abstract HelperDao.SubMenu subMenuDao();

    public abstract ControllerDao controllerDao();
    public abstract DepartmentDao departmentDao();
    public abstract DeviceDao deviceDao();
    public abstract LectureHallDao lectureHallDao();
    public abstract ProfessorDao professorDao();
    public abstract ReservationDao reservationDao();
    public abstract ScheduleDao scheduleDao();
    public abstract UniversityDao universityDao();
    public abstract UserDao userDao();
    public abstract UserTypeDao userTypeDao();


    private static final Migration migration = new Migration(START_VERSION, END_VERSION) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // database.execSQL("ALTER TABLE Bed_Data ADD COLUMN newColumn INTEGER NOT NULL DEFAULT 0");
            //change dataType For  column
            //database.execSQL("ALTER TABLE  Bed_Data ALTER COLUMN nameColumn  INTEGER NOT NULL DEFAULT 0 ");
        }
    };
    /***
     * populate database section
     */
    private static final RoomDatabase.Callback mCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //new PopulateDBAsync(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {
        // private final ReproductiveHealthDao mHealthDao;

        public PopulateDBAsync(@NotNull AppDatabase db) {
            // mHealthDao = db.reproductiveHealthDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }


}
