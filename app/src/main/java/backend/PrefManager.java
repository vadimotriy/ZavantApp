package backend;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefManager {
    private static final String PREF_NAME = "my_app_prefs";
    private static final String KEY_DATA = "data_items_list";

    private SharedPreferences prefs;
    private Gson gson;

    public PrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // --- СОХРАНЕНИЕ СПИСКА ---
    public void saveDataList(List<RemoteCar> dataList) {
        String json = gson.toJson(dataList); // Превращаем список в JSON строку
        prefs.edit().putString(KEY_DATA, json).apply(); // .apply() асинхронно, .commit() синхронно
    }

    // --- ЧТЕНИЕ СПИСКА ---
    public List<RemoteCar> getDataList() {
        String json = prefs.getString(KEY_DATA, null);
        if (json == null) {
            return new ArrayList<>(); // Если данных нет, возвращаем пустой список
        }
        // Превращаем JSON строку обратно в список объектов
        Type type = new TypeToken<ArrayList<RemoteCar>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void clearData() {
        prefs.edit().remove(KEY_DATA).apply();
    }
}