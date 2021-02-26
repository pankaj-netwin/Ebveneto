package com.ebveneto.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.DatePicker;


import com.ebveneto.interfaces.DateListener;

import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

    DatePicker datePicker;
    DatePickerDialog datePickerDialog;
    boolean isEndDate;
    DateListener dateListener;

    public void setDateListener(DateListener dateListener) {
        this.dateListener=dateListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the current date as the default date in the picker
        Locale.setDefault(Locale.ITALIAN);

        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(getArguments()!=null)
        {
            isEndDate= Boolean.parseBoolean(getArguments().get("isEndDate").toString());
            if(isEndDate)
            {
                c.set(Calendar.DAY_OF_MONTH, day);

            }
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog= new DatePickerDialog(getActivity(), this, year, month,  day);
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                arg0.dismiss();
                if(dateListener!=null)
                    dateListener.onDateSet(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            }
        });

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ANNULLA", new OnClickListener()
        {

            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                arg0.dismiss();
            }
        });
        datePicker=datePickerDialog.getDatePicker();
        //if(getArguments()!=null)
            datePicker.setMinDate(c.getTimeInMillis());
        return datePickerDialog;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {}


}