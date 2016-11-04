package com.example.zenglb.retrofittest.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zenglb.retrofittest.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by zenglb on 2016/9/25.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<String> data = new ArrayList<>();

	public interface OnItemClickListener {
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;
	}

	/**
	 * @param mContext
	 * @param data
	 */
	public MyAdapter(Context mContext, List<String> data) {
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
		this.data = data;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mLayoutInflater.inflate(R.layout.function_item_layout, parent, false));
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		viewHolder.function_name.setText(data.get(position));
		viewHolder.itemView.setClickable(true);

		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mOnItemClickListener.onItemClick(viewHolder.itemView, position);
				notifyDataSetChanged();
			}
		});

		viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				mOnItemClickListener.onItemLongClick(viewHolder.itemView, viewHolder.getLayoutPosition());
				return true;
			}
		});

	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		private View itemView;
		private TextView function_name;
		public ViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			function_name = (TextView) itemView.findViewById(R.id.function_name);
		}
	}
}