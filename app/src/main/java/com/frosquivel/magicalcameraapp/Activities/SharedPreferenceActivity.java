package com.frosquivel.magicalcameraapp.Activities;
/*
Copyright 2012 Lars Werkman

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.frosquivel.magicalcameraapp.R;
import com.frosquivel.magicalcameraapp.Utils.Utils;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Slider;

/**
 * Created by Fabian on 11/03/2017.
 * This class have all the configuration of the app for use magical camera dynamically
 */

public class SharedPreferenceActivity extends AppCompatActivity {

    private Button btnColor;
    private EditText txtHexadecimalColor;
    private EditText txtLineThickness;
    private EditText txtTitleSelectPictureName;
    private EditText txtDirectoryName;
    private EditText txtPhotoName;
    private Switch switchAutoIncrement;
    private Slider sliderQuality;
    private RadioGroup radioTypeExtension;
    private RadioButton radioExtensionWEBP;
    private RadioButton radioExtensionPNG;
    private RadioButton radioExtensionJPEG;
    private Context context;
    private TextView noteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        context = this;
        btnColor = (Button) findViewById(R.id.btnColor);
        txtHexadecimalColor = (EditText) findViewById(R.id.txtHexadecimalColor);
        txtLineThickness = (EditText) findViewById(R.id.txtLineThickness);
        txtTitleSelectPictureName = (EditText) findViewById(R.id.txtTitleSelectPictureName);
        txtDirectoryName = (EditText) findViewById(R.id.txtDirectoryName);
        txtPhotoName = (EditText) findViewById(R.id.txtPhotoName);
        switchAutoIncrement = (Switch) findViewById(R.id.switchAutoIncrement);
        sliderQuality = (Slider) findViewById(R.id.sliderQuality);
        radioTypeExtension = (RadioGroup) findViewById(R.id.radioTypeExtension);
        radioExtensionWEBP = (RadioButton) findViewById(R.id.radioExtensionWEBP);
        radioExtensionPNG = (RadioButton) findViewById(R.id.radioExtensionPNG);
        radioExtensionJPEG = (RadioButton) findViewById(R.id.radioExtensionJPEG);
        noteFragment = (TextView) findViewById(R.id.noteFragment);
        noteFragment.setVisibility(View.VISIBLE);
        valuesSharedPreference();
        listeners();
    }

    private void valuesSharedPreference(){
        txtDirectoryName.setText(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_DIRECTORY_NAME));
        txtPhotoName.setText(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_PHOTO_NAME));
        txtTitleSelectPictureName.setText(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_SELECTED_PICTURE));
        txtLineThickness.setText(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_THICK));
        txtHexadecimalColor.setText(
                Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR).replace("#",""));
        sliderQuality.setValue(
                Float.parseFloat(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_QUALITY_PICTURE)),true);

        if(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_FORMAT).equals(Utils.C_PNG)){
            radioExtensionPNG.setChecked(true);
        }else if(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_FORMAT).equals(Utils.C_JPG)){
            radioExtensionJPEG.setChecked(true);
        }else{
            radioExtensionWEBP.setChecked(true);
        }

        switchAutoIncrement.setChecked(Boolean.parseBoolean(Utils.getSharedPreference(context,Utils.C_PREFERENCE_MC_AUTO_IC_NAME)));
        try {
            btnColor.setBackgroundColor(
                    Color.parseColor(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR))
            );
        }catch(Exception ev){}
    }

    private void listeners(){
        txtDirectoryName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_DIRECTORY_NAME, txtDirectoryName.getText().toString());
            }
        });

        txtPhotoName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_PHOTO_NAME, txtPhotoName.getText().toString());
            }
        });

        txtTitleSelectPictureName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_SELECTED_PICTURE, txtTitleSelectPictureName.getText().toString());
            }
        });

        txtLineThickness.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_THICK, txtLineThickness.getText().toString());
            }
        });

        txtHexadecimalColor.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR, "#" + txtHexadecimalColor.getText().toString());
                try {
                    btnColor.setBackgroundColor(
                            Color.parseColor(Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR))
                    );
                }catch(Exception ev){}
            }
        });

        sliderQuality.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_QUALITY_PICTURE, String.valueOf(newValue));
            }
        });

        switchAutoIncrement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_AUTO_IC_NAME, String.valueOf(isChecked));
            }
        });

        radioExtensionWEBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_FORMAT, Utils.C_WEBP);
            }
        });

        radioExtensionPNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_FORMAT, Utils.C_PNG);
            }
        });

        radioExtensionJPEG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_FORMAT, Utils.C_JPG);
            }
        });

        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upPopUpColor(popUpLayout(R.layout.popup_color_picker));
            }
        });
    }



    private void upPopUpColor(View popup) {

        ColorPicker picker = (ColorPicker) popup.findViewById(R.id.picker);
        SVBar svBar = (SVBar) popup.findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) popup.findViewById(R.id.opacitybar);
        SaturationBar saturationBar = (SaturationBar) popup.findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) popup.findViewById(R.id.valuebar);

        int color = 0;

        try{
            color = Color.parseColor(Utils.getSharedPreference(context,Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR));
        }catch(Exception ev){}

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);
        picker.setColor(color);
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                btnColor.setBackgroundColor(color);
                Utils.setSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR,String.format("#%06X", (0xFFFFFF & color)));
                txtHexadecimalColor.setText(
                        Utils.getSharedPreference(context, Utils.C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR).replace("#",""));
            }
        });

        picker.setShowOldCenterColor(false);
    }



    private View popUpLayout(final int idLayout) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.shared_change_color)
                .customView(idLayout, true)
                .positiveText(R.string.shared_accept)
                .autoDismiss(false)
                .backgroundColor(getResources().getColor(R.color.colorWhite))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                        materialDialog.cancel();
                    }
                })

                .show();

        return dialog.getCustomView();
    }
}
