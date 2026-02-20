package backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ikar_2026.R;

import java.util.List;

public class RemoteCarAdapter extends BaseAdapter {
    private final Context context;
    private final List<RemoteCar> dataList;
    private final LayoutInflater inflater;

    // Слушатель для уведомления об изменении данных
    public interface OnDataChangedListener {
        void onDataChanged();
    }
    private OnDataChangedListener listener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.listener = listener;
    }

    public RemoteCarAdapter(Context context, List<RemoteCar> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() { return dataList.size(); }

    @Override
    public Object getItem(int position) { return dataList.get(position); }

    @Override
    public long getItemId(int position) { return dataList.get(position).getId(); }

    // ViewHolder для оптимизации
    static class ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_remote_car, parent, false);
            holder = new ViewHolder();
            holder.tvId = convertView.findViewById(R.id.tvId);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvStatus = convertView.findViewById(R.id.tvStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RemoteCar car = dataList.get(position);

        // Заполнение данными
        holder.tvId.setText(String.format("#%d", car.getId()));
        holder.tvName.setText(car.getName());

        // Статус с цветом
        if (car.isActive()) {
            holder.tvStatus.setText("Активен");
            holder.tvStatus.setTextColor(0xFF4CAF50); // Зеленый
        } else {
            holder.tvStatus.setText("Выключен");
            holder.tvStatus.setTextColor(0xFFF44336); // Красный
        }

        // Обработка клика - переключение статуса
        convertView.setOnClickListener(v -> {
            car.changeActive(); // Меняем статус в объекте
            notifyDataSetChanged(); // Перерисовываем список

            // Уведомляем MainActivity о необходимости сохранить данные
            if (listener != null) {
                listener.onDataChanged();
            }
        });

        return convertView;
    }
}