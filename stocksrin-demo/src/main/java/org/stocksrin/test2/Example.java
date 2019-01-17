package org.stocksrin.test2;

public class Example implements MyInterface, MyInterface2 {

	public static void main(String[] args) {
		Example example = new Example();
		example.newMethod();
	}

	@Override
	public void newMethod() {
		// call MyInterface method
		MyInterface.super.newMethod();

		// call MyInterface method
		MyInterface2.super.newMethod();
	}

}


