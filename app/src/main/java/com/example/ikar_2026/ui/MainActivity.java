package com.example.ikar_2026.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ikar_2026.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import backend.PrefManager;
import backend.RemoteCar;
import backend.RemoteCarAdapter;

public class MainActivity extends AppCompatActivity implements RemoteCarAdapter.OnDataChangedListener {
    private static final String LOG_TAG = "IKAR2026MainActivity";

    Button startButton;
    ListView robotsListView;


    PrefManager prefManager;
    private List<RemoteCar> dataList = new ArrayList<>();
    private RemoteCarAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button);
        robotsListView = findViewById(R.id.robots);

        prefManager = new PrefManager(this);

        // 1. ВОССТАНОВЛЕНИЕ данных при запуске
        dataList = prefManager.getDataList();
        Log.i(LOG_TAG, "Активность создана");

        Log.i(LOG_TAG, "Загружено объектов: " + dataList.size());

        adapter = new RemoteCarAdapter(this, dataList);
        adapter.setOnDataChangedListener(this);
        robotsListView.setAdapter(adapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "Пользователь нажал на кнопку");

                Intent intent = new Intent(MainActivity.this, AddRobot.class);
                startActivityForResult(intent, 100); // 100 - requestCode, любое число
                Log.i(LOG_TAG, "Открыт экран добавления робота");
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_info) {
                Intent intent = new Intent(getApplicationContext(), Information.class);
                intent.putExtra("COUNT", dataList.size());
                startActivity(intent);
                overridePendingTransition(0, 0); // Убирает анимацию переключения
                return true;
            }
            // ... другие страницы
            return false;
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefManager.saveDataList(dataList);

        Log.i(LOG_TAG, "Данные сохранены.");
    }

    @Override
    public void onDataChanged() {
        // Сохраняем сразу, чтобы не потерять при сворачивании
        prefManager.saveDataList(dataList);
        Log.i(LOG_TAG, "Статус изменён, данные сохранены.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            RemoteCar newCar = (RemoteCar) data.getSerializableExtra("new_car");
            if (newCar != null) {
                dataList.add(newCar);

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                prefManager.saveDataList(dataList);
                Log.i(LOG_TAG, "Добавлен новый робот: " + newCar.getName());
            }
        }
    }
}