package org.hackatron.hackatronapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class PaperAdapter extends ArrayAdapter<Paper>
{
    private final Context _context;
    private final ArrayList<Paper> _content;

    public PaperAdapter(Context context)
    {
        super(context, R.layout.paper_item);

        this._context = context;
        this._content = new ArrayList<Paper>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.paper_item, parent, false);
        TextView titleView = (TextView) rowView.findViewById(R.id.pager_item_title);
        TextView notesView = (TextView) rowView.findViewById(R.id.pager_item_description);
        Button button = (Button) rowView.findViewById(R.id.pager_item_vote_up);

        Paper paper = getItem(position);
        titleView.setText(paper.getTitle());
        notesView.setText(paper.getNotes());

        button.setOnClickListener(new VoteOnClick(paper));

        return rowView;
    }

    private class VoteOnClick implements View.OnClickListener
    {
        final Paper _paper;

        public VoteOnClick(Paper paper)
        {
            this._paper = paper;
        }

        @Override
        public void onClick(View view)
        {
            this._paper.addVote();
        }
    }
}
