package budiluhur.ac.id.uas;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import budiluhur.ac.id.uas.model.Record;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<String> judul;
    private List<Record> tokopedia;
    private List<Record> bukalapak;
    private List<Record> shopee;
    private Activity context;
    private RecyclerViewItemAdapter item_adapter;

    public RecyclerViewAdapter(Activity context, List<String> judul, List<Record> tokopedia, List<Record> bukalapak, List<Record> shopee) {
        this.context = context;
        this.judul = judul;
        this.tokopedia = tokopedia;
        this.bukalapak = bukalapak;
        this.shopee = shopee;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_toko, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.title.setText(judul.get(position));
        holder.item.setLayoutManager(new LinearLayoutManager(context));

        if (judul.get(position).equals("Tokopedia")) {
            item_adapter = new RecyclerViewItemAdapter(context, tokopedia);
        }
        else if (judul.get(position).equals("Bukalapak")) {
            item_adapter = new RecyclerViewItemAdapter(context, bukalapak);
        }
        else {
            item_adapter = new RecyclerViewItemAdapter(context, shopee);
        }

        holder.item.setAdapter(item_adapter);
    }

    @Override
    public int getItemCount() {
        return judul.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public RecyclerView item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            item = itemView.findViewById(R.id.item);
        }
    }
}