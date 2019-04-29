package com.fede.librame.Clases;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.fede.librame.Activities.newbook;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class fetchBooks {

    private JSONArray docs;

    private String ISBN13;
    private String ISBN10;
    private String openLibraryId;
    private String titulo;
    private String autor;
    private String fechapublicacion;
    private String editorial;
    private String paginas="0";
    private boolean finished = false;

    BookClient client = new BookClient();

    public fetchBooks(String query, final Context context) {
        client.getBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("docs");

                        for (int i = 0; i < docs.length(); i++) {
                            JSONObject bookJson = null;
                            try {
                                bookJson = docs.getJSONObject(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                                continue;
                            }
                            if(finished == false) {
                                if (bookJson.has("title")) {
                                    titulo = bookJson.getString("title");
                                    Toast.makeText(context, String.valueOf(finished), Toast.LENGTH_SHORT).show();
                                }

                                autor = getAuthor(bookJson);
                                fechapublicacion = bookJson.has("first_publish_year") ? bookJson.getString("first_publish_year") : "";

                                if (bookJson.has("cover_edition_key")) {
                                    openLibraryId = bookJson.getString("cover_edition_key");
                                } else if (bookJson.has("edition_key")) {
                                    final JSONArray ids = bookJson.getJSONArray("edition_key");
                                    openLibraryId = ids.getString(0);
                                }

                                if (!(openLibraryId.equals(""))) {
                                    getDetails(openLibraryId, context);
                                }
                            }
                            finished=true;
                        }
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDetails (String ID,final Context context){
        client.getExtraBookDetails(ID, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject resp) {
                try {
                    if (resp.has("publishers")) {
                        // display comma separated list of publishers
                        final JSONArray publisher = resp.getJSONArray("publishers");
                        final int numPublishers = publisher.length();
                        final String[] publishers = new String[numPublishers];
                        for (int i = 0; i < numPublishers; ++i) {
                            publishers[i] = publisher.getString(i);
                        }
                        editorial = TextUtils.join(", ", publishers);
                    }
                    if (resp.has("number_of_pages")) {
                        paginas = String.valueOf(resp.getInt("number_of_pages"));
                    } else {
                        paginas = "0";
                    }

                    if(resp.has("isbn_13")){
                        ISBN13 = resp.getString("isbn_13").replace("[","").replace("]","").replace("\"","");
                    }
                    if(resp.has("isbn_10")){
                        ISBN10 = resp.getString("isbn_10").replace("[","").replace("]","").replace("\"","");
                    }

                    ((newbook)context).Refresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static String getAuthor(final JSONObject jsonObject) {
        try {
            final JSONArray authors = jsonObject.getJSONArray("author_name");
            int numAuthors = authors.length();
            final String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; ++i) {
                authorStrings[i] = authors.getString(i);
            }
            return TextUtils.join(", ", authorStrings);
        } catch (JSONException e) {
            return "";
        }
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getFechapublicacion() {
        return fechapublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public String getCoverUrl() {
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-M.jpg?default=false";
    }

    public String getPaginas() {
        return paginas;
    }

    public JSONArray getDocs() {
        return docs;
    }

    // Get large sized book cover from covers API
    public String getLargeCoverUrl() {
        return "http://covers.openlibrary.org/b/olid/" + openLibraryId + "-L.jpg?default=false";
    }

    public String getISBN10() {
        return ISBN10;
    }

    public String getISBN13() {
        return ISBN13;
    }
    public boolean getStatus(){
        return finished;
    }
}
