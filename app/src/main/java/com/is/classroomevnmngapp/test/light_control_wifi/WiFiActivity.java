package com.is.classroomevnmngapp.core.light_control_wifi;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.is.classroomevnmngapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple UI demonstrating how to open a serial communication link to a
 * remote host over WiFi, send and receive messages, and update the display.
 * <p>
 * Author: Hayk Martirosyan
 */
public class WiFiActivity extends AppCompatActivity {

    // Tag for logging
    private final String TAG = getClass().getSimpleName();

    // AsyncTask object that manages the connection in a separate thread
    WiFiSocketTask wifiTask = null;

    // UI elements
    TextView textStatus, textRX, textTX;
    EditText editTextAddress, editTextPort, editSend;
    Button buttonConnect, buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi);

        // Save references to UI elements
        textStatus = findViewById(R.id.textStatus);
        textRX = findViewById(R.id.textRX);
        textTX = findViewById(R.id.textTX);
        editTextAddress = findViewById(R.id.address);
        editTextPort = findViewById(R.id.port);
        editSend = findViewById(R.id.editSend);
        buttonConnect = findViewById(R.id.connect);
        buttonSend = findViewById(R.id.buttonSend);

        // Disable send button until a connection is made
        buttonSend.setEnabled(false);
    }

    /**
     * Helper function, print a status to both the UI and program log.
     */
    void setStatus(String s) {
        Log.v(TAG, s);
        textStatus.setText(s);
    }

    /**
     * Try to start a connection with the specified remote host.
     */
    public void connectButtonPressed(View v) {

        if (wifiTask != null) {
            setStatus("Already connected!");
            return;
        }
        try {
            // Get the remote host from the UI and start the thread
            String host = editTextAddress.getText().toString().trim();
            int port = Integer.parseInt(editTextPort.getText().toString());
            // Start the asynchronous task thread
            setStatus("Attempting to connect...");
            wifiTask = new WiFiSocketTask(host, port);
            wifiTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
            setStatus("Invalid address/port!");
        }
    }

    /**
     * Disconnect from the connection.
     */
    public void disconnectButtonPressed(View v) {
        if (wifiTask == null) {
            setStatus("Already disconnected!");
            return;
        }
        wifiTask.disconnect();
        setStatus("Disconnecting...");
    }

    /**
     * Invoked by the AsyncTask when the connection is successfully established.
     */
    private void connected() {
        setStatus("Connected.");
        buttonSend.setEnabled(true);
    }

    /**
     * Invoked by the AsyncTask when the connection ends..
     */
    private void disconnected() {
        setStatus("Disconnected.");
        buttonSend.setEnabled(false);
        textRX.setText("");
        textTX.setText("");
        wifiTask = null;
    }

    /**
     * Invoked by the AsyncTask when a newline-delimited message is received.
     */
    private void gotMessage(String msg) {
        textRX.setText(msg);
        Log.v(TAG, "[RX] " + msg);
    }

    /**
     * Send the message typed in the input field using the AsyncTask.
     */
    public void sendButtonPressed(View v) {

        if (wifiTask == null) return;

        String msg = editSend.getText().toString();
        if (msg.length() == 0) return;

        wifiTask.sendMessage(msg);
        editSend.setText("");

        textTX.setText(msg);
        Log.v(TAG, "[TX] " + msg);
    }

    /**
     * AsyncTask that connects to a remote host over WiFi and reads/writes the connection
     * using a socket. The read loop of the AsyncTask happens in a separate thread, so the
     * main UI thread is not blocked. However, the AsyncTask has a way of sending data back
     * to the UI thread. Under the hood, it is using Threads and Handlers.
     */
    @SuppressLint("StaticFieldLeak")
    public class WiFiSocketTask extends AsyncTask<Void, String, Void> {

        // Location of the remote host
        String address;
        int port;

        // Special messages denoting connection status
        private static final String PING_MSG = "SOCKET_PING";
        private static final String CONNECTED_MSG = "SOCKET_CONNECTED";
        private static final String DISCONNECTED_MSG = "SOCKET_DISCONNECTED";

        Socket socket = null;
        BufferedReader inStream = null;
        OutputStream outStream = null;

        // Signal to disconnect from the socket
        private boolean disconnectSignal = false;

        // Constructor
        WiFiSocketTask(String address, int port) {
            this.address = address;
            this.port = port;
        }

        /**
         * Main method of AsyncTask, opens a socket and continuously reads from it
         */
        @Override
        protected Void doInBackground(Void... arg) {
            try {
                // Open the socket and connect to it
                socket = new Socket();
                // Socket timeout - close if no messages received (ms)
                int timeout = 5000;
                socket.connect(new InetSocketAddress(address, port), timeout);

                // Get the input and output streams
                inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outStream = socket.getOutputStream();

                // Confirm that the socket opened
                if (socket.isConnected()) {
                    // Make sure the input stream becomes ready, or timeout
                    long start = System.currentTimeMillis();
                    while (!inStream.ready()) {
                        long now = System.currentTimeMillis();
                        if (now - start > timeout) {
                            Log.e(TAG, "Input stream timeout, disconnecting!");
                            disconnectSignal = true;
                            break;
                        }
                    }
                } else {
                    Log.e(TAG, "Socket did not connect!");
                    disconnectSignal = true;
                }

                // Read messages in a loop until disconnected
                while (!disconnectSignal) {

                    // Parse a message with a newline character
                    String msg = inStream.readLine();

                    // Send it to the UI thread
                    publishProgress(msg);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error in socket thread!");
            }

            // Send a disconnect message
            publishProgress(DISCONNECTED_MSG);

            // Once disconnected, try to close the streams
            try {
                if (socket != null) socket.close();
                if (inStream != null) inStream.close();
                if (outStream != null) outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * This function runs in the UI thread but receives data from the
         * doInBackground() function running in a separate thread when
         * publishProgress() is called.
         */
        @Override
        protected void onProgressUpdate(String... values) {

            String msg = values[0];
            if (msg == null) return;

            // Handle meta-messages
            if (msg.equals(CONNECTED_MSG)) {
                connected();
            } else if (msg.equals(DISCONNECTED_MSG))
                disconnected();
            else if (msg.equals(PING_MSG)) {
            }

            // Invoke the gotMessage callback for all other messages
            else
                gotMessage(msg);

            super.onProgressUpdate(values);
        }

        /**
         * Write a message to the connection. Runs in UI thread.
         */
        public void sendMessage(String data) {

            try {
                outStream.write(data.getBytes());
                outStream.write('\n');
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Set a flag to disconnect from the socket.
         */
        public void disconnect() {
            disconnectSignal = true;
        }
    }

    void serverSocketDefault()  {
        try {
            ServerSocket serverSocket=new ServerSocket(6969);
            //Listens for a connection
            Socket socket=serverSocket.accept();

            //receive: read data from client inputStream allows to read data at low level :read to byte
            InputStream inputStream=socket.getInputStream();
            //InputStreamReader: read data higher level :read  single char ,read line of text
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            //send to client
            OutputStream outputStream=socket.getOutputStream();
            //wrap OutputStream by PrintWriter to send data in text format
            PrintWriter printWriter=new PrintWriter(outputStream,true);

            String  text;
            do {
                text=bufferedReader.readLine();
                printWriter.println("Server sent :"+text);
            }while (!text.equals("exit"));

            //
            socket.close();

            //
            serverSocket.close();

        }catch (Exception e){

        }

    }
}