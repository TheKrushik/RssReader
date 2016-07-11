package info.krushik.android.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ReadRss extends AsyncTask<Void, Void, Void> {

    static final String URL_ADDRESS = "http://www.cbc.ca/cmlink/rss-topstories";
    static final String URL_GET = "GET";

    static final String TAG_ITEM = "item";
    static final String TAG_TITLE = "title";
    static final String TAG_AUTHOR = "author";
    static final String TAG_PUB_DATE = "pubDate";
    static final String TAG_LINK = "link";
    static final String TAG_DESCRIPTION = "description";

    Context mContext;
    ProgressDialog mProgressDialog;
    ArrayList<Card> mCards;
    RecyclerView mRecyclerView;
    URL mUrl;

    public ReadRss(Context context, RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mContext = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.string_loading));
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mProgressDialog.dismiss();
        CardAdapter adapter = new CardAdapter(mContext, mCards);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        parserXml(getDataXmlFromInternet());
        return null;
    }

    public void parserXml(Document data) {
        if (data != null) {
            mCards = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();

            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);

                if (currentChild.getNodeName().equalsIgnoreCase(TAG_ITEM)) {
                    Card item = new Card();
                    NodeList itemChilds = currentChild.getChildNodes();

                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node current = itemChilds.item(j);

                        if (current.getNodeName().equalsIgnoreCase(TAG_TITLE)) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase(TAG_AUTHOR)) {
                            item.setAuthor(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase(TAG_PUB_DATE)) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase(TAG_LINK)) {
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase(TAG_DESCRIPTION)) {
                            //this will return us thumbnail url
                            String description = current.getTextContent();
                            String url = description.substring(description.indexOf("src='") + 5, description.indexOf("' />"));
                            item.setThumbnailUrl(url);
                        }
                    }
                    mCards.add(item);
//                    Log.d("itemTitle", item.getTitle());
//                    Log.d("itemAutor", item.getAuthor());
//                    Log.d("itemPutData", item.getPubDate());
//                    Log.d("itemLink", item.getLink());
//                    Log.d("itemThumbnailUrl", item.getThumbnailUrl());


                }
            }

        }
    }


    public Document getDataXmlFromInternet() {
        try {
            mUrl = new URL(URL_ADDRESS);
            HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
            connection.setRequestMethod(URL_GET);
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
