package com.is.classroomevnmngapp.core.light_control_bt;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.is.classroomevnmngapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ControllingActivity extends AppCompatActivity {
    private static final String TAG = "BlueTest5-Controlling";
    private int mMaxChars = 50000;//Default//change this to string..........
    private UUID mDeviceUUID;
    private BluetoothSocket mBTSocket;
    private ReadInput mReadThread = null;

    private boolean mIsBluetoothConnected = false;


    private Button mBtnDisconnect;
    private BluetoothDevice mDevice;

    final static String on = "92";//on
    final static String off = "79";//off


    private ProgressDialog progressDialog;
    Button btnOn, btnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlling);

        ActivityHelper.initialize(this);
        // mBtnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        btnOn = (Button) findViewById(R.id.on);
        btnOff = (Button) findViewById(R.id.off);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mDevice = b.getParcelable(BluetoothMainActivity.DEVICE_EXTRA);
        mDeviceUUID = UUID.fromString(b.getString(BluetoothMainActivity.DEVICE_UUID));
        mMaxChars = b.getInt(BluetoothMainActivity.BUFFER_SIZE);

        Log.d(TAG, "Ready");


        btnOn.setOnClickListener(v -> {
           // TODO Auto-generated method stub
            try {
                mBTSocket.getOutputStream().write(on.getBytes());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        btnOff.setOnClickListener(v -> {
         // TODO Auto-generated method stub
            try {
                mBTSocket.getOutputStream().write(off.getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });


    }

    private class ReadInput implements Runnable {

        private boolean bStop = false;
        private Thread t;

        public ReadInput() {
            t = new Thread(this, "Input Thread");
            t.start();
        }

        public boolean isRunning() {
            return t.isAlive();
        }

        @Override
        public void run() {
            InputStream inputStream;
            try {
                inputStream = mBTSocket.getInputStream();
                while (!bStop) {
                    byte[] buffer = new byte[256];
                    if (inputStream.available() > 0) {
                        inputStream.read(buffer);
                        int i = 0;
                        /*
                         * This is needed because new String(buffer) is taking the entire buffer i.e. 256 chars on Android 2.3.4 http://stackoverflow.com/a/8843462/1287554
                         */
                        for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
                        }
                        final String strInput = new String(buffer, 0, i);

                        /*
                         * If checked then receive text, better design would probably be to stop thread if unchecked and free resources, but this is a quick fix
                         */


                    }
                    Thread.sleep(500);
                }
            } catch (IOException | InterruptedException e) {
             // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void stop() {
            bStop = true;
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class DisConnectBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {//cant inderstand these dotss

            if (mReadThread != null) {
                mReadThread.stop();
                while (mReadThread.isRunning()) ; // Wait until it stops
                mReadThread = null;
            }

            try {
                mBTSocket.close();
            } catch (IOException e) {
               // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mIsBluetoothConnected = false;
            boolean mIsUserInitiatedDisconnect = false;
            if (mIsUserInitiatedDisconnect) {
                finish();
            }
        }

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (mBTSocket != null && mIsBluetoothConnected) {
            new DisConnectBT().execute();
        }
        Log.d(TAG, "Paused");
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mBTSocket == null || !mIsBluetoothConnected) {
            new ConnectBT().execute();
        }
        Log.d(TAG, "Resumed");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopped");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
      // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @SuppressLint("StaticFieldLeak")
    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean mConnectSuccessful = true;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ControllingActivity.this, "Hold on", "Connecting");
            // http://stackoverflow.com/a/11130220/1287554
        }

        @SuppressLint("MissingPermission")
        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (mBTSocket == null || !mIsBluetoothConnected) {
                    mBTSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mBTSocket.connect();
                }
            } catch (IOException e) {
                // Unable to connect to device`
                // e.printStackTrace();
                mConnectSuccessful = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!mConnectSuccessful) {
                Toast.makeText(getApplicationContext(), "Could not connect to device.Please turn on your Hardware", Toast.LENGTH_LONG).show();
                finish();
            } else {
                msg("Connected to device");
                mIsBluetoothConnected = true;
                mReadThread = new ReadInput(); // Kick off input reader
            }

            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}