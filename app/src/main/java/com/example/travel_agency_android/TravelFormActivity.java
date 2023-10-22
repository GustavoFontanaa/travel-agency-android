package com.example.travel_agency_android;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import adapter.ChildInfo;
import adapter.CustomExpandableListAdapter;
import adapter.GroupInformation;

public class TravelFormActivity extends AppCompatActivity {
    private LinkedHashMap<String, GroupInformation> mainSet = new LinkedHashMap<>();
    private ArrayList<GroupInformation> subSet = new ArrayList<GroupInformation>();

    private CustomExpandableListAdapter listAdapter;
    private ExpandableListView simpleExpandableListView1;

    final private List<String> locaisPartida = Arrays.asList("Criciuma", "Laguna", "Urussanga");

    final private List<String> locaisChegada = Arrays.asList("Criciuma", "Laguna", "Urussanga");

    final private List<String> tipoLocomocao = Arrays.asList("Aviao", "Onibus", "Carro");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_travel_form);

        loadSpinnerLocalPartida();

        loadSpinnerLocalChegada();

        loadSpinnerLocomocao();

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

    private void loadSpinnerLocomocao() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoLocomocao);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.spLocomocao);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                // Faça algo com o item selecionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nada foi selecionado
            }
        });
    }

    private void loadSpinnerLocalChegada() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locaisChegada);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.spLocalChegada);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                // Faça algo com o item selecionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nada foi selecionado
            }
        });
    }

    private void loadSpinnerLocalPartida() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locaisPartida);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.spLocalPartida);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                // Faça algo com o item selecionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nada foi selecionado
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