package com.pycloux.webtest.htmxdemo.service;

import org.springframework.stereotype.Component;

import com.pycloux.webtest.htmxdemo.bean.CalculatorState;
import com.pycloux.webtest.htmxdemo.bean.CalculatorState.LastKeyPressed;

@Component
public class CalculatorService {
	public static enum CalculatorOperation {
		ADDITION, SUBSTRACTION, MULTIPLICATION, DIVISION
	}
	
	/**
	 * The reset button has been pressed
	 * @param state the current calculator state
	 */
	public void reset(CalculatorState state) {
		state.reset();
	}
	
	/**
	 * The equality button has been pressed
	 * @param state the current calculator state
	 */
	public void negate(CalculatorState state) {
		state.setCurrentValue(-state.getCurrentValue());
	}
	
	/**
	 * The decimal dot button has been pressed
	 * @param state the current calculator state
	 */
	public void decimal(CalculatorState state) {
		state.setLastKeyPressed(LastKeyPressed.DECIMAL);
		System.out.print(state);
	}
	
	/**
	 * The reset button has been pressed
	 * @param state the current calculator state
	 */
	public void compute(CalculatorState state) {
		if(state.isOperationPending()) {
			state.setCurrentValue(performOperation(state));
			state.setPreviousValue(0);
			state.setOperation(null);
		}
	}
	
	/**
	 * A number is pressed
	 * @param state the current calculator state
	 * @param number the number entered
	 */
	public void numberInput(CalculatorState state, int number) {
		if(state.getCurrentValue()==0) {
			if(state.getLastKeyPressed()==LastKeyPressed.DECIMAL) {
				state.setCurrentValue(Float.parseFloat("0."+number));
			}else {
				state.setCurrentValue(number);
			}
		}else {
			//Append the number to the current value
			if(state.isCurrentValueInt()) {
				String newValueAsString = "";
				if(state.getLastKeyPressed()==LastKeyPressed.DECIMAL) {
					newValueAsString = String.valueOf((int) state.getCurrentValue()) + "." + String.valueOf(number);
					state.setCurrentValue(Float.parseFloat(newValueAsString));
				}else {
					newValueAsString = String.valueOf((int) state.getCurrentValue()) + String.valueOf(number);
					state.setCurrentValue(Integer.parseInt(newValueAsString));
				}
			}else {
				String newValueAsString = String.valueOf(state.getCurrentValue()) + String.valueOf(number);
				state.setCurrentValue(Float.parseFloat(newValueAsString));
			}
		}
		state.setLastKeyPressed(LastKeyPressed.NUMBER);
	}
	
	/**
	 * An operation has been pressed
	 * @param state the current calculator state
	 * @param operation the operation
	 */
	public float operationInput(CalculatorState state, CalculatorOperation operation) {
		if(state.getLastKeyPressed()==LastKeyPressed.OPERATION) {
			state.setOperation(operation);
			state.setLastKeyPressed(LastKeyPressed.OPERATION);
			return state.getPreviousValue();
		}
		if(state.getLastKeyPressed()==LastKeyPressed.NUMBER) {
			float valueToReturn = 0;
			if(state.isOperationPending()) {
				state.setPreviousValue(performOperation(state));
				valueToReturn = state.getPreviousValue();
			}else {
				state.setPreviousValue(state.getCurrentValue());
				valueToReturn = state.getCurrentValue();
			}
			state.setOperation(operation);
			state.setCurrentValue(0);
			state.setLastKeyPressed(LastKeyPressed.OPERATION);
			return valueToReturn;
		}
		return 0;
	}
	
	private float performOperation(CalculatorState state) {
		switch(state.getOperation()) {
			case ADDITION:
				return state.getPreviousValue() + state.getCurrentValue();
			case DIVISION:
				return state.getPreviousValue() / state.getCurrentValue();
			case MULTIPLICATION:
				return state.getPreviousValue() * state.getCurrentValue();
			case SUBSTRACTION:
				return state.getPreviousValue() - state.getCurrentValue();
		}
		throw new RuntimeException("Impossible");
	}
}
