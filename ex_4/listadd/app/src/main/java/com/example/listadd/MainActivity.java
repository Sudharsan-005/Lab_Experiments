package com.example.listadd;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        ArrayList<ItemModel> dataList = new ArrayList<>();
        dataList.add(new ItemModel("WiFi", true));
        dataList.add(new ItemModel("Bluetooth", false));
        dataList.add(new ItemModel("Mobile Data", true));
        dataList.add(new ItemModel("NFC", false));

        listView.setAdapter(new MyCustomAdapter(this, dataList));
    }

    public static class ItemModel {
        String name;
        boolean isToggled;
        public ItemModel(String name, boolean isToggled) {
            this.name = name;
            this.isToggled = isToggled;
        }
    }

    public class MyCustomAdapter extends BaseAdapter {
        Context context;
        ArrayList<ItemModel> list;

        public MyCustomAdapter(Context context, ArrayList<ItemModel> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() { return list.size(); }
        @Override
        public Object getItem(int i) { return list.get(i); }
        @Override
        public long getItemId(int i) { return i; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setPadding(40, 40, 40, 40);

                TextView textView = new TextView(context);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                textView.setTextSize(18);

                ToggleButton toggleButton = new ToggleButton(context);

                layout.addView(textView);
                layout.addView(toggleButton);

                convertView = layout;

                holder = new ViewHolder();
                holder.tv = textView;
                holder.tb = toggleButton;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ItemModel item = list.get(position);
            holder.tv.setText(item.name);

            holder.tb.setOnCheckedChangeListener(null);
            holder.tb.setChecked(item.isToggled);
            holder.tb.setOnCheckedChangeListener((btn, isChecked) -> item.isToggled = isChecked);

            return convertView;
        }

        private class ViewHolder {
            TextView tv;
            ToggleButton tb;
        }
    }
}