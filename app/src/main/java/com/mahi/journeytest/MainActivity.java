package com.mahi.journeytest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mahi.journeytest.Adapter.PostAdapter;
import com.mahi.journeytest.Helper.AppGlobal;
import com.mahi.journeytest.PostModel.PostResponseResult;
import com.mahi.journeytest.WebService.Api;
import com.mahi.journeytest.WebService.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    ListView postListView;
    EditText edtPostSearch;
    PostAdapter postAdapter;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postListView = (ListView) findViewById(R.id.postList);
        edtPostSearch = (EditText) findViewById(R.id.edtPostSearch);
        imgBack = (ImageView) findViewById(R.id.back_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        getPost();

    }


    private void getPost() {

        if (AppGlobal.isNetwork(MainActivity.this)) {
            Call<List<PostResponseResult>> call = RetrofitClient.getInstance().getMyApi().postResponseResultCall();
            call.enqueue(new Callback<List<PostResponseResult>>() {
                @Override
                public void onResponse(Call<List<PostResponseResult>> call, Response<List<PostResponseResult>> response) {
                    List<PostResponseResult> postList = response.body();
                    postAdapter = new PostAdapter(MainActivity.this, postList);
                    postListView.setAdapter(postAdapter);
                    postListView.setTextFilterEnabled(true);
                    CallEditSearch(postAdapter);
                }

                @Override
                public void onFailure(Call<List<PostResponseResult>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                }

            });
        } else {
            AppGlobal.showToast(MainActivity.this, "Network Error!!", Toast.LENGTH_LONG);
        }
    }

    private void CallEditSearch(PostAdapter postAdapter1) {
        if (postAdapter1 != null) {
            // Add Text Change Listener to EditText
            edtPostSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Call back the Adapter with current character to Filter
                    postAdapter1.getFilter().filter(s.toString());
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