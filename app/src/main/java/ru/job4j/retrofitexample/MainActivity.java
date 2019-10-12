package ru.job4j.retrofitexample;

import android.os.Bundle;
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
import retrofit2.http.GET;

import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView result = findViewById(R.id.result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", "1");
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);
//        Call<Post> call = jsonPlaceHolderApi.getPost(1);
//        call.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if (!response.isSuccessful()) {
//                    result.setText(String.format("Code: %s", response.code()));
//                }
//                Post post = response.body();
//                String content = String.format(
//                        "ID: %s \n user ID: %s \n Title: %s \n Text: %s \n\n",
//                        post.getId(), post.getUserId(), post.getTitle(), post.getText()
//                );
//                result.setText(content);
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//                result.setText(t.getMessage());
//            }
//        });
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
//                Log.d("MainActivity",t.getMessage());
                result.setText(t.getMessage());
                result.append("Fail");
            }
        });
    }

    public interface JsonPlaceHolderApi {
        //        @GET ("posts")
//        Call<List<Post>> getPosts();
//
        @GET("posts")
        Call<List<Post>> getPost(@QueryMap Map<String, String> parameters);

//        @GET("posts/{id}")
//        Call<Post> getPost(@Path("id") int postId);
//        @GET("posts")
//        Call<List<Post>> getPost(@Query("id") int postId);

    }


}
