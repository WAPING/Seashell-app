package me.drakeet.seashell.ui.menu;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.drakeet.seashell.R;
import me.drakeet.seashell.utils.ToastUtils;

public class MenuAdapter extends BaseAdapter {

    public interface MenuListener {

        void onActiveViewChanged(View v);
    }

    private Context mContext;

    private List<Object> mItems;

    private MenuListener mListener;

    private int mActivePosition = -1;

    public MenuAdapter(Context context, List<Object> items) {
        mContext = context;
        mItems = items;
    }

    public void setListener(MenuListener listener) {
        mListener = listener;
    }

    public void setActivePosition(int activePosition) {
        mActivePosition = activePosition;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof Item ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
//        if (position == 2) {
//            ((MyMenuDrawer) mContext).getListView().getChildAt(2).setEnabled(false);
//            ((MyMenuDrawer) mContext).getListView().getChildAt(2).setClickable(false);
//            return false;
//        }
        return getItem(position) instanceof Item;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Object item = getItem(position);

        if (item instanceof Category) {
            if (v == null) {
                v = LayoutInflater.from(mContext).inflate(R.layout.menu_row_category, parent, false);
            }

            ((TextView) v).setText(((Category) item).mTitle);

        } else {
            if (v == null) {
                v = LayoutInflater.from(mContext).inflate(R.layout.menu_row_item, parent, false);
            }

            TextView tv = (TextView) v;
            tv.setText(((Item) item).mTitle);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(((Item) item).mIconRes, 0, 0, 0);
            } else {
                tv.setCompoundDrawablesWithIntrinsicBounds(((Item) item).mIconRes, 0, 0, 0);
            }
        }

        v.setTag(R.id.mdActiveViewPosition, position);

        if (position == mActivePosition) {
            mListener.onActiveViewChanged(v);
        }
        return v;
    }
}
