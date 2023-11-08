package adapter;

import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;

import java.text.DecimalFormat;

public class TravelCalculator {
    private TextView totalGasolinaTextView;
    private TextView totalPassagemAereaTextView;
    private EditText totalKmEditText;
    private EditText mediaKmPorLitroEditText;
    private EditText custoMedioPorLitroEditText;
    private EditText qtdVeiculosEditText;
    private EditText custoPessoaEditText;
    private EditText aluguelVeiculoEditText;

    public TravelCalculator(
            TextView totalGasolinaTextView, TextView totalPassagemAereaTextView,
            EditText totalKmEditText, EditText mediaKmPorLitroEditText,
            EditText custoMedioPorLitroEditText, EditText qtdVeiculosEditText,
            EditText custoPessoaEditText, EditText aluguelVeiculoEditText
    ) {
        this.totalGasolinaTextView = totalGasolinaTextView;
        this.totalPassagemAereaTextView = totalPassagemAereaTextView;
        this.totalKmEditText = totalKmEditText;
        this.mediaKmPorLitroEditText = mediaKmPorLitroEditText;
        this.custoMedioPorLitroEditText = custoMedioPorLitroEditText;
        this.qtdVeiculosEditText = qtdVeiculosEditText;
        this.custoPessoaEditText = custoPessoaEditText;
        this.aluguelVeiculoEditText = aluguelVeiculoEditText;

        setupTextWatchers();
    }

    private void setupTextWatchers() {
        totalKmEditText.addTextChangedListener(createTextWatcher(totalGasolinaTextView));
        mediaKmPorLitroEditText.addTextChangedListener(createTextWatcher(totalGasolinaTextView));
        custoMedioPorLitroEditText.addTextChangedListener(createTextWatcher(totalGasolinaTextView));
        qtdVeiculosEditText.addTextChangedListener(createTextWatcher(totalGasolinaTextView));
        custoPessoaEditText.addTextChangedListener(createTextWatcher(totalPassagemAereaTextView));
        aluguelVeiculoEditText.addTextChangedListener(createTextWatcher(totalPassagemAereaTextView));
    }

    private TextWatcher createTextWatcher(final TextView totalTextView) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTotalValues(totalTextView);
            }
        };
    }

    private void updateTotalValues(TextView totalTextView) {
        String totalKmText = totalKmEditText.getText().toString();
        String mediaKmPorLitroText = mediaKmPorLitroEditText.getText().toString();
        String custoMedioPorLitroText = custoMedioPorLitroEditText.getText().toString();
        String qtdVeiculosText = qtdVeiculosEditText.getText().toString();
        String custoPessoaText = custoPessoaEditText.getText().toString();
        String aluguelVeiculoText = aluguelVeiculoEditText.getText().toString();

        if (totalTextView == totalGasolinaTextView) {
            if (isEmpty(totalKmText) || isEmpty(mediaKmPorLitroText) || isEmpty(custoMedioPorLitroText) || isEmpty(qtdVeiculosText)) {
                totalTextView.setText("Total Gasolina: Valores em branco");
                return;
            }
        } else if (totalTextView == totalPassagemAereaTextView) {
            if (isEmpty(custoPessoaText) || isEmpty(aluguelVeiculoText)) {
                totalTextView.setText("Total Passagem Aérea: Valores em branco");
                return;
            }
        }

        if (totalTextView == totalGasolinaTextView) {
            int qtdVeiculos = Integer.parseInt(qtdVeiculosText);
            double totalKm = Double.parseDouble(totalKmText);
            double mediaKmPorLitro = Double.parseDouble(mediaKmPorLitroText);
            double custoMedioPorLitro = Double.parseDouble(custoMedioPorLitroText);

            double totalGasolina = calculateTotalGasoline(totalKm, mediaKmPorLitro, custoMedioPorLitro, qtdVeiculos);
            setTotalTextView(totalTextView, totalGasolina);
        } else if (totalTextView == totalPassagemAereaTextView) {
            double custoPessoa = Double.parseDouble(custoPessoaText);
            double aluguelVeiculo = Double.parseDouble(aluguelVeiculoText);

            double totalPassagemAerea = calculateTotalPassagemAerea(custoPessoa, aluguelVeiculo);
            setTotalTextView(totalTextView, totalPassagemAerea);
        }
    }

    private void setTotalTextView(TextView totalTextView, double totalValue) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String totalValueFormatted = decimalFormat.format(totalValue);
        totalTextView.setText("Total: " + totalValueFormatted);
    }

    private boolean isEmpty(String text) {
        return text.trim().isEmpty();
    }

    private double calculateTotalGasoline(
            double totalKm,
            double mediaKmPorLitro,
            double custoMedioPorLitro,
            int qtdVeiculos
    ) {
        return ((totalKm / mediaKmPorLitro) * custoMedioPorLitro) / qtdVeiculos;
    }

    private double calculateTotalPassagemAerea(double custoPessoa, double aluguelVeiculo) {
        return custoPessoa + aluguelVeiculo;
    }
}

