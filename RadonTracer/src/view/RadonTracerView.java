/*
  @author msezer
 * Sep 20, 2017
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.MainCalculator;

public class RadonTracerView{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 8611749777314626929L;

	private final JFrame radonFrame;

	private JTextField radonAnswerField;

	private MainCalculator calculator;

	public RadonTracerView(MainCalculator calculator_i){
		super();
		this.calculator = calculator_i;
		radonFrame = new JFrame("Radon Anomaly Observer (Kulali & Sezer)");
		initializeFrame(radonFrame);
		initializeComponents(radonFrame);

	}

	/*
	 * @param JFrame
	 * */
	private void initializeComponents(JFrame inradonFrame) {

		// initialize components
		JLabel[] radonLabels = new JLabel[6];
		JLabel[] radonLabelInfos = new JLabel[6];
		JTextField[] radonFields = new JTextField[6];
		JButton btnCalculate = new JButton("Calculate Possibility");
		btnCalculate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				radonAnswerField.setText("...");
				//System.out.print("Button Pressed: ");
				//System.out.println(radonFields[5].getText() + "\n\n");
				String in_result = calculator.AnomalyCalculate(
						Integer.parseInt(radonFields[0].getText()), 
						Integer.parseInt(radonFields[1].getText()), 
						Integer.parseInt(radonFields[2].getText()), 
						Integer.parseInt(radonFields[3].getText()), 
						Integer.parseInt(radonFields[4].getText()), 
						Integer.parseInt(radonFields[5].getText())
						);
				radonAnswerField.setText(in_result);
			}
		});

		btnCalculate.setPreferredSize(new Dimension(200, 40));
		radonAnswerField = new JTextField();
		radonAnswerField.setEnabled(false);
		radonAnswerField.setPreferredSize(new Dimension(200, 60));

		for (int i = 0; i < radonLabels.length; i++) {
			radonFields[i] = new JTextField(String.valueOf(0));
			radonLabels[i] = new JLabel(String.valueOf(0));
			radonLabelInfos[i] = new JLabel(String.valueOf(0));
		}

		radonFields[0].setToolTipText("Anomaly Detection Period (+- hours)");
		radonFields[0].setText("12");
		radonFields[1].setToolTipText("Anomaly Initial Value (%)");
		radonFields[1].setText("12");
		radonFields[2].setToolTipText("Anomaly Final Value (%)");	
		radonFields[2].setText("30");
		radonFields[3].setToolTipText("Me Initial Value");
		radonFields[3].setText("5");
		radonFields[4].setToolTipText("Me Final Value");
		radonFields[4].setText("10");
		radonFields[5].setToolTipText("Detection Period (+ hours)");
		radonFields[5].setText("24");

		radonLabels[0].setText("ADP");	radonLabelInfos[0].setText("Anomaly Detection Period (+- hours)");
		radonLabels[1].setText("AIV");	radonLabelInfos[1].setText("Anomaly Initial Value (%)");
		radonLabels[2].setText("AFV");	radonLabelInfos[2].setText("Anomaly Final Value (%)");
		radonLabels[3].setText("EIV");	radonLabelInfos[3].setText("Me Initial Value");
		radonLabels[4].setText("EFV");	radonLabelInfos[4].setText("Me Final Value");
		radonLabels[5].setText("DPR");	radonLabelInfos[5].setText("Detection Period (+ hours)");

		// Set the main panel
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{215};
		gbl_panel.rowHeights = new int[]{40, 40, 40, 20, 20, 0};
		gbl_panel.columnWeights = new double[]{1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		radonFrame.getContentPane().setLayout(gbl_panel);

		// Anomaly starts here
		// instantiation of panel
		// set border for panel
		JPanel panelanomaly = new JPanel();
		panelanomaly.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Anomaly", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		// set constraints for Frame
		GridBagConstraints gbc_panel_anomaly = new GridBagConstraints();
		gbc_panel_anomaly.insets = new Insets(0, 0, 5, 0);
		gbc_panel_anomaly.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_anomaly.gridx = 0;
		gbc_panel_anomaly.gridy = 0;

		// set inner layout
		GridBagLayout gbl_panelanomaly = new GridBagLayout();
		gbl_panelanomaly.columnWidths = new int[] {20, 60, 60, 0};
		gbl_panelanomaly.columnWeights = new double[]{0, 0, 1, Double.MIN_VALUE};
		gbl_panelanomaly.rowHeights = new int[] {20, 20, 20, 0};
		gbl_panelanomaly.rowWeights = new double[]{Double.MIN_VALUE};
		panelanomaly.setLayout(gbl_panelanomaly);

		// set inner constraints
		GridBagConstraints gbc_defaultElements = new GridBagConstraints();
		gbc_defaultElements.fill = GridBagConstraints.HORIZONTAL;

		// anomaly set & put inner elements
		for (int i = 0; i < 3; i++) {
			gbc_defaultElements.gridy = i;
			gbc_defaultElements.gridx = 0;
			panelanomaly.add(radonLabels[i], gbc_defaultElements);
			gbc_defaultElements.gridx = 1;
			panelanomaly.add(radonFields[i], gbc_defaultElements);
			gbc_defaultElements.gridx = 2;
			panelanomaly.add(radonLabelInfos[i], gbc_defaultElements);
		}

		// earthquake starts here
		// instantiation of panel
		// set border for panel
		JPanel panelearthquake = new JPanel();
		panelearthquake.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Earthquake", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		// set constraints for Frame
		GridBagConstraints gbc_panel_earthquake = new GridBagConstraints();
		gbc_panel_earthquake.insets = new Insets(0, 0, 5, 0);
		gbc_panel_earthquake.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_earthquake.gridx = 0;
		gbc_panel_earthquake.gridy = 1;

		// set inner layout
		GridBagLayout gbl_panel_earth = new GridBagLayout();
		gbl_panel_earth.columnWidths = new int[] {20, 60, 60, 0};
		gbl_panel_earth.columnWeights = new double[]{0, 0, 1, Double.MIN_VALUE};
		gbl_panel_earth.rowHeights = new int[] {20, 20, 0};
		gbl_panel_earth.rowWeights = new double[]{Double.MIN_VALUE};
		panelearthquake.setLayout(gbl_panel_earth);

		// set inner constraints
		gbc_defaultElements.fill = GridBagConstraints.HORIZONTAL;

		// set & put inner elements
		for (int i = 0; i < 2; i++) {
			gbc_defaultElements.gridy = i;
			gbc_defaultElements.gridx = 0;
			panelearthquake.add(radonLabels[i + 3], gbc_defaultElements);
			gbc_defaultElements.gridx = 1;
			panelearthquake.add(radonFields[i + 3], gbc_defaultElements);
			gbc_defaultElements.gridx = 2;
			panelearthquake.add(radonLabelInfos[i + 3], gbc_defaultElements);
		}

		// detection starts here
		// instantiation of panel
		// set border for panel
		JPanel paneldetection = new JPanel();
		paneldetection.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Detection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		// set constraints for Frame
		GridBagConstraints gbc_panel_detect = new GridBagConstraints();
		gbc_panel_detect.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_detect.gridx = 0;
		gbc_panel_detect.gridy = 2;

		// set inner layout
		GridBagLayout gbl_panel_detect = new GridBagLayout();
		gbl_panel_detect.columnWidths = new int[] {20, 60, 60, 0};
		gbl_panel_detect.columnWeights = new double[]{0, 0, 1, Double.MIN_VALUE};
		gbl_panel_detect.rowHeights = new int[] {20, 0};
		gbl_panel_detect.rowWeights = new double[]{Double.MIN_VALUE};
		paneldetection.setLayout(gbl_panel_detect);

		// set inner constraints
		gbc_defaultElements.fill = GridBagConstraints.HORIZONTAL;

		// set & put inner elements
		gbc_defaultElements.gridy = 0;
		gbc_defaultElements.gridx = 0;
		paneldetection.add(radonLabels[5], gbc_defaultElements);
		gbc_defaultElements.gridx = 1;
		paneldetection.add(radonFields[5], gbc_defaultElements);
		gbc_defaultElements.gridx = 2;
		paneldetection.add(radonLabelInfos[5], gbc_defaultElements);

		// calculate part starts here

		GridBagConstraints gbc_Calculate = new GridBagConstraints();
		gbc_Calculate.gridx = 0;
		gbc_Calculate.gridy = 3;

		// add panels to the frame
		radonFrame.getContentPane().add(panelanomaly, gbc_panel_anomaly);
		radonFrame.getContentPane().add(panelearthquake, gbc_panel_earthquake);
		radonFrame.getContentPane().add(paneldetection, gbc_panel_detect);
		radonFrame.getContentPane().add(btnCalculate, gbc_Calculate);

		GridBagConstraints gbc_Result = new GridBagConstraints();
		gbc_Result.gridx = 0;
		gbc_Result.gridy = 4;
		radonFrame.getContentPane().add(radonAnswerField, gbc_Result);
	}

	/*
	 * @param JFrame inradonFrame
	 * */
	private void initializeFrame(JFrame inradonFrame) {
		inradonFrame.setAlwaysOnTop(true);
		inradonFrame.setMinimumSize(new Dimension(400, 380));
		inradonFrame.setLocationRelativeTo(null);
		inradonFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		inradonFrame.setVisible(false);
	}
	public void setVisible(boolean b) {
		this.radonFrame.setVisible(b);
	}
}