package com.mahi.journeytest;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mahi.journeytest.Adapter.CommentAdapter;
import com.mahi.journeytest.Adapter.PostAdapter;
import com.mahi.journeytest.Helper.AppGlobal;
import com.mahi.journeytest.PostModel.CommentResponseResult;
import com.mahi.journeytest.PostModel.PostResponseResult;
import com.mahi.journeytest.WebService.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {


    ListView postListView;
    TextView postName;
    TextView postDesc;
    EditText edtCommentSearch;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        postListView = (ListView) findViewById(R.id.commentList);
        postName = (TextView) findViewById(R.id.post_name_c);
        postDesc = (TextView) findViewById(R.id.post_description_c);
        edtCommentSearch = (EditText) findViewById(R.id.edtCommentSearch);
        imgBack = (ImageView) findViewById(R.id.back_comment);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        int userId = getIntent().getIntExtra("userId", 1);
        int postId = getIntent().getIntExtra("postId", 1);
        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        String strName = "<font color=#000000>Title: </font> <font color=#7f7f7f>" + title + "</font>";
        String strDesc = "<font color=#000000>Description: </font> <font color=#7f7f7f>" + body + "</font>";
        postName.setText(Html.fromHtml(strName));
        postDesc.setText(Html.fromHtml(strDesc));

        getComment(postId + "");

    }


    private void getComment(String postId) {

        try {
            if (AppGlobal.isNetwork(CommentActivity.this)) {
                Call<List<CommentResponseResult>> call = RetrofitClient.getInstance().getMyApi().commentResponseResultCall(postId);
                call.enqueue(new Callback<List<CommentResponseResult>>() {
                    @Override
                    public void onResponse(Call<List<CommentResponseResult>> call, Response<List<CommentResponseResult>> response) {
                        List<CommentResponseResult> commentResponseResult = response.body();
                        postListView.setAdapter(new CommentAdapter(CommentActivity.this, commentResponseResult));

                        CommentAdapter commentAdapter = new CommentAdapter(CommentActivity.this, commentResponseResult);
                        postListView.setAdapter(commentAdapter);
                        postListView.setTextFilterEnabled(true);
                        CallEditSearch(commentAdapter);

                    }

                    @Override
                    public void onFailure(Call<List<CommentResponseResult>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                    }

                });
            } else {
                AppGlobal.showToast(CommentActivity.this, "Network Error!!", Toast.LENGTH_LONG);
            }
        } catch (Exception e) {
            AppGlobal.showToast(CommentActivity.this, "" + e, Toast.LENGTH_LONG);
            Log.d("Exception", "" + e);
        }

    }

    private void CallEditSearch(CommentAdapter commentAdapter1) {
        if (commentAdapter1 != null) {
            // Add Text Change Listener to EditText
            edtCommentSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Call back the Adapter with current character to Filter
                    commentAdapter1.getFilter().filter(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            //edtPostSearch.setEnabled(false);
        }
    }
}