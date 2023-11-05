package com.example.travel_agency_android;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_agency_android.databinding.ActivitySignupBinding;
import com.example.travel_agency_android.databinding.ActivityTravelFormBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import adapter.ChildInfo;
import adapter.CustomExpandableListAdapter;
import adapter.GroupInformation;
import models.AccommodationModelDB;
import models.AirfareModelDB;
import models.EntertainmentModelDB;
import models.GasolineModelDB;
import models.MealModelDB;
import models.TravelModelDB;

public class TravelFormActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
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

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areAllFieldsFilled()) {
                    if (insertData()) {
                        Toast.makeText(TravelFormActivity.this, "Viagem registrada com sucesso.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TravelFormActivity.this, "Erro ao registrar viagem.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TravelFormActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean areAllFieldsFilled() {
        Spinner spLocalPartida = findViewById(R.id.spLocalPartida);
        Spinner spLocalChegada = findViewById(R.id.spLocalChegada);
        Spinner spLocomocao = findViewById(R.id.spLocomocao);
        EditText etTravelName = findViewById(R.id.etNomeViagem);
        EditText etDescription = findViewById(R.id.etDescricaoViagem);
        EditText etQuantasPessoas = findViewById(R.id.etQuantasPessoas);

        String selectedLocalPartida = spLocalPartida.getSelectedItem().toString();
        String selectedLocalChegada = spLocalChegada.getSelectedItem().toString();
        String selectedLocomocao = spLocomocao.getSelectedItem().toString();
        String travelName = etTravelName.getText().toString();
        String description = etDescription.getText().toString();
        String quantidadePessoas = etQuantasPessoas.getText().toString();

        return !selectedLocalPartida.isEmpty() && !selectedLocalChegada.isEmpty() &&
                !selectedLocomocao.isEmpty() && !travelName.isEmpty() &&
                !description.isEmpty() && !quantidadePessoas.isEmpty();
    }

    private boolean insertData() {
        long travelId = insertTravel();

        if (travelId == -1) {
            return false;
        }

        GasolineModelDB gasoline = new GasolineModelDB();
        AirfareModelDB airfare = new AirfareModelDB();
        MealModelDB meal = new MealModelDB();

        if (!insertGasoline(travelId, gasoline) &&
                !insertAirfare(travelId, airfare) &&
                !insertMeals(travelId, meal)) {
            return false;
        }

        //arrumar essa logica//////////////////////////

        return true;
    }

    private long insertTravel() {
        Spinner spLocalPartida = findViewById(R.id.spLocalPartida);
        Spinner spLocalChegada = findViewById(R.id.spLocalChegada);
        Spinner spLocomocao = findViewById(R.id.spLocomocao);

        String selectedLocalPartida = spLocalPartida.getSelectedItem().toString();
        String selectedLocalChegada = spLocalChegada.getSelectedItem().toString();
        String selectedLocomocao = spLocomocao.getSelectedItem().toString();

        EditText etTravelName = findViewById(R.id.etNomeViagem);
        EditText etDescription = findViewById(R.id.etDescricaoViagem);
        int quantidadePessoas = getIntValueFromEditText(R.id.etQuantasPessoas);

        TravelModelDB travel = new TravelModelDB();
        travel.setNumberOfPeople(quantidadePessoas);
        travel.setDepartureLocation(selectedLocalPartida);
        travel.setArrivalLocation(selectedLocalChegada);
        travel.setTransportationMode(selectedLocomocao);

        travel.setTravelName(etTravelName.getText().toString());
        travel.setDescription(etDescription.getText().toString());

        DatabaseHelper dbHelper = new DatabaseHelper(TravelFormActivity.this);
        return dbHelper.insertTravel(travel);
    }

    private boolean insertGasoline(long travelId, GasolineModelDB gasoline) {
        double totalKm = getDoubleValueFromEditText(R.id.total_km);
        double mediaKmPorLitro = getDoubleValueFromEditText(R.id.media_km_por_l);
        double custoMedioPorLitro = getDoubleValueFromEditText(R.id.custo_medio_litro);
        int qtdVeiculos = getIntValueFromEditText(R.id.qtd_veiculos);
        double totalGasoline = calculateTotalGasoline(totalKm, mediaKmPorLitro, custoMedioPorLitro, qtdVeiculos);

        gasoline = new GasolineModelDB(travelId, totalKm, mediaKmPorLitro, custoMedioPorLitro, qtdVeiculos, totalGasoline);

        DatabaseHelper dbHelper = new DatabaseHelper(TravelFormActivity.this);
        return dbHelper.insertGasoline(gasoline);
    }

    private boolean insertAirfare(long travelId, AirfareModelDB airfare) {
        double custoPessoa = getDoubleValueFromEditText(R.id.custo_pessoa);
        double aluguelVeiculo = getDoubleValueFromEditText(R.id.aluguel_veiculo);
        double totalAirfare = calculateTotalAirfare(custoPessoa, aluguelVeiculo);

        airfare = new AirfareModelDB(travelId, custoPessoa, aluguelVeiculo, totalAirfare);

        DatabaseHelper dbHelper = new DatabaseHelper(TravelFormActivity.this);
        return dbHelper.insertAirfare(airfare);
    }

    private boolean insertMeals(long travelId, MealModelDB meal) {
        double mealCost = getDoubleValueFromEditText(R.id.custo_refeicao);
        int mealsPerDay = getIntValueFromEditText(R.id.refeicoes_dia);
        double totalMeals = calculateTotalMeals(mealCost, mealsPerDay);

        meal = new MealModelDB(travelId, mealCost, mealsPerDay, totalMeals);

        DatabaseHelper dbHelper = new DatabaseHelper(TravelFormActivity.this);
        return dbHelper.insertMeal(meal);
    }

    private boolean insertAccommodation(long travelId, AccommodationModelDB accommodation) {
        double custoPorNoite = getDoubleValueFromEditText(R.id.custo_noite);
        int noites = getIntValueFromEditText(R.id.noites);
        int quartos = getIntValueFromEditText(R.id.quartos);
        double total = calculateTotalAccommodation(custoPorNoite, noites, quartos);

        accommodation = new AccommodationModelDB(travelId, custoPorNoite, noites, quartos, total);

        DatabaseHelper dbHelper = new DatabaseHelper(TravelFormActivity.this);
        return dbHelper.insertAccommodation(accommodation);
    }

    private boolean insertEntertainment(long travelId, EntertainmentModelDB entertainment) {
        CheckBox option1 = findViewById(R.id.option1);
        CheckBox option2 = findViewById(R.id.option2);
        CheckBox option3 = findViewById(R.id.option3);
        CheckBox option4 = findViewById(R.id.option4);
        CheckBox option5 = findViewById(R.id.option5);
        CheckBox option6 = findViewById(R.id.option6);
        CheckBox option7 = findViewById(R.id.option7);
        CheckBox option8 = findViewById(R.id.option8);
        CheckBox option9 = findViewById(R.id.option9);
        CheckBox option10 = findViewById(R.id.option10);

        double totalCost = 0.0;

        if (option1.isChecked()) {
            totalCost += 80.00;
        }

        if (option2.isChecked()) {
            totalCost += 120.00;
        }

        if (option3.isChecked()) {
            totalCost += 50.00;
        }

        if (option4.isChecked()) {
            totalCost += 150.00;
        }

        if (option5.isChecked()) {
            totalCost += 40.00;
        }

        if (option6.isChecked()) {
            totalCost += 80.00;
        }

        if (option7.isChecked()) {
            totalCost += 30.00;
        }

        if (option8.isChecked()) {
            totalCost += 70.00;
        }

        if (option9.isChecked()) {
            totalCost += 90.00;
        }

        if (option10.isChecked()) {
            totalCost += 60.00;
        }

        entertainment = new EntertainmentModelDB(
                travelId,
                option1.isChecked() ? 1 : 0,
                option2.isChecked() ? 1 : 0,
                option3.isChecked() ? 1 : 0,
                option4.isChecked() ? 1 : 0,
                option5.isChecked() ? 1 : 0,
                option6.isChecked() ? 1 : 0,
                option7.isChecked() ? 1 : 0,
                option8.isChecked() ? 1 : 0,
                option9.isChecked() ? 1 : 0,
                option10.isChecked() ? 1 : 0,
                totalCost
        );

        DatabaseHelper dbHelper = new DatabaseHelper(TravelFormActivity.this);
        return dbHelper.insertEntertainment(entertainment);
    }

    private double getDoubleValueFromEditText(int editTextId) {
        EditText editText = findViewById(editTextId);
        String text = editText.getText().toString();
        if (!text.isEmpty()) {
            return Double.parseDouble(text);
        }
        return 0.0;
    }

    private int getIntValueFromEditText(int editTextId) {
        EditText editText = findViewById(editTextId);
        String text = editText.getText().toString();
        if (!text.isEmpty()) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    private double calculateTotalGasoline(double totalKm, double mediaKmPorLitro, double custoMedioPorLitro, int qtdVeiculos) {
        // Implemente o cálculo do total da gasolina aqui
        return 0.0;
    }

    private double calculateTotalAirfare(double custoPessoa, double aluguelVeiculo) {
        // Implemente o cálculo do custo total do "airfare" aqui
        return custoPessoa + aluguelVeiculo;
    }

    private double calculateTotalMeals(double mealCost, int mealsPerDay) {
        return mealCost * mealsPerDay;
    }

    private double calculateTotalAccommodation(double costPerNight, int totalNights, int totalRooms) {
        return costPerNight * totalNights * totalRooms;
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
