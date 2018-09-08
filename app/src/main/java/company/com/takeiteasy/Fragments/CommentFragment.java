package company.com.takeiteasy.Fragments;

import android.os.Bundle;
import 	android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import company.com.takeiteasy.R;

public class CommentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.comment_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String commentText = getArguments() != null ?  getArguments().getString("commentText") : "";
        TextView comentTV = view.findViewById(R.id.comment_text);
        comentTV.setText(commentText);
    }
}
