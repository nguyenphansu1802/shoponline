package hcmute.edu.vn.my_project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import Model.ModelItem;
import Model.UserModel;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<ModelItem> mfoodLists;
    private UserModel userItem;

    public ItemAdapter(Context context, List<ModelItem> foodLists, UserModel user) {
        mContext = context;
        mfoodLists = foodLists;
        userItem = user;
    }

    @Override
    public int getItemCount() {
        return mfoodLists.size();
    }


//    public Object getItem(int position){
//        return position;
//    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.inf_product_item, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelItem item = mfoodLists.get(position);
        Bitmap image = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
        holder.picture.setImageBitmap(image);
        holder.txtName.setText(item.getName_item());
        holder.txtPrice.setText(standardizeProductPrice((int) item.getPrice()));
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView picture;
        public TextView txtName;
        public TextView txtPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picture);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ModelItem modelItemclick = mfoodLists.get(position);
                Intent intent = new Intent(mContext, InformationProduct.class);
                intent.putExtra("item", modelItemclick);
                intent.putExtra("user", userItem);
                mContext.startActivity(intent);
            }
        }
    }
}
