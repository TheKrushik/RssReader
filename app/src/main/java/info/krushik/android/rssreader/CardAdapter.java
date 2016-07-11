package info.krushik.android.rssreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    static final String TEG_URL_NEWS = "url";

    ArrayList<Card> mCards;
    Context mContext;

    public CardAdapter(Context context, ArrayList<Card> cards) {
        this.mCards = cards;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final Card current = mCards.get(position);
        holder.mTitle.setText(current.getTitle());
        holder.mDate.setText(current.getPubDate());
        holder.mAuthor.setText(current.getAuthor());
        holder.mLink.setText(current.getLink());
        Picasso.with(mContext).load(current.getThumbnailUrl()).into(holder.mThumbnail);

        //click
        holder.mRootRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = current.getLink();

//                Log.d("itemLink", url);
                Intent intent = new Intent(mContext, DetailNewsActivity.class);
                intent.putExtra(TEG_URL_NEWS, url);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custum_row_news_item, parent, false);
        CardViewHolder holder = new CardViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

}
