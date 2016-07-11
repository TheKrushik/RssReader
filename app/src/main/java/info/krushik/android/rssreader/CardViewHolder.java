package info.krushik.android.rssreader;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardViewHolder extends RecyclerView.ViewHolder {

    TextView mTitle;
    TextView mDate;
    TextView mAuthor;
    TextView mLink;
    ImageView mThumbnail;
    RelativeLayout mRootRelative;

    //объявляем конструктор
    public CardViewHolder(View itemView){
        super(itemView);

        mTitle = (TextView)itemView.findViewById(R.id.title_text);
        mDate = (TextView)itemView.findViewById(R.id.date_text);
        mAuthor = (TextView)itemView.findViewById(R.id.author_text);
        mLink = (TextView)itemView.findViewById(R.id.link_text);
        mThumbnail = (ImageView)itemView.findViewById(R.id.thumb_img);
        mRootRelative = (RelativeLayout)itemView.findViewById(R.id.myRelativeLayout);

    }


}
