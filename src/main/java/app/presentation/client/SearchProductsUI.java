package app.presentation.client;


import org.apache.commons.collections.map.AbstractHashedMap;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchProductsUI {
    private JFrame frame;
    private JTextField titleTextField;
    private RangeSlider ratingRangeSlider;
    private RangeSlider proteinRangeSlider;
    private RangeSlider caloriesRangeSlider;
    private RangeSlider fatRangeSlider;
    private RangeSlider sodiumRangeSlider;
    private RangeSlider priceRangeSlider;
    private ArrayList<RangeSlider> rangeSliderList;

    private JLabel minRatingLabel;
    private JTextField minCaloriesTextField;
    private JTextField minProteinTextField;
    private JTextField minFatTextField;
    private JTextField minSodiumTextField;
    private JTextField minPriceTextField;

    private JLabel maxRatingLabel;
    private JTextField maxCaloriesTextField;
    private JTextField maxProteinTextField;
    private JTextField maxFatTextField;
    private JTextField maxSodiumTextField;
    private JTextField maxPriceTextField;

    private Map<JTextField, JTextField> rangeTextFieldMap;

    private JButton searchExecuteButton;

    public JButton getSearchExecuteButton() {
        return searchExecuteButton;
    }

    public JLabel getMinRatingLabel() {
        return minRatingLabel;
    }

    public JTextField getMinCaloriesTextField() {
        return minCaloriesTextField;
    }

    public JTextField getMinProteinTextField() {
        return minProteinTextField;
    }

    public JTextField getMinFatTextField() {
        return minFatTextField;
    }

    public JTextField getMinSodiumTextField() {
        return minSodiumTextField;
    }

    public JTextField getMinPriceTextField() {
        return minPriceTextField;
    }

    public JLabel getMaxRatingLabel() {
        return maxRatingLabel;
    }

    public JTextField getMaxCaloriesTextField() {
        return maxCaloriesTextField;
    }

    public JTextField getMaxProteinTextField() {
        return maxProteinTextField;
    }

    public JTextField getMaxFatTextField() {
        return maxFatTextField;
    }

    public JTextField getMaxSodiumTextField() {
        return maxSodiumTextField;
    }

    public JTextField getMaxPriceTextField() {
        return maxPriceTextField;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public RangeSlider getRatingRangeSlider() {
        return ratingRangeSlider;
    }

    public RangeSlider getProteinRangeSlider() {
        return proteinRangeSlider;
    }

    public RangeSlider getCaloriesRangeSlider() {
        return caloriesRangeSlider;
    }

    public RangeSlider getFatRangeSlider() {
        return fatRangeSlider;
    }

    public RangeSlider getSodiumRangeSlider() {
        return sodiumRangeSlider;
    }

    public RangeSlider getPriceRangeSlider() {
        return priceRangeSlider;
    }

    public ArrayList<RangeSlider> getRangeSliderList() {
        return rangeSliderList;
    }

    public Map<JTextField, JTextField> getRangeTextFieldMap() {
        return rangeTextFieldMap;
    }

    public SearchProductsUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Search Manager");
        frame.setBounds(100, 100, 397, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{40, 80, 40, 0};
        gridBagLayout.rowHeights = new int[]{16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);

        JLabel titleLabel = new JLabel("Title");
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 1;
        frame.getContentPane().add(titleLabel, gbc_titleLabel);

        titleTextField = new JTextField();
        GridBagConstraints gbc_titleTextField = new GridBagConstraints();
        titleTextField.setName("Title");
        titleTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_titleTextField.insets = new Insets(0, 0, 5, 5);
        gbc_titleTextField.fill = GridBagConstraints.VERTICAL;
        gbc_titleTextField.gridx = 1;
        gbc_titleTextField.gridy = 2;
        frame.getContentPane().add(titleTextField, gbc_titleTextField);
        titleTextField.setColumns(10);

        JLabel ratingLabel = new JLabel("Rating");
        GridBagConstraints gbc_ratingLabel = new GridBagConstraints();
        gbc_ratingLabel.insets = new Insets(0, 0, 5, 5);
        gbc_ratingLabel.gridx = 1;
        gbc_ratingLabel.gridy = 3;
        frame.getContentPane().add(ratingLabel, gbc_ratingLabel);

        minRatingLabel = new JLabel("0");
        GridBagConstraints gbc_minRatingLabel = new GridBagConstraints();
        gbc_minRatingLabel.insets = new Insets(0, 0, 5, 5);
        gbc_minRatingLabel.gridx = 0;
        gbc_minRatingLabel.gridy = 4;
        frame.getContentPane().add(minRatingLabel, gbc_minRatingLabel);

        ratingRangeSlider = new RangeSlider(0, 5);
        ratingRangeSlider.setName("rating slider");
        GridBagConstraints gbc_ratingRangeSlider = new GridBagConstraints();
        gbc_ratingRangeSlider.insets = new Insets(0, 0, 5, 5);
        gbc_ratingRangeSlider.gridx = 1;
        gbc_ratingRangeSlider.gridy = 4;
        frame.getContentPane().add(ratingRangeSlider, gbc_ratingRangeSlider);

        maxRatingLabel = new JLabel("5");
        GridBagConstraints gbc_maxRatingLabel = new GridBagConstraints();
        gbc_maxRatingLabel.insets = new Insets(0, 0, 5, 0);
        gbc_maxRatingLabel.gridx = 2;
        gbc_maxRatingLabel.gridy = 4;
        frame.getContentPane().add(maxRatingLabel, gbc_maxRatingLabel);

        JLabel caloriesTextField = new JLabel("Calories");
        GridBagConstraints gbc_caloriesTextField = new GridBagConstraints();
        gbc_caloriesTextField.insets = new Insets(0, 0, 5, 5);
        gbc_caloriesTextField.gridx = 1;
        gbc_caloriesTextField.gridy = 5;
        frame.getContentPane().add(caloriesTextField, gbc_caloriesTextField);

        minCaloriesTextField = new JTextField();
        minCaloriesTextField.setName("Min calories");
        minCaloriesTextField.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_minCaloriesTextField = new GridBagConstraints();
        gbc_minCaloriesTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_minCaloriesTextField.insets = new Insets(0, 0, 5, 5);
        gbc_minCaloriesTextField.gridx = 0;
        gbc_minCaloriesTextField.gridy = 6;
        frame.getContentPane().add(minCaloriesTextField, gbc_minCaloriesTextField);

        caloriesRangeSlider = new RangeSlider(0, 1000);
        caloriesRangeSlider.setName("calories slider");
        GridBagConstraints gbc_caloriesRangeSlider = new GridBagConstraints();
        gbc_caloriesRangeSlider.insets = new Insets(0, 0, 5, 5);
        gbc_caloriesRangeSlider.gridx = 1;
        gbc_caloriesRangeSlider.gridy = 6;
        frame.getContentPane().add(caloriesRangeSlider, gbc_caloriesRangeSlider);

        maxCaloriesTextField = new JTextField();
        maxCaloriesTextField.setName("Max calories");
        GridBagConstraints gbc_maxCaloriesTextField = new GridBagConstraints();
        maxCaloriesTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_maxCaloriesTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxCaloriesTextField.insets = new Insets(0, 0, 5, 0);
        gbc_maxCaloriesTextField.gridx = 2;
        gbc_maxCaloriesTextField.gridy = 6;
        frame.getContentPane().add(maxCaloriesTextField, gbc_maxCaloriesTextField);

        JLabel proteinTextField = new JLabel("Protein");
        GridBagConstraints gbc_proteinTextField = new GridBagConstraints();
        gbc_proteinTextField.insets = new Insets(0, 0, 5, 5);
        gbc_proteinTextField.gridx = 1;
        gbc_proteinTextField.gridy = 7;
        frame.getContentPane().add(proteinTextField, gbc_proteinTextField);

        minProteinTextField = new JTextField();
        minProteinTextField.setName("Min protein");
        GridBagConstraints gbc_minProteinTextField = new GridBagConstraints();
        minProteinTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_minProteinTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_minProteinTextField.insets = new Insets(0, 0, 5, 5);
        gbc_minProteinTextField.gridx = 0;
        gbc_minProteinTextField.gridy = 8;
        frame.getContentPane().add(minProteinTextField, gbc_minProteinTextField);

        proteinRangeSlider = new RangeSlider(0, 1000);
        proteinRangeSlider.setName("protein slider");
        GridBagConstraints gbc_proteinRangeSlider = new GridBagConstraints();
        gbc_proteinRangeSlider.insets = new Insets(0, 0, 5, 5);
        gbc_proteinRangeSlider.gridx = 1;
        gbc_proteinRangeSlider.gridy = 8;
        frame.getContentPane().add(proteinRangeSlider, gbc_proteinRangeSlider);

        maxProteinTextField = new JTextField();
        maxProteinTextField.setName("Max protein");
        GridBagConstraints gbc_maxProteinTextField = new GridBagConstraints();
        maxProteinTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_maxProteinTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxProteinTextField.insets = new Insets(0, 0, 5, 0);
        gbc_maxProteinTextField.gridx = 2;
        gbc_maxProteinTextField.gridy = 8;
        frame.getContentPane().add(maxProteinTextField, gbc_maxProteinTextField);

        JLabel fatTextField = new JLabel("Fat");
        GridBagConstraints gbc_fatTextField = new GridBagConstraints();
        gbc_fatTextField.insets = new Insets(0, 0, 5, 5);
        gbc_fatTextField.gridx = 1;
        gbc_fatTextField.gridy = 9;
        frame.getContentPane().add(fatTextField, gbc_fatTextField);

        minFatTextField = new JTextField();
        minFatTextField.setName("Min fat");
        GridBagConstraints gbc_minFatTextField = new GridBagConstraints();
        minFatTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_minFatTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_minFatTextField.insets = new Insets(0, 0, 5, 5);
        gbc_minFatTextField.gridx = 0;
        gbc_minFatTextField.gridy = 10;
        frame.getContentPane().add(minFatTextField, gbc_minFatTextField);

        fatRangeSlider = new RangeSlider(0, 1000);
        fatRangeSlider.setName("fat slider");
        GridBagConstraints gbc_fatRangeSlider = new GridBagConstraints();
        gbc_fatRangeSlider.insets = new Insets(0, 0, 5, 5);
        gbc_fatRangeSlider.gridx = 1;
        gbc_fatRangeSlider.gridy = 10;
        frame.getContentPane().add(fatRangeSlider, gbc_fatRangeSlider);

        maxFatTextField = new JTextField();
        maxFatTextField.setName("Max fat");
        GridBagConstraints gbc_maxFatTextField = new GridBagConstraints();
        maxFatTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_maxFatTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxFatTextField.insets = new Insets(0, 0, 5, 0);
        gbc_maxFatTextField.gridx = 2;
        gbc_maxFatTextField.gridy = 10;
        frame.getContentPane().add(maxFatTextField, gbc_maxFatTextField);

        JLabel sodiumTextField = new JLabel("Sodium");
        GridBagConstraints gbc_sodiumTextField = new GridBagConstraints();
        gbc_sodiumTextField.insets = new Insets(0, 0, 5, 5);
        gbc_sodiumTextField.gridx = 1;
        gbc_sodiumTextField.gridy = 11;
        frame.getContentPane().add(sodiumTextField, gbc_sodiumTextField);

        minSodiumTextField = new JTextField();
        minSodiumTextField.setName("Min sodium");
        GridBagConstraints gbc_minSodiumTextField = new GridBagConstraints();
        minSodiumTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_minSodiumTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_minSodiumTextField.insets = new Insets(0, 0, 5, 5);
        gbc_minSodiumTextField.gridx = 0;
        gbc_minSodiumTextField.gridy = 12;
        frame.getContentPane().add(minSodiumTextField, gbc_minSodiumTextField);

        sodiumRangeSlider = new RangeSlider(0, 1000);
        sodiumRangeSlider.setName("sodium slider");
        GridBagConstraints gbc_sodiumRangeSlider = new GridBagConstraints();
        gbc_sodiumRangeSlider.insets = new Insets(0, 0, 5, 5);
        gbc_sodiumRangeSlider.gridx = 1;
        gbc_sodiumRangeSlider.gridy = 12;
        frame.getContentPane().add(sodiumRangeSlider, gbc_sodiumRangeSlider);

        maxSodiumTextField = new JTextField();
        maxSodiumTextField.setName("Max sodium");
        GridBagConstraints gbc_maxSodiumTextField = new GridBagConstraints();
        maxSodiumTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_maxSodiumTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxSodiumTextField.insets = new Insets(0, 0, 5, 0);
        gbc_maxSodiumTextField.gridx = 2;
        gbc_maxSodiumTextField.gridy = 12;
        frame.getContentPane().add(maxSodiumTextField, gbc_maxSodiumTextField);

        JLabel priceTextField = new JLabel("Price");
        GridBagConstraints gbc_priceTextField = new GridBagConstraints();
        gbc_priceTextField.insets = new Insets(0, 0, 5, 5);
        gbc_priceTextField.gridx = 1;
        gbc_priceTextField.gridy = 13;
        frame.getContentPane().add(priceTextField, gbc_priceTextField);

        minPriceTextField = new JTextField();
        minPriceTextField.setName("Min price");
        GridBagConstraints gbc_minPriceTextField = new GridBagConstraints();
        minPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_minPriceTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_minPriceTextField.insets = new Insets(0, 0, 5, 5);
        gbc_minPriceTextField.gridx = 0;
        gbc_minPriceTextField.gridy = 14;
        frame.getContentPane().add(minPriceTextField, gbc_minPriceTextField);

        priceRangeSlider = new RangeSlider(0, 1000);
        priceRangeSlider.setName("price slider");
        GridBagConstraints gbc_priceRangeSlider = new GridBagConstraints();
        gbc_priceRangeSlider.insets = new Insets(0, 0, 5, 5);
        gbc_priceRangeSlider.gridx = 1;
        gbc_priceRangeSlider.gridy = 14;
        frame.getContentPane().add(priceRangeSlider, gbc_priceRangeSlider);

        maxPriceTextField = new JTextField();
        maxPriceTextField.setName("Max price");
        GridBagConstraints gbc_maxPriceTextField = new GridBagConstraints();
        maxPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc_maxPriceTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxPriceTextField.insets = new Insets(0, 0, 5, 0);
        gbc_maxPriceTextField.gridx = 2;
        gbc_maxPriceTextField.gridy = 14;
        frame.getContentPane().add(maxPriceTextField, gbc_maxPriceTextField);

        searchExecuteButton = new JButton("Search");
        GridBagConstraints gbc_searchExecuteButton = new GridBagConstraints();
        gbc_searchExecuteButton.insets = new Insets(0, 0, 0, 5);
        gbc_searchExecuteButton.gridx = 1;
        gbc_searchExecuteButton.gridy = 17;
        frame.getContentPane().add(searchExecuteButton, gbc_searchExecuteButton);
        frame.repaint();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        rangeSliderList = new ArrayList<>();
        rangeSliderList.add(ratingRangeSlider);
        rangeSliderList.add(proteinRangeSlider);
        rangeSliderList.add(caloriesRangeSlider);
        rangeSliderList.add(fatRangeSlider);
        rangeSliderList.add(sodiumRangeSlider);
        rangeSliderList.add(priceRangeSlider);
        rangeSliderList.forEach(rangeSlider -> {
            rangeSlider.setValue(0);
            rangeSlider.setUpperValue(1000);
        });

        minCaloriesTextField.setText("0");
        minFatTextField.setText("0");
        minPriceTextField.setText("0");
        minProteinTextField.setText("0");
        minSodiumTextField.setText("0");

        maxCaloriesTextField.setText("1000");
        maxFatTextField.setText("1000");
        maxPriceTextField.setText("1000");
        maxProteinTextField.setText("1000");
        maxSodiumTextField.setText("1000");

        rangeTextFieldMap = new HashMap<>();

        rangeTextFieldMap.put(minCaloriesTextField, maxCaloriesTextField);
        rangeTextFieldMap.put(minProteinTextField, maxProteinTextField);
        rangeTextFieldMap.put(minFatTextField, maxFatTextField);
        rangeTextFieldMap.put(minSodiumTextField, maxSodiumTextField);
        rangeTextFieldMap.put(minPriceTextField, maxPriceTextField);
    }
}
