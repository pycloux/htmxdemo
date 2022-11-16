package com.pycloux.webtest.htmxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pycloux.webtest.htmxdemo.bean.CalculatorState;
import com.pycloux.webtest.htmxdemo.service.CalculatorService;
import com.pycloux.webtest.htmxdemo.service.CalculatorService.CalculatorOperation;

@Controller
public class MainController{
	@Autowired
	private CalculatorService calculator;
	
	@Autowired
	private CalculatorState state;
	
	@Value("${app.version}")
	private String appVersion;
	
	@Value("${network.latency}")
	private int latency;
	
	@GetMapping("/version")
	public String version(Model model) {
		model.addAttribute("version", this.appVersion);
		return "version";
	}
	
	/**
	 * Display the calculator home page
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("currentValue", formatValueForDisplay(state.getCurrentValue()));
		model.addAttribute("stateId", state.getUniqueId());
		return "calculator";
	}
	
	/**
	 * A number was pressed on the calculator.<br/>
	 * Proceed with the appropriate changes to the state and send back
	 * what must be displayed.
	 * @return
	 */
	@GetMapping(path = "/set_number/{number}")
	@ResponseBody
	public String numberPressed(@PathVariable(name = "number") int value) {
		try {
			simulateNetworkLatency();
			calculator.numberInput(state, value);
			return formatValueForDisplay(state.getCurrentValue());
		}catch(Exception e) {
			e.printStackTrace();
			return "ERR";
		}
	}
	
	/**
	 * An operation key was pressed
	 * @return
	 */
	@GetMapping(path = "/set_operation/{operation}")
	@ResponseBody
	public String operationPressed(@PathVariable(name = "operation") String value) {
		CalculatorOperation operation = CalculatorService.CalculatorOperation.valueOf(value);
		return formatValueForDisplay(calculator.operationInput(state, operation));
	}
	
	/**
	 * Reset the calculator to its initial state
	 * @return
	 */
	@GetMapping(path = "/reset")
	@ResponseBody
	public String resetPressed() {
		calculator.reset(state);
		return formatValueForDisplay(state.getCurrentValue());
	}
	
	/**
	 * The equality key was pressed
	 * @return
	 */
	@GetMapping(path = "/equal")
	@ResponseBody
	public String equalPressed() {
		calculator.compute(state);
		return formatValueForDisplay(state.getCurrentValue());
	}
	
	/**
	 * The negation key was pressed
	 * @return
	 */
	@GetMapping(path = "/negate")
	@ResponseBody
	public String negatePressed() {
		calculator.negate(state);
		return formatValueForDisplay(state.getCurrentValue());
	}
	
	/**
	 * The decimal dot key was pressed
	 * @return
	 */
	@GetMapping(path = "/decimal")
	@ResponseBody
	public String decimalPressed() {
		calculator.decimal(state);
		return formatValueForDisplay(state.getCurrentValue());
	}
	
	/**
	 * Wait to simulate network latency
	 */
	private void simulateNetworkLatency() {
		try {
			Thread.sleep(this.latency);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Format the current value to be displayed
	 * @return a String
	 */
	private String formatValueForDisplay(float value) {
		if(value == (int) value) {
			return String.valueOf((int) value);
		}
		return String.valueOf(value);
	}
}
