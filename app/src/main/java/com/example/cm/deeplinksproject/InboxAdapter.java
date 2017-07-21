package com.example.cm.deeplinksproject;

/**
 * Created by cm on 12/7/2017.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.leanplum.LeanplumInboxMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ka on 11/7/2017.
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.AppInboxMessagesViewHolder> {
    private List<LeanplumInboxMessage> messages;
    private InboxFragment.Listener<LeanplumInboxMessage> mListener;
    public InboxAdapter(InboxFragment.Listener<LeanplumInboxMessage> listener){
        this.mListener=listener;
        messages= new ArrayList<>();
    }

    public void addMessages(List<LeanplumInboxMessage> messages){
        this.messages=messages;
        notifyDataSetChanged();
    }
    @Override
    public AppInboxMessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppInboxMessagesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_detail_list_item, parent, false));
    }
    @Override
    public void onBindViewHolder(AppInboxMessagesViewHolder holder, int position) {
        final LeanplumInboxMessage leanplumInboxMessage= messages.get(position);
        holder.mTitleView.setText(leanplumInboxMessage.getTitle());
        holder.mDetailsView.setText(leanplumInboxMessage.getSubtitle());
        holder.mDate.setText(leanplumInboxMessage.getDeliveryTimestamp().toString());
        if(leanplumInboxMessage.isRead()){
            holder.mImageStatus.setImageResource(R.drawable.ic_inbox_read);
        }else{
            holder.mImageStatus.setImageResource(R.drawable.ic_inbox_unread);
        }
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onListFragmentInteraction(leanplumInboxMessage);
                    leanplumInboxMessage.read();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class AppInboxMessagesViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final private TextView mTitleView;
        final private TextView mDetailsView;
        final private TextView mDate;
        final private ImageView mImageStatus;
        public AppInboxMessagesViewHolder(View view) {
            super(view);
            this.mView=view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mDetailsView = (TextView) view.findViewById(R.id.subtitle);
            mDate = (TextView) view.findViewById(R.id.date);
            mImageStatus=(ImageView) view.findViewById(R.id.icon);
        }
    }
}
