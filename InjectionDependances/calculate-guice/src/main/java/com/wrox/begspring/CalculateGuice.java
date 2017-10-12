package com.wrox.begspring;

import com.google.inject.Inject;

public class CalculateGuice {
	private Operation ops;
	private ResultWriter wtr;

	@Inject
	public CalculateGuice(Operation ops, ResultWriter writer) {
		this.ops = ops;
		this.wtr = writer;
	}

	public void execute(String[] args) {
		long op1 = Long.parseLong(args[0]);
		long op2 = Long.parseLong(args[1]);
		wtr.showResult("The result of " + op1 + ops.getOpsName() + op2 + " is "
				+ ops.operate(op1, op2) + "!");
	}
}