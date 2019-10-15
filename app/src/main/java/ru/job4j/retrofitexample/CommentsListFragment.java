package ru.job4j.retrofitexample;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentsListFragment extends Fragment {
    private RecyclerView recycler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        this.recycler = view.findViewById(R.id.list);
        this.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        getComments();
        return view;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private View view;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

        private final List<Comment> comments;

        public CommentAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.comment_info, parent, false);
            return new CommentHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentHolder holder, int i) {
            Comment comment = this.comments.get(i);
            TextView name = holder.view.findViewById(R.id.name);
            TextView id = holder.view.findViewById(R.id.id);
            TextView postId = holder.view.findViewById(R.id.postId);
            TextView email = holder.view.findViewById(R.id.email);
            TextView text = holder.view.findViewById(R.id.comment_text);

            if ((i % 2) == 0) {
                holder.view.setBackgroundColor(Color.parseColor("#d8d8d8"));
            }

            name.setText(comment.getName());
            id.setText(comment.getId());
            postId.setText(comment.getPostId());
            email.setText(comment.getEmail());
            text.setText(comment.getText());
        }

        @Override
        public int getItemCount() {
            return this.comments.size();
        }
    }

    public void getComments() {
        final List<Comment> comments;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments();

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(String.format("Code: %s", response.code()));
                }
                List<Comment> comments = response.body();
                recycler.setAdapter(new CommentAdapter(comments));
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("CommentsListFragment",t.getMessage());
            }
        });


    }
}

