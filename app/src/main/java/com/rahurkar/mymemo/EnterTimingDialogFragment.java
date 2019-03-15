package com.rahurkar.mymemo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rahurkar.mymemo.pojo.TimeModel;
import com.rahurkar.mymemo.util.MultiSpinner;
import com.rahurkar.mymemo.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnterTimingDialogFragment extends AppCompatDialogFragment implements MultiSpinner.MultiSpinnerListener {
    private static final String TAG = EnterTimingDialogFragment.class.getSimpleName();
    TimeModel mTimeModel;
    List<TimeModel> timeModelList;
    boolean[] mSelected;
    Util.OnDialogComplete mOnDialogComplete;
    int mPosition;

    public EnterTimingDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EnterTimingDialogFragment newInstance(Util.OnDialogComplete onDialogComplete, int position) {
        
        Bundle args = new Bundle();
        
        EnterTimingDialogFragment fragment = new EnterTimingDialogFragment();
        fragment.mOnDialogComplete = onDialogComplete;
        fragment.setArguments(args);
        fragment.mPosition = position;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return dialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enter_timing, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MultiSpinner multiSpinner = view.findViewById(R.id.multi_spinner);
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.days)));
        multiSpinner.setItems(arrayList, getString(R.string.select_day), this);

        mTimeModel = new TimeModel();
        final Button startTimeButton = view.findViewById(R.id.start_time_button);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EnterTimingDialogFragment.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTimeModel.setStartHour(hourOfDay);
                        mTimeModel.setStartMinutes(minute);
                        String value = Util.getDisplayTime(hourOfDay) + ":" + Util.getDisplayTime(minute);
                        startTimeButton.setText(value);
                    }
                }, 12, 0, false);

                timePickerDialog.show();
            }
        });

        final Button endTimeButton = view.findViewById(R.id.end_time_button);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EnterTimingDialogFragment.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTimeModel.setEndHour(hourOfDay);
                        mTimeModel.setEndMinutes(minute);
                        String value = Util.getDisplayTime(hourOfDay) + ":" + Util.getDisplayTime(minute);
                        endTimeButton.setText(value);
                    }
                }, 12, 0, false);

                timePickerDialog.show();
            }
        });

        Button doneButton = view.findViewById(R.id.done_fragment_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelected != null && mSelected.length > 0 && startTimeButton.getText().toString().contains(":") && endTimeButton.getText().toString().contains(":")) {
                    boolean hasValidDay = false;
                    List<TimeModel> timeModelList = new ArrayList<>();
                    for (int i = 0; i < mSelected.length; i ++) {
                        if (mSelected[i]) {
                            TimeModel timeModel = new TimeModel();
                            timeModel.setValueFromObject(mTimeModel);
                            timeModel.setDayOfWeek(i);
                            timeModelList.add(timeModel);
                            hasValidDay = true;
                        }
                    }
                    if (!hasValidDay) {
                        Toast.makeText(EnterTimingDialogFragment.this.getContext(), R.string.please_enter_valid_day, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (mOnDialogComplete == null) {
                        Toast.makeText(EnterTimingDialogFragment.this.getContext(), R.string.please_try_again, Toast.LENGTH_LONG).show();
                        EnterTimingDialogFragment.this.dismiss();
                    }
                    mOnDialogComplete.onDialogComplete(timeModelList, mPosition);
                    EnterTimingDialogFragment.this.dismiss();
                } else{
                    Toast.makeText(EnterTimingDialogFragment.this.getContext(), R.string.check_values, Toast.LENGTH_LONG).show();
                }
            }
        });
        // Get field from view
//        mEditText = (EditText) view.findViewById(R.id.txt_your_name);

    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        mSelected = selected;
    }
}
