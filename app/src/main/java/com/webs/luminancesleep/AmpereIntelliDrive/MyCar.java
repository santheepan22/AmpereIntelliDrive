package com.webs.luminancesleep.AmpereIntelliDrive;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.bluetooth.BluetoothSocket;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.ToggleButton;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


// Button btnOn, btnOff, btnDis;
public class MyCar extends Fragment {

    Button btnOn, btnOff, Disconnect, AuthoriseCar, reverseButton, forwardButton;
    TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3, steeringTextView, throttleTextView;
    Handler bluetoothIn;
    SeekBar seekBarSteering, seekBarThrottle;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2mycar, container, false);
        //Link the buttons and textViews to respective views

/*        txtString = (TextView) v.findViewById(R.id.TextString);
        txtStringLength = (TextView) v.findViewById(R.id.TestView1);
        sensorView0 = (TextView) v.findViewById(R.id.sensorView0);
        sensorView1 = (TextView) v.findViewById(R.id.sensorView1);
        sensorView2 = (TextView) v.findViewById(R.id.sensorView2);
        sensorView3 = (TextView) v.findViewById(R.id.sensorView3);*/

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();							//get length of data received
                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                            sensorView0.setText(" Light Sensor = " + sensor0 + "Lux");	//update the textviews with sensor values
                            sensorView1.setText(" Rear Range Sensor = " + sensor1 + "cm");
                            sensorView2.setText(" Sensor 2 Voltage = " + sensor2 + "V");
                            sensorView3.setText(" Sensor 3 Voltage = " + sensor3 + "V");
                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();


        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED

/*        btnOff = (Button) v.findViewById(R.id.blueledoffbutton);
        btnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("0");    // Send "0" via Bluetooth
                Toast.makeText(getActivity().getApplicationContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
            }
        });

        btnOn = (Button) v.findViewById(R.id.blueledonbutton);
        btnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");    // Send "1" via Bluetooth
                Toast.makeText(getActivity().getApplicationContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
            }
        });*/





        Disconnect = (Button) v.findViewById(R.id.disconnectbutton);
        Disconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DisconnectProcedure();
            }
        });

       throttleTextView = (TextView)  v.findViewById(R.id.throttleTextView);
       seekBarThrottle = (SeekBar) v.findViewById(R.id.seekBarThrottle);
       seekBarThrottle.setOnSeekBarChangeListener(
               new SeekBar.OnSeekBarChangeListener() {
                   int throttleValue;
                   String firstThrottleDigit;
                   String finalThrottleDigit;
                   String bufferForThrottleFirstAndFinalDigitSwap = "";
                   int throttleValueLength = 0;
                   String sendThrottleValue = "";
                   String FinalSendThrottleValue = "";
                   String arrayForThrottleValue[];


                   @Override
                   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                       throttleValue = progress;
                       throttleTextView.setText("TV : " + progress + " / " +seekBarThrottle.getMax());
                       sendThrottleValue = Integer.toString(throttleValue);
                       throttleValueLength = sendThrottleValue.length();
/*                       arrayForThrottleValue = new String[throttleValueLength];
                       for(int i =0; i<throttleValueLength;i++){
                           arrayForThrottleValue[i] = sendThrottleValue.substring(i,i+1);
                       }
                       bufferForThrottleFirstAndFinalDigitSwap = arrayForThrottleValue[0];
                       arrayForThrottleValue[0] =  arrayForThrottleValue[throttleValueLength - 1];
                       arrayForThrottleValue[throttleValueLength - 1] = bufferForThrottleFirstAndFinalDigitSwap;
                       for(int i =0; i<throttleValueLength;i++){
                           FinalSendThrottleValue = arrayForThrottleValue[throttleValueLength - (1+i)] + FinalSendThrottleValue;
                       }*/
                       mConnectedThread.write("#");
                       mConnectedThread.write(sendThrottleValue);
                       mConnectedThread.write("@"); /*indicates the end of the transmission for throttle value*/


                   }

                   @Override
                   public void onStartTrackingTouch(SeekBar seekBar) {

                   }

                   @Override
                   public void onStopTrackingTouch(SeekBar seekBar) {

                   }
               }
       );

        steeringTextView = (TextView)  v.findViewById(R.id.steeringTextView);
        seekBarSteering = (SeekBar) v.findViewById(R.id.seekBarSteering);
        seekBarSteering.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int steeringValue;
                    String sendSteeringValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        steeringValue = progress;
                        steeringTextView.setText("SV : " + progress + " / " +seekBarSteering.getMax());
                        steeringValue = progress + 50;
                        sendSteeringValue = Integer.toString(steeringValue);
                        mConnectedThread.write("s");
                        mConnectedThread.write(sendSteeringValue);
                        mConnectedThread.write("@"); /*indicates the end of the transmission for throttle value*/
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );



        AuthoriseCar = (Button) v.findViewById(R.id.AuthoriseCarButton);
        AuthoriseCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("A");
                msg("Authorised");
            }
        });

        reverseButton = (Button) v.findViewById(R.id.reverseButton);
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConnectedThread.write("(");
            }
        });

        forwardButton = (Button) v.findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConnectedThread.write(")");
            }
        });


        return v;
    }

    private void DisconnectProcedure()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
                msg("Disconnected");
            }
            catch (IOException e) {
                e.printStackTrace();
                msg("Error Disconnecting");
            }
        }
    }





    private void msg(String s)
    {
        Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent newint = getActivity().getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = newint.getStringExtra(DeviceList.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getActivity().getApplicationContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                msg("Connection Failure. Returned to mainscreen");
                Intent i = new Intent(getActivity(), DeviceList.class);
                startActivity(i);
            }
        }
    }
}







