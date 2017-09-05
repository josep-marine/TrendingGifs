package marine.josep.trendinggifs.activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import marine.josep.trendinggifs.R;
import marine.josep.trendinggifs.databinding.ActivityMainBinding;
import marine.josep.trendinggifs.model.GiphyModel;
import marine.josep.trendinggifs.service.GiphyService;
import marine.josep.trendinggifs.viewmodel.GiphyListViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding =  DataBindingUtil.setContentView(this, R.layout.activity_main);

        Map<String,String> params = new HashMap<>();
        params.put("limit","10");

        GiphyService.getInstance().getList(MainActivity.this, "trending", params, new GiphyService.ResponseListListener() {
            @Override
            public void onResponseList(List<GiphyModel> list) {

                GiphyListViewModel giphyListViewModel = new GiphyListViewModel();
                giphyListViewModel.list.addAll(list);
                activityMainBinding.setGiphyListViewModel(giphyListViewModel);

            }
        }, new GiphyService.ErrorListener() {
            @Override
            public void onError(VolleyError error) {

                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        },true);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
