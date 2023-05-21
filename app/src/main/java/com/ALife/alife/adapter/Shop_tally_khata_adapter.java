package com.ALife.alife.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.shop_tally_khata_response;

import java.text.DecimalFormat;
import java.util.List;

public class Shop_tally_khata_adapter extends RecyclerView.Adapter<Shop_tally_khata_adapter.AppViewholder> {
    private List<shop_tally_khata_response> sell_list;
    private OnItemClickListener clickListener;

    public Shop_tally_khata_adapter(List<shop_tally_khata_response> sell_list) {
        this.sell_list = sell_list;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_sell_history_card, parent, false);
        return new Shop_tally_khata_adapter.AppViewholder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        shop_tally_khata_response tali = sell_list.get(position);
        Double due = 0.0;
        if (tali.getDue().equals(""))
            due = 0.0;
        else
            due = Double.parseDouble(tali.getDue());
        if (tali.getTransaction_type().equals("due_pay")) {
            //holder.defaultLayout.setVisibility(View.INVISIBLE);
            holder.customerName.setText(tali.customer_name);
            //holder.pay_due.setText(tali.getDue_pay());
            holder.paymentMethodText.setText(tali.getPayment_system());
            holder.dateText.setText(tali.getDate());
            if (due >= 0.0) {
                // holder.dueText.setTextColor(000000);
                holder.duePrice.setTextColor(R.color.black);
                holder.duePrice.setText(new DecimalFormat("##.##").format(due));
            } else if (due < 0.0) {
                due = due * (-1);
                holder.duePrice.setTextColor(0xffff0000);
                holder.duePrice.setText(new DecimalFormat("##.##").format(due));
            }

        } else {
            //holder.payDueLayout.setVisibility(View.INVISIBLE);
            if (tali.getTransaction_type().equals("non_registered_pay")) {
                holder.customerName.setText(tali.getCustomer_name() + "(Non Registered)");
                holder.sellPrice.setText(tali.getSell_price());
                holder.dateText.setText(tali.getDate());
                if (due >= 0.0) {
                    // holder.dueText.setTextColor(000000);
                    holder.duePrice.setTextColor(R.color.black);
                    holder.duePrice.setText(new DecimalFormat("##.##").format(due));
                } else if (due < 0.0) {
                    due = due * (-1);
                    holder.duePrice.setTextColor(0xffff0000);
                    holder.duePrice.setText(new DecimalFormat("##.##").format(due));
                }

                holder.paidPrice.setText(tali.getNon_registered_pay());
                holder.paymentMethodText.setText(tali.getPayment_system());
            } else {
                holder.customerName.setText(tali.getCustomer_name());
                holder.sellPrice.setText(tali.getSell_price());
                holder.dateText.setText(tali.getDate());
                if (due >= 0.0) {
                    // holder.dueText.setTextColor(000000);
                    // holder.duePrice.setTextColor(R.color.black);
                    holder.duePrice.setText(new DecimalFormat("##.##").format(due));
                } else if (due < 0.0) {
                    due = due * (-1);
                    holder.duePrice.setTextColor(0xffff0000);
                    holder.duePrice.setText(new DecimalFormat("##.##").format(due));
                }
                holder.paidPrice.setText(tali.getCash());
                holder.paymentMethodText.setText(tali.getPayment_system());
            }
        }


    }

    @Override
    public int getItemCount() {
        return sell_list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView customerName, sellPrice, paidPrice, duePrice, dateText,  paymentMethodText;
        LinearLayout defaultLayout, payDueLayout;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            customerName = (TextView) itemView.findViewById(R.id.customerNameID);
            sellPrice = (TextView) itemView.findViewById(R.id.sellPriceID);
            paidPrice = (TextView) itemView.findViewById(R.id.paidPriceID);
            duePrice = (TextView) itemView.findViewById(R.id.duePriceID);
            dateText = (TextView) itemView.findViewById(R.id.dateID);

            paymentMethodText = (TextView) itemView.findViewById(R.id.paymentMethodID);

            defaultLayout = (LinearLayout) itemView.findViewById(R.id.defaultLayoutID);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
