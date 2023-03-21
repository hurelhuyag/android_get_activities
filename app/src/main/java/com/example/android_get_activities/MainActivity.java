package com.example.android_get_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        //PackageInfo packageInfo = getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);

        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);
        Iterator<PackageInfo> packageIterator = installedPackages.iterator();
        PackageInfo packageInfo = null;
        while(packageIterator.hasNext()){
            packageInfo = (PackageInfo) packageIterator.next();
            if(packageInfo.activities != null){
                for(ActivityInfo activity : packageInfo.activities){
                    Log.d("ACTIVITY", activity.name);
                }
            }
        }

        listView.setAdapter(new MyAdapter(this, installedPackages));
    }

    private static class MyAdapter extends BaseAdapter {

        private Context context;
        private List<PackageInfo> items;

        public MyAdapter(Context context, List<PackageInfo> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public PackageInfo getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, viewGroup, false);
            }

            PackageInfo item = getItem(i);

            TextView tv1 = view.findViewById(android.R.id.text1);
            TextView tv2 = view.findViewById(android.R.id.text2);

            String row = "";
            if (item.activities == null) {
                row = "";
            } else {
                for (ActivityInfo activityInfo : item.activities) {
                    row += activityInfo.name + "\n";
                }
            }

            tv1.setText(item.packageName);
            tv2.setText(row);

            return view;
        }
    }
}