package ru.job4j.retrofitexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    JsonPlaceHolderApi jsonPlaceHolderApi;
    TextView result;
    EditText id;
    EditText userId;
    EditText title;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        id = findViewById(R.id.id);
        userId = findViewById(R.id.userId);
        title = findViewById(R.id.title);
        text = findViewById(R.id.text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    }

    public void get(View view) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", "1");
        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    result.setText(String.format("Code: %s", response.code()));
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    String content = String.format(
                            "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
                            post.getId(), post.getUserId(), post.getTitle(), post.getText()
                    );
                    result.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                result.setText(t.getMessage());
                result.append("Fail");
            }
        });
    }

    public void createBtn(View view) {
        final Post post = new Post(Integer.valueOf(userId.getText().toString()), title.getText().toString(), text.getText().toString());
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post postResponse = response.body();
                    String content = String.format(
                            "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
                            postResponse.getId(), postResponse.getUserId(), postResponse.getTitle(), postResponse.getText()
                    );
                    result.setText(content);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }

    public void putBtn(View view) {
        final Post post = new Post(Integer.valueOf(userId.getText().toString()), title.getText().toString(), text.getText().toString());
        int postId = Integer.valueOf(id.getText().toString());
        Call<Post> call = jsonPlaceHolderApi.putPost(postId, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post postResponse = response.body();
                    String content = String.format(
                            "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
                            postResponse.getId(), postResponse.getUserId(), postResponse.getTitle(), postResponse.getText()
                    );
                    result.setText(content);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }
}
