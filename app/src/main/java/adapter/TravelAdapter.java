package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.travel_agency_android.MainActivity;
import com.example.travel_agency_android.R;
import com.example.travel_agency_android.ReportFormActivity;

import java.util.ArrayList;

public class TravelAdapter extends BaseAdapter {
    private ArrayList<TravelModel> travelList;
    private Activity activity;

    public TravelAdapter(final Activity activity) {
        this.activity = activity;
    }

    public void setTravelList(ArrayList<TravelModel> travels){
        travelList = travels;
    }

    @Override
    public int getCount() {
        return travelList != null ? travelList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return travelList != null ? travelList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            view = activity.getLayoutInflater().inflate(R.layout.item_lista, viewGroup, false);
        }

        TravelModel travel = travelList.get(position);

        TextView name = view.findViewById(R.id.travelName);
        name.setText(travel.getName());

        TextView description = view.findViewById(R.id.travelDescription);
        description.setText(travel.getDescription());

        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnDelete = view.findViewById(R.id.btnEdit);
        Button btnViewReport = view.findViewById(R.id.btnViewReport);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReportFormActivity.class);
                activity.startActivity(intent);
            }
        });

        return view;
    }
}
