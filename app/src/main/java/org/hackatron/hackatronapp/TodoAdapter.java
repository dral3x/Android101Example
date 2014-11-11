package org.hackatron.hackatronapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class TodoAdapter extends BaseAdapter
{
    private static final String STATE_KEY = "TodoAdapter:content";

    private ArrayList<String> _content;

    public TodoAdapter()
    {
        this._content = new ArrayList<String>();
    }

    public void add(String item)
    {
        this._content.add(item);

        this.notifyDataSetChanged();
    }

    public void remove(String item)
    {
        if (!this._content.contains(item)) {
            return;
        }

        this._content.remove(item);

        this.notifyDataSetChanged();
    }

    public void update(String original, String updated)
    {
        if (!this._content.contains(original)) {
            return;
        }

        this._content.set(this._content.indexOf(original), updated);

        this.notifyDataSetChanged();
    }

    /*public void saveInstanceState(Bundle outState)
    {
        outState.putSerializable(STATE_KEY, this._content);
    }

    public void restoreInstanceState(Bundle state)
    {
        if (state != null && state.containsKey(STATE_KEY)) {
            this._content = (ArrayList<String>) state.getSerializable(STATE_KEY);
        }

        this.notifyDataSetChanged();
    }*/

    @Override
    public int getCount()
    {
        return this._content.size();
    }

    @Override
    public Object getItem(int i)
    {
        return i >= 0 && i < this._content.size() ? this._content.get(i) : null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        String item = (String) this.getItem(i);

        if (view != null) {
            this._hydrateView(item, view);
            return view;
        }

        View newView = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        this._hydrateView(item, newView);
        return newView;
    }

    private void _hydrateView(String item, View view)
    {
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(item);
    }
}
