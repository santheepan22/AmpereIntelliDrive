package com.webs.luminancesleep.AmpereIntelliDrive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by santh on 05/03/2017.
 */

public class Help extends Fragment {

    private TimePicker mytimepicker;
    private Button ArmVioletLEDHourUntil, ArmVioletLEDMinuteUntil;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3help, container, false);

        mytimepicker = (TimePicker) v.findViewById(R.id.timePicker);
        ArmVioletLEDHourUntil = (Button) v.findViewById(R.id.ArmSetVioletLEDsUntil);
        ArmVioletLEDHourUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rightNow = Calendar.getInstance();

                int DisplayHour = mytimepicker.getHour();

                int currenthour = rightNow.get(Calendar.HOUR_OF_DAY);

                int HowLongSleepHours = (24-currenthour)+DisplayHour;
                String HoursToSleep = Integer.toString(HowLongSleepHours);
                Toast.makeText(getActivity().getApplicationContext(),HoursToSleep, Toast.LENGTH_SHORT).show();

                if (HowLongSleepHours == 2) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "M";
                } else if (HowLongSleepHours == 3) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "N";
                } else if (HowLongSleepHours == 4) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "O";
                } else if (HowLongSleepHours == 5) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "P";
                } else if (HowLongSleepHours == 6) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "Q";
                } else if (HowLongSleepHours == 7) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "R";
                } else if (HowLongSleepHours == 8) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "S";
                } else if (HowLongSleepHours == 9) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "T";
                } else if (HowLongSleepHours == 10) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "U";
                } else if (HowLongSleepHours == 11) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "V";
                } else if (HowLongSleepHours == 12) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "W";
                } else if (HowLongSleepHours == 13) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "X";
                } else if (HowLongSleepHours == 14) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "Y";
                } else if (HowLongSleepHours == 15) {
                    Global.GlobalVioletLEDOnUntilHourOutputID = "Z";
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Time entered is too long or short to sleep. Check AM/PM choice, refresh to reinput", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity().getApplicationContext(),Global.GlobalVioletLEDOnUntilHourOutputID, Toast.LENGTH_SHORT).show();

            }


        });

        ArmVioletLEDMinuteUntil = (Button) v.findViewById(R.id.ArmVioletUntilMinute);
        ArmVioletLEDMinuteUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rightNow = Calendar.getInstance();

                int DisplayMinute = mytimepicker.getMinute();

                int currentminute = rightNow.get(Calendar.MINUTE);
                int calculatedcurrentminute;
                calculatedcurrentminute = java.lang.Math.round(currentminute / 5); //Being 0 shouldn't have any problems for this division, should return 0 if 0
                int newcalculatedcurrentminute = (calculatedcurrentminute * 5);

                int CalculatedDisplayMinute = java.lang.Math.round(DisplayMinute / 5);
                int newCalculatedDisplayMinute = (CalculatedDisplayMinute * 5);

                int HowLongSleepAddedMinutes = (((60 - newcalculatedcurrentminute) + newCalculatedDisplayMinute));
                String AddedMinutesToSleep = Integer.toString(HowLongSleepAddedMinutes);
                Toast.makeText(getActivity().getApplicationContext(), AddedMinutesToSleep, Toast.LENGTH_SHORT).show();

                if (HowLongSleepAddedMinutes == 0) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "A";
                } else if (HowLongSleepAddedMinutes == 5) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "B";
                } else if (HowLongSleepAddedMinutes == 10) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "C";
                } else if (HowLongSleepAddedMinutes == 15) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "D";
                } else if (HowLongSleepAddedMinutes == 20) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "E";
                } else if (HowLongSleepAddedMinutes == 25) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "F";
                } else if (HowLongSleepAddedMinutes == 30) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "G";
                } else if (HowLongSleepAddedMinutes == 35) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "H";
                } else if (HowLongSleepAddedMinutes == 40) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "I";
                } else if (HowLongSleepAddedMinutes == 45) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "J";
                } else if (HowLongSleepAddedMinutes == 50) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "K";
                } else if (HowLongSleepAddedMinutes == 55) {
                    Global.GlobalVioletLEDOnUntilMinuteOutputID = "L";
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Time entered is too long to sleep. Check AM/PM choice, refresh to reinput", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity().getApplicationContext(),Global.GlobalVioletLEDOnUntilMinuteOutputID, Toast.LENGTH_SHORT).show();

            }


        });


        return v;
    }
}

