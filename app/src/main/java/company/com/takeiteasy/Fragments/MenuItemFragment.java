package company.com.takeiteasy.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import company.com.takeiteasy.Activities.CafeActivity;
import company.com.takeiteasy.R;
import company.com.takeiteasy.Serializables.CafeMenuItem;

public class MenuItemFragment extends Fragment {
    private CafeMenuItem _cafeMenuItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_item_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this._cafeMenuItem = getArguments() != null ? (CafeMenuItem) getArguments().getSerializable("cafeMenuItem"): null;

        TextView menuItemNameTV = view.findViewById(R.id.menu_item_name_tv);
        menuItemNameTV.setText(this._cafeMenuItem.itemName);

        TextView menuItemCookingTimeTV = view.findViewById(R.id.menu_item_cooking_time_tv);
        menuItemCookingTimeTV.setText(this._cafeMenuItem.time);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ((CafeActivity) getActivity()).OpenCafeMenuItemPage(v, _cafeMenuItem);
                }
            }
        });
    }


}
