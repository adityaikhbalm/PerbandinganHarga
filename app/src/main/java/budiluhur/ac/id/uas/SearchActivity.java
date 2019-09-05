package budiluhur.ac.id.uas;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import budiluhur.ac.id.uas.model.Record;
import budiluhur.ac.id.uas.model.ServerResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private EditText keyword;
    private ImageButton btnSearch, btnClear;
    private ProgressBar loading;
    private List<String> title = new ArrayList<>();
    private List<Record> tokopedia = new ArrayList<>();
    private List<Record> bukalapak = new ArrayList<>();
    private List<Record> shopee = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keyword = findViewById(R.id.keyword);
        btnSearch = findViewById(R.id.btnSearch);
        btnClear = findViewById(R.id.btnClear);
        recyclerView = findViewById(R.id.content);
        loading = findViewById(R.id.loading);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword.setText("");
            }
        });

        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    btnClear.setVisibility(View.VISIBLE);
                else
                    btnClear.setVisibility(View.INVISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
    }

    public interface RequestInterface {
        @FormUrlEncoded
        @POST("api/compare/")
        Call<ServerResponse> search(@Field("keyword") String keyword, @Field("hehehe") String hehehe);
    }

    private void searchKeyword() {
        if (keyword.getText().toString().trim().length() > 0) {
            disable();

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://asalole.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            RequestInterface requestInterface = retrofit.create(RequestInterface.class);
            Call<ServerResponse> response = requestInterface.search(keyword.getText().toString(),"hehehe");
            response.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                    ServerResponse resp = response.body();
                    title.clear();

                    if (resp.getTokopedia().getStatus().equals("success")){
                        tokopedia.clear();
                        for (int i=0; i<resp.getTokopedia().getTokopedia().size(); i++) {
                            Record record = new Record(
                                    resp.getTokopedia().getTokopedia().get(i).getNama(),
                                    resp.getTokopedia().getTokopedia().get(i).getHarga(),
                                    resp.getTokopedia().getTokopedia().get(i).getRating(),
                                    resp.getTokopedia().getTokopedia().get(i).getView(),
                                    resp.getTokopedia().getTokopedia().get(i).getUrl(),
                                    resp.getTokopedia().getTokopedia().get(i).getGambar()
                            );
                            tokopedia.add(record);
                        }
                        title.add("Tokopedia");
                    }
                    if (resp.getBukalapak().getStatus().equals("success")){
                        bukalapak.clear();
                        for (int i=0; i<resp.getBukalapak().getBukalapak().size(); i++) {
                            Record record = new Record(
                                    resp.getBukalapak().getBukalapak().get(i).getNama(),
                                    resp.getBukalapak().getBukalapak().get(i).getHarga(),
                                    resp.getBukalapak().getBukalapak().get(i).getRating(),
                                    resp.getBukalapak().getBukalapak().get(i).getView(),
                                    resp.getBukalapak().getBukalapak().get(i).getUrl(),
                                    resp.getBukalapak().getBukalapak().get(i).getGambar()
                            );
                            bukalapak.add(record);
                        }
                        title.add("Bukalapak");
                    }
                    if (resp.getShopee().getStatus().equals("success")){
                        shopee.clear();
                        for (int i=0; i<resp.getShopee().getShopee().size(); i++) {
                            Record record = new Record(
                                    resp.getShopee().getShopee().get(i).getNama(),
                                    resp.getShopee().getShopee().get(i).getHarga(),
                                    resp.getShopee().getShopee().get(i).getRating(),
                                    resp.getShopee().getShopee().get(i).getView(),
                                    resp.getShopee().getShopee().get(i).getUrl(),
                                    resp.getShopee().getShopee().get(i).getGambar()
                            );
                            shopee.add(record);
                        }
                        title.add("Shopee");
                    }

                    enable();

                    if (title.size() > 0 ) {
                        adapter = new RecyclerViewAdapter(SearchActivity.this, title, tokopedia, bukalapak, shopee);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    enable();
                    Snackbar.make(findViewById(R.id.root_search), t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private void enable() {
        keyword.setEnabled(true);
        btnSearch.setEnabled(true);
        recyclerView.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        btnClear.setVisibility(View.VISIBLE);
    }

    private void disable() {
        keyword.setEnabled(false);
        btnSearch.setEnabled(false);
        recyclerView.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        btnClear.setVisibility(View.INVISIBLE);
    }
}
