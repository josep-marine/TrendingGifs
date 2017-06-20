package marine.josep.androidinterviewexercise.binder;

import android.databinding.BindingAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import marine.josep.androidinterviewexercise.adapter.GiphyGridAdapter;
import marine.josep.androidinterviewexercise.model.GiphyModel;
import marine.josep.androidinterviewexercise.viewmodel.GiphyListViewModel;

//This class contains all custom bindings. Android only identifies them by the parameters.

public class ListBinder {

    @BindingAdapter({"android:src"})
    public static void bindGiphyListViewModel(GridView gridView, GiphyListViewModel giphyListViewModel) {
        if(giphyListViewModel!=null){
            GiphyGridAdapter giphyGridAdapter = new GiphyGridAdapter(giphyListViewModel);
            gridView.setAdapter(giphyGridAdapter);
        }
    }

    @BindingAdapter({"android:giphyModel"})
    public static void bindGif(ImageView imageView, final GiphyModel giphyModel) {
        if(giphyModel!=null){
            Glide.with(imageView.getContext())
                    .asGif()
                    .load(giphyModel.getUrl())
                    .into(imageView)
                    .onStart();

        }
    }
}
