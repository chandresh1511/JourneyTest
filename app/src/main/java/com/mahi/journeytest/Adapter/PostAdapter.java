package com.mahi.journeytest.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mahi.journeytest.CommentActivity;
import com.mahi.journeytest.PostModel.PostResponseResult;
import com.mahi.journeytest.R;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private List<PostResponseResult> searchList;
    private List<PostResponseResult> postList;

    private LayoutInflater inflate;

    public PostAdapter(Activity a) {
        activity = a;
        inflate = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public PostAdapter(Activity a, List<PostResponseResult> arr) {
        activity = a;
        searchList = arr;
        postList = arr;
        inflate = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return /*5*/searchList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        String url = null;
        Log.i("getView", "called");

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflate.inflate(R.layout.list_post, null);
            holder.postName = (TextView) convertView.findViewById(R.id.post_name);
            holder.postDesc = (TextView) convertView.findViewById(R.id.post_description);
            holder.postLinear = (LinearLayout) convertView.findViewById(R.id.post_linear);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String strName = "<font color=#000000>Title: </font> <font color=#7f7f7f>" + searchList.get(position).getTitle() + "</font>";
        String strDesc = "<font color=#000000>Description: </font> <font color=#7f7f7f>" + searchList.get(position).getBody() + "</font>";
        holder.postName.setText(Html.fromHtml(strName));
        holder.postDesc.setText(Html.fromHtml(strDesc));
        holder.postLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity, CommentActivity.class);
                myIntent.putExtra("userId", searchList.get(position).getUserId());
                myIntent.putExtra("postId", searchList.get(position).getId());
                myIntent.putExtra("title", searchList.get(position).getTitle());
                myIntent.putExtra("body", searchList.get(position).getBody());
                activity.startActivity(myIntent);

            }
        });


        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                searchList = (ArrayList<PostResponseResult>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<PostResponseResult> FilteredArrList = new ArrayList<PostResponseResult>();

                if (postList == null) {
                    postList = new ArrayList<PostResponseResult>(searchList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = postList.size();
                    results.values = postList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < postList.size(); i++) {
                        String data = postList.get(i).title;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new PostResponseResult(postList.get(i).userId, postList.get(i).id, postList.get(i).title, postList.get(i).body));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public static class ViewHolder {
        TextView postName;
        TextView postDesc;
        LinearLayout postLinear;

    }


}
