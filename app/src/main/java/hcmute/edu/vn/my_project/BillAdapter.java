package hcmute.edu.vn.my_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Control.DB_Cart;
import Model.ModelCart;
import Model.ModelItem;
import Model.UserModel;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{

    private Context mContext;

    private List<ModelCart> mCartList;

    private UserModel mUser;

    public BillAdapter(Context context, List<ModelCart> cartList, UserModel mUser) {
        mContext = context;
        mCartList = cartList;
        mUser = mUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelCart modelCart = mCartList.get(position);
        holder.tvBillName.setText(standardizeProductName(modelCart.getNameItem()));
//        holder.txtPrice.setText(standardizePrice((int) modelCart.getPrice()));
        holder.tvBillNumber.setText(String.valueOf(modelCart.getAmount()));
        holder.txtTotalPrice.setText(standardizePrice(totalPrice((int) modelCart.getPrice(), modelCart.getAmount())));
    }
    @Override
    public int getItemCount() {
        int count;
        try {
            count = mCartList.size();
        } catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    private String standardizeProductName(String name) {
        if (name.length() <= 29)
            return name;
        return name.substring(0, 28) + "...";
    }

    private String standardizePrice(int price) {
        String priceInString = String.valueOf(price);
        String result = "";
        int i = priceInString.length() - 1, count = 0;
        while (i >= 0) {
            result += priceInString.substring(i, i + 1);
            count++;
            if (count == 3 && i != 0) {
                result += ".";
                count = 0;
            }
            i--;
        }
        return new StringBuilder(result).reverse().toString() + "đ";
    }

    private int totalCost() {
        int result = 0;
        for (int i = 0; i < mCartList.size(); i++) {
            result += mCartList.get(i).getPrice() * mCartList.get(i).getAmount();
        }
        return result;
    }
    private int totalPrice(int a, int b) {
        int result = 0;
        result = a * b;
        return result;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvBillName,txtTotalPrice, tvBillNumber;
        public AppCompatButton btn_Confirm;

        public ViewHolder(View view) {
            super(view);
            tvBillName = view.findViewById(R.id.tvBillName);
            txtTotalPrice = view.findViewById(R.id.tvCostBill);
            tvBillNumber = view.findViewById(R.id.tvBillNumber);
//            btn_Confirm = view.findViewById(R.id.btn_Confirm);
//            btn_Confirm.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ModelCart modelcartclick = mCartList.get(position);
                Intent intent = new Intent(mContext, Bill.class);
                intent.putExtra("cartpay", modelcartclick);
                intent.putExtra("user", mUser);
                mContext.startActivity(intent);
            }
        }
    }}