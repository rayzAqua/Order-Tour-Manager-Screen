package com.example.travelordersmanagement;

import androidx.annotation.NonNull;
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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout backButton;
    private RecyclerView list_order_view;
    private OrderAdapter orderAdapter;
    private SearchView searchView;
    private Spinner dropdown;
    private String [] items = {"Tất cả", "Xác nhận", "Thanh toán", "Chờ hoàn thành"};
    List<TourOrder> dataList = new ArrayList<>();
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Trở về
        backButton = findViewById(R.id.back_btn);

        // Tìm kiếm
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        // Thiết lập sự kiện tương tác với thanh tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Xử lý khi nhấn nút tìm kiếm
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return true;
            }
            // Xử lý khi có sự thay đổi text trên thanh tìm kiếm
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        // RecycleView
        // Ánh xạ list_order_view với thành phần giao diện list_order
        list_order_view = findViewById(R.id.list_order);
        // Hiển thị RecycleView theo chiều dọc
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        list_order_view.setLayoutManager(linearLayoutManager);
        // Tạo mới một đối tượng OrderAdapter và cho phép truy cập dữ liệu, giao diện
        // từ môi trường này.
        orderAdapter = new OrderAdapter(this);

        // Kết nối đến Firebase lấy cái node con từ node cha datas
        database = FirebaseDatabase.getInstance().getReference("datas");
        // Lấy dữ liệu Firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    TourOrder tourOrder = childSnapshot.getValue(TourOrder.class);
                    dataList.add(tourOrder);
                }
                // Gọi đến phương thức setData để lưu dữ liệu lấy được từ getDataList()
                // vào thuộc tính TourOrder của OrderAdapter
                orderAdapter.setData(dataList);
                // Sau khi lưu dữ liệu, đặt Adapter là orderAdapter cho RecyleView list_order_view để
                // định dạng cách hiển thị dữ liệu.
                list_order_view.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Dropdown
        // Ánh xạ dropdown với thành phần giao diện dropdown
        dropdown = findViewById(R.id.dropdown);
        // Tạo ra một stringApdapter để đặt kiểu hiển thị cho dữ liệu của dropdown
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, items);
        // Thiết lập tài nguyên giao diện cho dropDown, mỗi mục dữ liệu sẽ được hiển thị bằng
        // tài nguyên giao diện này.
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Đặt Adapter là dropDownAdapter cho dropdown
        dropdown.setAdapter(dropDownAdapter);
        // Sự kiện chọn mục của dropdown
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            // Xử lý sự kiện khi có một mục của dropdown được lựa chọn
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy dữ liệu của mục được lựa chọn của dropdown bằng position
                String value = parent.getItemAtPosition(position).toString();
                // Kiểm tra xem dữ liệu đang được chọn là chế độ xem nào.
                // Nếu là "Tất cả" thì không có sự thay đổi về DataList
                if (value.equals("Tất cả")) {
                    orderAdapter.setData(getDataList());
                }
                // Nếu là các lựa chọn khác thì truyền các lựa chọn đó vào hàm
                // filterListByStatus() để lấy về danh sách dữ liệu mới.
                else {
                    filterListByStatus(value);
                }
            }
            // Khi không có lựa chọn mục nào từ dropdown thì mặc định hiển thị "Tất cả"
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                orderAdapter.setData(getDataList());
            }
        });

        // Xử lý sự kiện click nút Back: Trở về giao diện trước đó.
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Hàm getDataList được dùng để lấy dữ liệu.
    private List<TourOrder> getDataList () {
        database = FirebaseDatabase.getInstance().getReference("datas");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    TourOrder tourOrder = childSnapshot.getValue(TourOrder.class);
                    dataList.add(tourOrder);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return dataList;
    }

    // Xử lý sự kiện nhập vào ô tìm kiếm
    private void filterList(String text) {
        // Tạo ra một list mới có kiểu là TourOrder
        List<TourOrder> filterList = new ArrayList<>();
        // Duyệt qua từng phần tử của ListData được lấy từ getDataList()
        for (TourOrder item : dataList) {
            // Kiểm tra xem với mỗi item thì thuộc tính tourName của item đó có chứa
            // cụm từ/từ được nhập vào ô tìm kiếm không.
            // Nếu kết quả là có thì thêm item đó vào filterList
            if (item.getTourName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        // Sau bước xử lý tạo một list dữ liệu mới như trên, nếu filterList rỗng thì thông báo
        // không tìm thấy gì cả.
        if (filterList.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        // Nếu filterList không rỗng thì gọi đến phương thức setFilteredList để
        // đặt lại dữ liệu mới cho OrderAdapter.
        else {
            orderAdapter.setFilteredList(filterList);
        }
    }

    // Xử lý sự kiện chọn mục từ dropdown
    private void filterListByStatus(String text) {
        // Khởi tạo một filterListByStatus để lấy dữ liệu mới dựa trên biến text.
        // Biến text sẽ chứa chuỗi được lấy từ thuộc tính "status" của TourOrder.
        List<TourOrder> filterListByStatus = new ArrayList<>();
        // Duyệt qua từng phần tử của ListData được lấy từ getDataList()
        for (TourOrder item : dataList) {
            // Kiểm tra xem với mỗi item thì thuộc tính status có chứa giá trị
            // trạng thái như biến text không. Nếu có thì thêm item đó vào filterListByStatus
            if (item.getStatus().toLowerCase().contains(text.toLowerCase())) {
                filterListByStatus.add(item);
            }
        }
        // Nếu filterListByStatus rỗng thì thông báo là không lọc được gì.
        if (filterListByStatus.isEmpty()) {
            Toast.makeText(this, "No Data Filter Found", Toast.LENGTH_SHORT).show();
        }
        // Nếu không rỗng thì gọi đến phương thức setFilteredList để đặt lại dữ liệu mới
        // cho orderAdapter
        else  {
            orderAdapter.setFilteredList(filterListByStatus);
        }
    }


}