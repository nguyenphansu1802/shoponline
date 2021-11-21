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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Control.DB_Cart;
import Model.ModelCart;
import Model.UserModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private Context mContext;

    private List<ModelCart> mCartList;


    private UserModel mUser;

    public CartAdapter(Context context, List<ModelCart> cartList, UserModel user) {
        mContext = context;
        mCartList = cartList;
        mUser = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_in_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelCart modelCart = mCartList.get(position);
        holder.txtProductName.setText(standardizeProductName(modelCart.getNameItem()));
        holder.txtPrice.setText(standardizePrice((int) modelCart.getPrice()));
        holder.txtAmount.setText(String.valueOf(modelCart.getAmount()));
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

        public TextView txtProductName, txtPrice, txtAmount,txtTotalPrice;
        public Button btnPayItemCart, btnDelItemCart;

        public ViewHolder(View view) {
            super(view);
            txtProductName = view.findViewById(R.id.txtProductName);
            txtPrice = view.findViewById(R.id.txtPrice);
            txtAmount = view.findViewById(R.id.txtAmount);
            txtTotalPrice = view.findViewById(R.id.txtTotalPrice);
            btnDelItemCart = view.findViewById(R.id.btnDelItemCart);
            btnPayItemCart = view.findViewById(R.id.btnPayItemCart);
            btnPayItemCart.setOnClickListener(this);
            btnDelItemCart.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            DB_Cart databaseCart = new DB_Cart(mContext);
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ModelCart modelCartClick = mCartList.get(position);
                switch (v.getId()) {
                    case R.id.btnPayItemCart:
                        Intent intent = new Intent(mContext, Bill.class);
                        intent.putExtra("cart", modelCartClick);
                        intent.putExtra("user", mUser);
                        intent.putExtra("kindpay", "pay 1");
                        mContext.startActivity(intent);
                    case R.id.btnDelItemCart:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Do you really want to Delete this product?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (databaseCart.DeleteCart(modelCartClick.getCartId()) == true){
                                            Toast.makeText(mContext,"Deleted", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(v.getContext(), AddCart.class);
                                            intent.putExtra("user", mUser);
                                            mContext.startActivity(intent);
                                        }else{
                                            Toast.makeText(mContext,"Error", Toast.LENGTH_LONG).show();
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
    }}