package com.example.travelordersmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout backButton;
    private RecyclerView list_order_view;
    private OrderAdapter orderAdapter;
    private SearchView searchView;
    private Spinner dropdown;
    private String [] items = {"Tất cả", "Xác nhận", "Thanh toán", "Hoàn thành"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backButton = findViewById(R.id.back_btn);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

        });

        list_order_view = findViewById(R.id.list_order);
        orderAdapter = new OrderAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_order_view.setLayoutManager(linearLayoutManager);

        orderAdapter.setData(getDataList());
        list_order_view.setAdapter(orderAdapter);

        dropdown = findViewById(R.id.dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                if (value.equals("Tất cả")) {
                    orderAdapter.setData(getDataList());

                } else {
                    filterListByStatus(value);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                orderAdapter.setData(getDataList());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<TourOrder> getDataList () {

        List<TourOrder> dataList = new ArrayList<>();

        dataList.add(new TourOrder(R.drawable.bienho, "Biển Hồ", "4.9", "12 - 15/04/2023", "1,5m/Ngày", "Gia Lai", "Chờ xác nhận",
                            "ABCDEF1", "Sơn Tùng", R.drawable.sontung, "0912345678", "sontung@gmail.com", "2", "3.500.000 VNĐ", ".........."));
        dataList.add(new TourOrder(R.drawable.bienhoche, "Biển Hồ Chè", "4.8", "10 - 12/04/2023", "1,4m/Ngày", "Gia Lai", "Chờ thanh toán",
                            "ABCDEF2", "Chipu", R.drawable.chipu, "0912345778", "chipu@gmail.com", "2", "2.500.000 VNĐ", ".........."));
        dataList.add(new TourOrder(R.drawable.dalat, "Đà Lạt", "4.5", "15 - 17/04/2023", "2,5m/Ngày", "Lâm Đồng", "Chờ hoàn thành",
                            "ABCDEF3", "Hào quang rực rỡ", R.drawable.tranthanh, "0912365778", "haoquangrucro@gmail.com", "3", "4.500.000 VNĐ", "Khách cần sự riêng tư"));
        dataList.add(new TourOrder(R.drawable.doicat, "Đồi Cát", "4.9", "19 - 21/04/2023", "1,2m/Ngày", "Bình Thuận", "Chờ thanh toán",
                            "ABCDEF4", "Vùng đất cấm", R.drawable.dvh, "0912345378", "privateArea@gmail.com", "1", "6.500.000 VNĐ", "Vùng đất cấm"));
        dataList.add(new TourOrder(R.drawable.vungtau, "Vũng Tàu", "4.7", "16 - 23/04/2023", "3,5m/Ngày", "Vũng Tàu", "Chờ xác nhận",
                            "ABCDEF5", "Linh14", R.drawable.hoailinh, "0932385378", "vuanuoclu@gmail.com", "2", "1.400.000 VNĐ", "Khách rất thích nước"));

        return dataList;
    }

    private void filterList(String text) {
        List<TourOrder> filterList = new ArrayList<>();
        for (TourOrder item : getDataList()) {
            if (item.getTourName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            orderAdapter.setFilteredList(filterList);
        }
    }

    private void filterListByStatus(String text) {
        List<TourOrder> filterListByStatus = new ArrayList<>();
        for (TourOrder item : getDataList()) {
            if (item.getStatus().toLowerCase().contains(text.toLowerCase())) {
                filterListByStatus.add(item);
            }
        }
        if (filterListByStatus.isEmpty()) {
            Toast.makeText(this, "No Data Filter Found", Toast.LENGTH_SHORT).show();
        } else  {
            orderAdapter.setData(filterListByStatus);
        }
    }


}