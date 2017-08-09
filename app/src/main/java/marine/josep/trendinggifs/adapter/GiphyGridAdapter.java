package marine.josep.trendinggifs.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import marine.josep.trendinggifs.R;
import marine.josep.trendinggifs.databinding.ItemGiphyBinding;
import marine.josep.trendinggifs.model.GiphyModel;
import marine.josep.trendinggifs.viewmodel.GiphyListViewModel;

public class GiphyGridAdapter extends BaseAdapter{

    private GiphyListViewModel giphyListViewModel;
    private GiphyGridAdapter.ViewHolder viewHolder;

    public GiphyGridAdapter(GiphyListViewModel giphyListViewModel) {
        this.giphyListViewModel = giphyListViewModel;
    }

    @Override
    public int getCount() {
        return giphyListViewModel.list.size();
    }

    @Override
    public Object getItem(int position) {
        return giphyListViewModel.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final GiphyModel giphyModel = giphyListViewModel.list.get(position);

        viewHolder = new GiphyGridAdapter.ViewHolder();
        viewHolder.itemGiphyBinding = DataBindingUtil.inflate(inflater, R.layout.item_giphy, parent, false);
        View view = viewHolder.itemGiphyBinding.getRoot();
        view.setTag(viewHolder);

        viewHolder.itemGiphyBinding.setItem(giphyModel);

        return view;
    }

    class ViewHolder {
        ItemGiphyBinding itemGiphyBinding;
    }
}
