package com.example.ikar_2026.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ikar_2026.R;

import java.util.ArrayList;
import java.util.List;

import backend.PrefManager;
import backend.RemoteCar;

public class AddRobot extends AppCompatActivity {
    private static final String LOG_TAG = "IKAR2026AddRobot";

    Button buttonFinish;
    Button buttonAdd;

    EditText carId;
    EditText carPassword;
    EditText carName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_robot);

        buttonFinish = findViewById(R.id.buttonBack);
        buttonAdd = findViewById(R.id.buttonAdd);

        carId = findViewById(R.id.carId);
        carPassword = findViewById(R.id.carPassword);
        carName = findViewById(R.id.carName);

        Log.i(LOG_TAG, "Активность создана");

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Активность завершена (Выход)");
                finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCar = carId.getText().toString();
                String password = carPassword.getText().toString();
                String name = carName.getText().toString();

                if (idCar.isBlank() || password.isBlank() || name.isBlank()) {
                    Toast.makeText(AddRobot.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isInteger(idCar)) {
                    Toast.makeText(AddRobot.this, "Значение ID должно быть числом", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id = Integer.parseInt(idCar);
                if (! (id == 676539 || id == 12345)) {
                    Toast.makeText(AddRobot.this, "Устройства с таким ID не найдено", Toast.LENGTH_SHORT).show();
                } else {
                    if (!password.equals("Example123!")) {
                        Toast.makeText(AddRobot.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                    } else {
                        RemoteCar car = new RemoteCar(Integer.parseInt(idCar), name, false);
                        Intent result = new Intent();
                        result.putExtra("new_car", car); // RemoteCar должен implements Serializable!
                        setResult(RESULT_OK, result);
                        finish();

                        Log.i(LOG_TAG, "Все успешно!");
                    }
                }
            }
        });
    }

    public static boolean isInteger(String str) {
        if (str == null) { return false; }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}