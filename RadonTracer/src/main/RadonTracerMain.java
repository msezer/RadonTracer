/*
  @author msezer
 * Sep 20, 2017
 */

package main;

import view.RadonTracerView;

class RadonTracerMain {
	
	
	public static void main(String[] args) {
		
		RadonTracerView view;
		MainCalculator calculator;
		
		// Create model, view, and controller
		calculator = MainCalculator.getInstance();
		view = new RadonTracerView(calculator);
		view.setVisible(true);
	}
}