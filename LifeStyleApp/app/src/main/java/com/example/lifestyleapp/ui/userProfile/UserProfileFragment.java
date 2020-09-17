package com.example.lifestyleapp.ui.userProfile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.lifestyleapp.CreateAccountActivity;
import com.example.lifestyleapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class UserProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private UserProfileViewModel userProfileViewModel;

    private SharedPreferences pref;

    private Button mButtonCamera;
    private EditText et_DOB, et_city, et_name;
    private Spinner spin_country, spin_sex;
    private NumberPicker picker_weight, picker_height;
    private ImageView mIvPic;

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    final Calendar calendar_DOB = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userProfileViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        pref = this.getActivity().getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);

        // This grabs the stored profile picture and displays it.
        final String picPath = pref.getString("profile_pic", null);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                loadProfilePic(picPath);
            }
        });

        spin_country = root.findViewById(R.id.spinner_country);
        spin_sex = root.findViewById(R.id.spinner_sex);
        initializeSpinners();

        picker_weight = root.findViewById(R.id.np_weight);
        picker_height = root.findViewById(R.id.np_height);
        initializePickers();

        et_DOB = root.findViewById(R.id.et_DOB);
        initializeDOB();

        et_name = root.findViewById(R.id.et_name_big);
        et_city = root.findViewById(R.id.et_city_big);

        initializeEditTexts();

        mButtonCamera = root.findViewById(R.id.button_take_pic);
        initializeCamera();

        return root;
    }

    public void initializeCamera() {
        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });
    }

    public void takePhoto(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(this.getActivity().getPackageManager())!=null){
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == this.getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnailImage = (Bitmap) extras.get("data");

            saveToInternalStorage(thumbnailImage);
            mIvPic = (ImageView) this.getActivity().findViewById(R.id.imageView_profile);
            mIvPic.setImageBitmap(thumbnailImage);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(requireActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String path = directory.getAbsolutePath();

        SharedPreferences pref = this.getActivity().getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);
        pref.edit().putString("profile_pic", path).apply();

        return path;
    }

    private void loadProfilePic(String path)
    {
        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            mIvPic = this.getActivity().findViewById(R.id.imageView_profile);
            mIvPic.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void initializeDOB() {

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar_DOB.set(Calendar.YEAR, year);
                calendar_DOB.set(Calendar.MONTH, month);
                calendar_DOB.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        et_DOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, calendar_DOB
                        .get(Calendar.YEAR), calendar_DOB.get(Calendar.MONTH),
                        calendar_DOB.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String dob = sdf.format(calendar_DOB.getTime());

        SharedPreferences pref = this.getActivity().getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);

        pref.edit().putString("dob", dob).apply();

        et_DOB.setText(sdf.format(calendar_DOB.getTime()));
    }

    private void initializeSpinners() {
        ArrayAdapter<CharSequence> country_adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.countries_array, R.layout.spinner_item_userprofile);
        country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_userprofile);
        spin_country.setAdapter(country_adapter);
        spin_country.setOnItemSelectedListener(this);
        spin_country.setSelection(pref.getInt("country_idx", 0));

        ArrayAdapter<CharSequence> sex_adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.sex_array, R.layout.spinner_item_userprofile);
        country_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_userprofile);
        spin_sex.setAdapter(sex_adapter);
        spin_sex.setOnItemSelectedListener(this);
        spin_sex.setSelection(pref.getInt("sex_idx", 0));

    }

    public void initializeWeightPicker() {
        picker_weight.setMaxValue(600);
        picker_weight.setMinValue(50);
        int weight_current = pref.getInt("weight", 0);
        picker_weight.setValue(weight_current);

        picker_weight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                pref.edit().putInt("weight", i2).apply();
            }
        });
    }

    public void initializeHeightPicker() {
        picker_height.setMaxValue(96);
        picker_height.setMinValue(24);
        int height_current = pref.getInt("height", 0);
        picker_height.setValue(height_current);

        picker_height.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                pref.edit().putInt("height", i2).apply();
            }
        });
    }

    public void initializePickers() {
        initializeHeightPicker();
        initializeWeightPicker();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int iid = parent.getId();
        switch (iid) {
            case R.id.spinner_country: {
                pref.edit().putString("country", spin_country.getSelectedItem().toString()).apply();
                pref.edit().putInt("country_idx", spin_country.getSelectedItemPosition()).apply();
                break;
            }

            case R.id.spinner_sex: {
                pref.edit().putString("sex", spin_sex.getSelectedItem().toString()).apply();
                pref.edit().putInt("sex_idx", spin_sex.getSelectedItemPosition()).apply();
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initializeEditTexts() {
        String mName = pref.getString("username", null);
        String mAge = pref.getString("dob", null);
        String mCity = pref.getString("city", null);

        et_name.setText(mName);
        et_DOB.setText(mAge);
        et_city.setText(mCity);

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pref.edit().putString("username", et_name.getText().toString()).apply();
            }
        });

        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pref.edit().putString("city", et_city.getText().toString()).apply();
            }
        });

    }
}