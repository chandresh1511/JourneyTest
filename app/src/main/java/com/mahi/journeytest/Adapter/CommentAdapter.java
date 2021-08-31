package com.mahi.journeytest.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mahi.journeytest.PostModel.CommentResponseResult;
import com.mahi.journeytest.PostModel.PostResponseResult;
import com.mahi.journeytest.R;

import java.util.ArrayList;
import java.util.List;


public class CommentAdapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private List<CommentResponseResult> searchList;
    private List<CommentResponseResult> commentList;

    private LayoutInflater inflate;

    public CommentAdapter(Activity a) {
        activity = a;
        inflate = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public CommentAdapter(Activity a, List<CommentResponseResult> arr) {
        activity = a;
        searchList = arr;
        commentList = arr;
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

            convertView = inflate.inflate(R.layout.list_comment, null);
            holder.postName = (TextView) convertView.findViewById(R.id.post_name_c);
            holder.postEmail = (TextView) convertView.findViewById(R.id.post_email_c);
            holder.postDesc = (TextView) convertView.findViewById(R.id.post_description_c);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String strName = "<font color=#000000>Name: </font> <font color=#7f7f7f>" + searchList.get(position).getName() + "</font>";
        String strEmail = "<font color=#000000>Email: </font> <font color=#7f7f7f>" + searchList.get(position).getEmail() + "</font>";
        String strDesc = "<font color=#000000>Comment: </font> <font color=#7f7f7f>" + searchList.get(position).getBody() + "</font>";
        holder.postName.setText(Html.fromHtml(strName));
        holder.postEmail.setText(Html.fromHtml(strEmail));
        holder.postDesc.setText(Html.fromHtml(strDesc));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                searchList = (ArrayList<CommentResponseResult>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<CommentResponseResult> FilteredArrList = new ArrayList<CommentResponseResult>();

                if (commentList == null) {
                    commentList = new ArrayList<CommentResponseResult>(searchList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = commentList.size();
                    results.values = commentList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < commentList.size(); i++) {
                        String data = commentList.get(i).name;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new CommentResponseResult(commentList.get(i).postId,
                                    commentList.get(i).id,
                                    commentList.get(i).name,
                                    commentList.get(i).email,
                                    commentList.get(i).body));
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
        TextView postEmail;
        TextView postDesc;

    }


}
