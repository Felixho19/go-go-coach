package hkucs.comp3330.gogocoach;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import hkucs.comp3330.gogocoach.firebase.UserChatItem;

public class MyChatAdapter extends RecyclerView.Adapter<MyChatAdapter.ViewHolder> implements View.OnClickListener{

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, UserChatItem c);
    }

    private Context mContext;
    private ArrayList<UserChatItem> UserChatItems;

    public MyChatAdapter(Context context, ArrayList<UserChatItem> UserChatItems) {
        this.mContext = context;
        this.UserChatItems = UserChatItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_chat_item, null); //parent, false);
        ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserChatItem mData = UserChatItems.get(position);

        holder.UserTextView.setText(mData.getName());
        holder.MsgTextView.setText(mData.getlastMessage());
        holder.TimeTextView.setText(mData.getTime());
        holder.itemView.setTag(mData);
        final ViewHolder _holder = holder;
        (new Thread(new Runnable(){
            @Override
            public void run() {
                final Bitmap avatar = loadImageFromNetwork(mData.getPhotoUrl());
                _holder.avatarImageView.post(new Runnable(){
                    @Override
                    public void run() {
                        _holder.avatarImageView.setImageBitmap(avatar);
                    }
                });
            }
        })).start();
        final int _position = position;
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, UserChatItems.get(_position));
            }
        });
    }

    public Bitmap loadImageFromNetwork(String url) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream
                    ((InputStream) new URL(url).getContent());
            return bitmap;
        } catch(
        Exception e)

        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return UserChatItems.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (UserChatItem) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatarImageView;
        public TextView UserTextView;
        public TextView MsgTextView;
        public TextView TimeTextView;
        public LinearLayout rootLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.avatarImageView);
            UserTextView = (TextView) itemView.findViewById(R.id.UserTextView);
            MsgTextView = (TextView) itemView.findViewById(R.id.MsgTextView);
            TimeTextView = (TextView) itemView.findViewById(R.id.TimeTextView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayout2);
        }
    }
}