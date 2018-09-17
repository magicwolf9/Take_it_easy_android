package company.com.takeiteasy.Fragments;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import company.com.takeiteasy.R;

public class CommentsList extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Использование собственного шаблона
        ArrayAdapter<CommentFragment> adapter = new ArrayAdapter<CommentFragment>(this,
                R.layout.comment_fragment);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }

}

