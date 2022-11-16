package com.pycloux.webtest.htmxdemo.bean;

import java.util.UUID;

import com.pycloux.webtest.htmxdemo.service.CalculatorService.CalculatorOperation;

public class CalculatorState {
	public static enum LastKeyPressed {
		NUMBER, OPERATION, DECIMAL
	}
	
	private String uniqueId = UUID.randomUUID().toString();
	private LastKeyPressed lastKeyPressed;
	private float currentValue = 0;
	private float previousValue = 0;
	private CalculatorOperation operation;

	public String getUniqueId() {
		return uniqueId;
	}

	public float getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(float currentValue) {
		this.currentValue = currentValue;
	}

	public CalculatorOperation getOperation() {
		return operation;
	}

	public void setOperation(CalculatorOperation operation) {
		this.operation = operation;
	}
	
	public void reset() {
		setCurrentValue(0);
		setPreviousValue(0);
		setOperation(null);
	}
	
	public boolean isCurrentValueInt() {
		return getCurrentValue() == (int) getCurrentValue();
	}
	
	public boolean isOperationPending() {
		return getOperation()!=null;
	}

	public float getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(float previousValue) {
		this.previousValue = previousValue;
	}

	public LastKeyPressed getLastKeyPressed() {
		return lastKeyPressed;
	}

	public void setLastKeyPressed(LastKeyPressed lastKeyPressed) {
		this.lastKeyPressed = lastKeyPressed;
	}

	@Override
	public String toString() {
		return "CalculatorState [uniqueId=" + uniqueId + ", lastKeyPressed=" + lastKeyPressed + ", currentValue="
				+ currentValue + ", previousValue=" + previousValue + ", operation=" + operation + "]";
	}
}
