package com.example.HW1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HW1.TaskFragment.OnListFragmentInteractionListener;
import com.example.HW1.tasks.TaskList.Task;

import java.util.List;


public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Task task = mValues.get ( position );
        holder.mItem = task;
        holder.mContentView.setText ( task.name);
        final String picPath = task.picPath;
        Context context = holder.mView.getContext ();

                Drawable taskDrawable;
                taskDrawable = context.getResources ().getDrawable ( R.drawable.awatar );
                holder.mItemImageView.setImageDrawable ( taskDrawable );


        holder.mView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.OnListFragmentClickInteraction( holder.mItem, position );
                }
            }
        } );
        holder.deleteButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mListener.OnDeleteClick ( position );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mItemImageView;
        public final TextView mContentView;
        public Task mItem;
        public final ImageView deleteButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemImageView = view.findViewById(R.id.item_image);
            mContentView = view.findViewById(R.id.content);
            deleteButton = view.findViewById(R.id.imageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
