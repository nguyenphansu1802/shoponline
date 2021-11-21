package hcmute.edu.vn.my_project;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import Control.DB_Bill;
import Control.DB_Cart;
import Control.DB_Report;
import Control.DB_Usertable;
import Model.ModelBill;
import Model.ModelCart;
import Model.ModelReport;
import Model.UserModel;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder>{

    private Context mContext;

    private List<ModelBill> mBillList;

    private UserModel mUser;

    public RevenueAdapter(Context context, List<ModelBill> billList, UserModel user) {
        mContext = context;
        mBillList = billList;
        mUser = user;
    }

    @NonNull
    @Override
    public RevenueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inf_renvenue, parent, false);
        return new RevenueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueAdapter.ViewHolder holder, int position) {
        ModelBill modelBill = mBillList.get(position);

        DB_Usertable databaseUser = new DB_Usertable(mContext);
        holder.tvInfUser.setText(databaseUser.GetNameByIdUser(modelBill.getUserId()));
        holder.tvTotal.setText(standardizeProductPrice((int)modelBill.getTotalPrice()));
    }
    @Override
    public int getItemCount() {
        int count;
        try {
            count = mBillList.size();
        } catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }
    private String standardizeProductPrice(int price) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvInfUser, tvTotal;
        public ImageButton btnDel;

        public ViewHolder(View view) {
            super(view);
            tvInfUser = view.findViewById(R.id.tvInfUser);
            tvTotal = view.findViewById(R.id.tvTotal);
            btnDel = view.findViewById(R.id.btnDelReport);
            btnDel.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            DB_Bill databaseBill = new DB_Bill(mContext);
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ModelBill modelBillClick = mBillList.get(position);
                switch (v.getId()) {
                    case R.id.btnDelReport:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Do you really want to Delete this bill?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (databaseBill.DeleteBill(modelBillClick.getBillId()) == true) {
                                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(v.getContext(), Revenue.class);
                                            intent.putExtra("user", mUser);
                                            mContext.startActivity(intent);
                                        } else {
                                            Toast.makeText(mContext, "Error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog2 = builder.create();
                        alertDialog2.setTitle("MiniShop");
                        alertDialog2.show();
                        break;
                }
            }
        }
    }
}
