package company.com.takeiteasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import company.com.takeiteasy.Fragments.CommentFragment;
import company.com.takeiteasy.Fragments.MenuItemFragment;
import company.com.takeiteasy.R;
import company.com.takeiteasy.Serializables.Cafe;
import company.com.takeiteasy.Serializables.FeedbackItem;
import company.com.takeiteasy.Serializables.CafeMenuItem;

public class CafeActivity extends AppCompatActivity {

    private Cafe _cafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafe_info);

        this._cafe = (Cafe) this.getIntent().getSerializableExtra("cafe");

        ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        TextView cafeName = findViewById(R.id.tv_cafe_name);
        cafeName.setText(this._cafe.cafeName);

        TextView cafeAddress = findViewById(R.id.tv_cafe_address);
        String cafeAddressText = this._cafe.cafeAddress.city + " " +
                this._cafe.cafeAddress.street + " д." + this._cafe.cafeAddress.house;
        cafeAddress.setText(cafeAddressText);

        TextView cafeOpeningHours = findViewById(R.id.tv_cafe_work_hours);
        String openingHoursText = this._cafe.cafeOpeningHours.openingTime + " - " + this._cafe.cafeOpeningHours.closingTime;
        cafeOpeningHours.setText(openingHoursText);

        ShowCafeMenuItems();
        if(this._cafe.cafeFeedback.length > 0) {
            ShowComments();
        } else {
            TextView commentsTitle = findViewById(R.id.tv_comments_title);
            commentsTitle.setText("");
        }
    }

    private void ShowCafeMenuItems()
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for(CafeMenuItem cafeMenuItem: this._cafe.cafeMenu) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("cafeMenuItem", cafeMenuItem);

            MenuItemFragment menuItemFragment = new MenuItemFragment();
            menuItemFragment.setArguments(bundle);

            fragmentTransaction.add(R.id.favorites_list_holder, menuItemFragment);
        }

        fragmentTransaction.commit();
    }

    private void ShowComments()
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for(FeedbackItem comment: this._cafe.cafeFeedback) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("comment", comment);

            CommentFragment commentFragment = new CommentFragment();
            commentFragment.setArguments(bundle);

            fragmentTransaction.add(R.id.comments_list, commentFragment);
        }

        fragmentTransaction.commit();
    }

    public void OpenCafeMenuItemPage(View v, CafeMenuItem cafeMenuItem) {
        Intent intent = new Intent(v.getContext(), CafeMenuItemActivity.class);
        intent.putExtra("cafeMenuItem", cafeMenuItem);


        startActivityForResult(intent, 0);
    }
}
