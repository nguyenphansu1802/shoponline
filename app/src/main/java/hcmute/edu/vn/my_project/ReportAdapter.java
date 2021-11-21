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


import Control.DB_Cart;
import Control.DB_Report;
import Model.ModelCart;
import Model.ModelReport;
import Model.UserModel;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{

    private Context mContext;

    private List<ModelReport> mReportList;

    private UserModel mUser;

    public ReportAdapter(Context context, List<ModelReport> reportList, UserModel user) {
        mContext = context;
        mReportList = reportList;
        mUser = user;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inf_report, parent, false);
        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        ModelReport modelReport = mReportList.get(position);
        holder.tvInfUserReport.setText(modelReport.getUsername());
        holder.tvItem.setText(modelReport.getItemname());
        holder.tvContents.setText(modelReport.getContents());
    }
    @Override
    public int getItemCount() {
        int count;
        try {
            count = mReportList.size();
        } catch (Exception e) {
            count = 0;
            e.printStackTrace();
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvInfUserReport, tvItem, tvContents;
        public ImageButton btnDelReport;

        public ViewHolder(View view) {
            super(view);
            tvInfUserReport = view.findViewById(R.id.tvInfUserReport);
            tvItem = view.findViewById(R.id.tvItem);
            tvContents = view.findViewById(R.id.tvContents);
            btnDelReport = view.findViewById(R.id.btnDelReport);
            btnDelReport.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            DB_Report databaseReport = new DB_Report(mContext);
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ModelReport modelReportClick = mReportList.get(position);
                switch (v.getId()) {
                    case R.id.btnDelReport:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Do you really want to Delete this report?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (databaseReport.DeleteReport(modelReportClick.getReport_id()) == true) {
                                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(v.getContext(), ListReport.class);
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
