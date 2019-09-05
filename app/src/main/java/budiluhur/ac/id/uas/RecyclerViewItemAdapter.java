package budiluhur.ac.id.uas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import budiluhur.ac.id.uas.model.Record;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.MyViewHolder> {

    private List<Record> data;
    private Activity context;
    private NumberFormat nf;
    private DecimalFormat df;

    public RecyclerViewItemAdapter(Activity context, List<Record> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item, viewGroup, false);
        nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        df  = (DecimalFormat)nf;
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewItemAdapter.MyViewHolder holder, final int position) {
        final Record nomor = data.get(position);
        String harga = nf.format(nomor.getHarga());

        Picasso.get()
                .load(nomor.getGambar())
                .into(holder.gambar);
        holder.nama.setText(nomor.getNama());
        holder.harga.setText("Rp "+harga);
        holder.view.setText("("+String.valueOf(nomor.getView())+")");
        holder.rating.setRating(nomor.getRating());
        holder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(nomor.getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout url;
        public ImageView gambar;
        public TextView nama, harga, view;
        public RatingBar rating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            url = itemView.findViewById(R.id.url);
            gambar = itemView.findViewById(R.id.gambar);
            nama = itemView.findViewById(R.id.nama);
            harga = itemView.findViewById(R.id.harga);
            view = itemView.findViewById(R.id.count);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
