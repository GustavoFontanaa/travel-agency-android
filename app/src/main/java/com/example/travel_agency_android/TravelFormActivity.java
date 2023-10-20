package com.example.travel_agency_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import adapter.ChildInfo;
import adapter.CustomExpandableListAdapter;
import adapter.GroupInformation;
import adapter.TravelAdapter;
import adapter.TravelModel;

public class TravelFormActivity extends AppCompatActivity {
    private LinkedHashMap<String, GroupInformation> mainSet = new LinkedHashMap<>();
    private ArrayList<GroupInformation> subSet = new ArrayList<GroupInformation>();

    private CustomExpandableListAdapter listAdapter;
    private ExpandableListView simpleExpandableListView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_travel_form);

        // add data for displaying in expandable list view
        loadData();

        // get reference of the ExpandableListView from activity_main
        simpleExpandableListView1 = (ExpandableListView) findViewById(R.id.simpleExpandableListView1);

        // create the adapter and by passing your ArrayList data
        listAdapter = new CustomExpandableListAdapter(TravelFormActivity.this, subSet);
        simpleExpandableListView1.setAdapter(listAdapter);

        // setOnChildClickListener listener for child row click, so that we can get the value
        simpleExpandableListView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // get the group header
                GroupInformation headerInfo = subSet.get(groupPosition);
                // get the child info
                ChildInfo detailInfo = headerInfo.getSubsetName().get(childPosition);
                // display it or do something with it
//                Toast.makeText(getBaseContext(), headerInfo.getName() + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // get the group header
                GroupInformation headerInfo = subSet.get(groupPosition);
                // display it or do something with it
                Toast.makeText(getBaseContext(), headerInfo.getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    // load some initial data into out list
    private void loadData() {

        addDetails("Gasolina", "1");
        addDetails("Tarifa Aérea", "2");
        addDetails("Refeições", "3");
        addDetails("Hospedagem", "4");
        addDetails("Entretenimento/Diversos", "5");
    }

    // here we maintain main set like Programming languages and subsets like Python
    private int addDetails(String mainSet, String subSet) {

        int groupPosition = 0;

        // check the hash map if the group already exists
        GroupInformation headerInfo = this.mainSet.get(mainSet);

        // add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new GroupInformation();
            headerInfo.setName(mainSet);
            this.mainSet.put(mainSet, headerInfo);
            this.subSet.add(headerInfo);
        }

        // get the children for the group
        ArrayList<ChildInfo> subList = headerInfo.getSubsetName();

        // size of the children list
        int listSize = subList.size();

        // add to the counter
        listSize++;

        // create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setName(subSet);
        subList.add(detailInfo);
        headerInfo.setSubsetName(subList);

        // find the group position inside the list
        groupPosition = this.subSet.indexOf(headerInfo);
        return groupPosition;
    }
}



//
// btnAdd = findViewById(R.id.btnAdd);
//
//         btnAdd.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//
//        }
//        });