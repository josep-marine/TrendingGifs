package marine.josep.androidinterviewexercise.service;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import marine.josep.androidinterviewexercise.R;
import marine.josep.androidinterviewexercise.model.GiphyModel;
import marine.josep.androidinterviewexercise.request.MyRequestQueue;

public class GiphyService {

    private static AlertDialog dialog;

    private static GiphyService instance;

    private GiphyService() {
        super();
    }

    public static GiphyService getInstance() {
        if (instance == null) {
            instance = new GiphyService();
        }
        return instance;
    }

    public void getList(final Context context, String path, Map<String, String> params, final ResponseListListener responseListListener, final ErrorListener errorListener, final boolean showLoadingDialog) {

        if (showLoadingDialog) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.loading);
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, prepareUrl(context, path, params), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    List<GiphyModel> list = new ArrayList<>();
                    JSONArray data = response.getJSONArray("data");

                    for(int index=0;index<data.length();index++){
                        Gson gson = new GsonBuilder().registerTypeAdapter(GiphyModel.class, new GiphyModelDeserializer()).create();
                        list.add(gson.fromJson(data.getJSONObject(index).toString(), GiphyModel.class));
                    }

                    responseListListener.onResponseList(list);

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                } catch (JSONException je) {
                    Log.i(context.getString(R.string.app_name), je.toString());
                }
            }
        }, errorListener != null ? new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (showLoadingDialog) {
                    dialog.show();
                }

                errorListener.onError(error);
            }
        } : null);

        MyRequestQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    private String prepareUrl(Context context, String path, Map<String, String> params) {

        StringBuffer url = new StringBuffer(context.getString(R.string.giphy_root));
        url.append(path);
        url.append("?api_key=" + context.getString(R.string.giphy_key));

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url.append("&");
                url.append(entry.getKey());
                url.append("=");
                url.append(entry.getValue());
            }
        }

        return url.toString();
    }

    private class GiphyModelDeserializer implements JsonDeserializer<GiphyModel> {

        @Override
        public GiphyModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            JsonObject jsonObject = jsonElement.getAsJsonObject().get("images").getAsJsonObject().get("fixed_width").getAsJsonObject();

            GiphyModel giphyModel = new GiphyModel();
            giphyModel.setUrl(jsonObject.get("url").getAsString());

            return giphyModel;
        }
    }

    public interface ResponseListListener {
        void onResponseList(List<GiphyModel> list);
    }

    public interface ErrorListener {
        void onError(VolleyError error);
    }

}
