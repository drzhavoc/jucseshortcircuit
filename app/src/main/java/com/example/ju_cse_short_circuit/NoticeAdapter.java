package com.example.ju_cse_short_circuit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context context;
    private List<Notice> noticeList;

    public NoticeAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        Notice notice = noticeList.get(position);
        holder.categoryTextView.setText(notice.getCategory());
        holder.detailsTextView.setText(notice.getDetails());
        holder.uploaderEmailTextView.setText(notice.getUploaderEmail());
        holder.uploadTimeTextView.setText(notice.getUploadTime());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView, detailsTextView, uploaderEmailTextView, uploadTimeTextView;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
            uploaderEmailTextView = itemView.findViewById(R.id.uploaderEmailTextView);
            uploadTimeTextView = itemView.findViewById(R.id.uploadTimeTextView);
        }
    }
}
