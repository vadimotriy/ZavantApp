package com.example.ikar_2026.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ikar_2026.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import backend.PrefManager;

public class Information extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information);

        TextView textview = findViewById(R.id.textView2);
        int userId = getIntent().getIntExtra("COUNT", 0);
        textview.setText("  Количество устройств: " + Integer.toString(userId));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_info) {
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0); // Убирает анимацию переключения
                return true;
            }
            // ... другие страницы
            return false;
        });

        Button buttonClear = findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new androidx.appcompat.app.AlertDialog.Builder(Information.this)
                        .setTitle("Очистка данных")
                        .setMessage("Все устройства будут удалены безвозвратно. Продолжить?")
                        .setPositiveButton("Да", (dialog, which) -> {
                            // 2. Очищаем данные в хранилище
                            PrefManager prefManager = new PrefManager(getApplicationContext());
                            prefManager.saveEmptyList(); // Или prefManager.clearData();

                            // 3. Возвращаемся на главный экран
                            // Флаг NEW_TASK важен, чтобы обновить стек и не вернуться назад в эту активность
                            Intent intent = new Intent(Information.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            android.widget.Toast.makeText(Information.this, "Данные очищены", android.widget.Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Отмена", null)
                        .show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }
}