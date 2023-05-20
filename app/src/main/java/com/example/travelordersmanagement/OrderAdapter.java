package com.example.travelordersmanagement;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolderView>{

    private Context mContext;
    private List<TourOrder> dataList;

    public OrderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setFilteredList(List<TourOrder> filteredList) {
        this.dataList = filteredList;
        notifyDataSetChanged();
    }

    public void setData(List<TourOrder> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent,false);
        return new OrderHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolderView holder, int position) {
        TourOrder tourOrder = dataList.get(position);
        if (tourOrder == null) {
            return;
        }

        // Main Panel
//        holder.img_tour.setImageResource(tourOrder.getResourceId());
        Picasso.get().load(tourOrder.getTourImage()).into(holder.img_tour);

        holder.tour_name.setText(tourOrder.getTourName());
        holder.rating.setText(tourOrder.getRating());
        holder.tour_date.setText(tourOrder.getDate());
        holder.tour_cost.setText(tourOrder.getCost());
        holder.tour_place.setText(tourOrder.getPlace());
        holder.tour_status.setText(tourOrder.getStatus());

        // Expand Panel
        holder.tour_id.setText(tourOrder.getTourId());
//        holder.customer_img.setImageResource(tourOrder.getCustomerImg());
        Picasso.get().load(tourOrder.getCustomerImg()).into(holder.customer_img);

        holder.customer_name.setText(tourOrder.getCustomer_name());
        holder.phoneNumber.setText(tourOrder.getPhoneNumber());
        holder.email.setText(tourOrder.getEmail());
        holder.peoples.setText(tourOrder.getPeoples());
        holder.totalCost.setText(tourOrder.getTotalCost());
        holder.note.setText(tourOrder.getNote());

        // Xử lý hiển thị của các nút thao tác trên thành phần giao diện dựa vào thuộc tính
        // status của TourOrder
        if (tourOrder.getStatus().contains("Chờ xác nhận")) {
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.status_green);
            holder.background.setBackground(drawable);
            holder.text.setText("Xác nhận");
        } if (tourOrder.getStatus().contains("Chờ thanh toán")) {
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.status_gold);
            holder.background.setBackground(drawable);
            holder.text.setText("Thanh toán");
        } else if (tourOrder.getStatus().contains("Chờ hoàn thành")) {
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.status_lightgreen);
            holder.background.setBackground(drawable);
            holder.text.setText("Hoàn thành");
        }

        // Gía trị của isExpandable sẽ được set lại mỗi khi click chuột vào mainPanel
        boolean isExpandable = dataList.get(position).isExpandable();
        holder.expandPanel.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    public class OrderHolderView extends RecyclerView.ViewHolder {

        private ImageView img_tour;
        private TextView tour_name, rating, tour_date, tour_cost, tour_place, tour_status;
        private ImageView customer_img;
        private TextView tour_id, customer_name, phoneNumber, email, peoples, totalCost, note;
        private FrameLayout background;
        private TextView text;
        private RelativeLayout mainPanel;
        private LinearLayout expandPanel;

        public OrderHolderView(@NonNull View itemView) {
            super(itemView);

            // Main Panel
            img_tour = itemView.findViewById(R.id.img_tour);
            tour_name = itemView.findViewById(R.id.tour_name);
            rating = itemView.findViewById(R.id.rating);
            tour_date = itemView.findViewById(R.id.tour_date);
            tour_cost = itemView.findViewById(R.id.tour_cost);
            tour_place = itemView.findViewById(R.id.tour_place);
            tour_status = itemView.findViewById(R.id.tour_status);
            background = itemView.findViewById(R.id.status_container);

            // Expand Panel
            tour_id = itemView.findViewById(R.id.tour_order_id);
            customer_img = itemView.findViewById(R.id.customer_img);
            customer_name = itemView.findViewById(R.id.customer_name);
            phoneNumber = itemView.findViewById(R.id.customer_phone);
            email = itemView.findViewById(R.id.email);
            peoples = itemView.findViewById(R.id.quantity);
            totalCost = itemView.findViewById(R.id.total_cost);
            note = itemView.findViewById(R.id.note);
            text = itemView.findViewById(R.id.btn_accept);

            mainPanel = itemView.findViewById(R.id.tour_container);
            expandPanel = itemView.findViewById(R.id.tour_details);

            // Thiết lập sự kiện click chuột - để mở tắt phần chi tiết hoá đơn.
            mainPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy vị trí của adapter được click
                    // Mỗi adapter đại diện cho một đối tượng dữ liệu được vẽ lên giao diện
                    // Trả về mẫu dữ liệu từ chỉ số vị trí của Adapter được click.
                    TourOrder tourOrder = dataList.get(getAdapterPosition());
                    // Sau khi có được dữ liệu của Adapter được click. Gọi đến phương thức
                    // setExpandable của tourOrder và lấy not giá trị
                    // trả về của phương thức getExpandable
                    tourOrder.setExpandable(!tourOrder.isExpandable());
                    // Thông báo sự thay đổi của dữ liệu tại vị trí được chọn
                    // và yêu cầu RecycleView cập nhật lại. Lúc này, nó sẽ gọi đến
                    // hàm onBindViewHolder để cập nhật lại dữ liệu của Adapter được click.
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
