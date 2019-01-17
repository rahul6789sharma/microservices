package org.stocksrin.test2;

public interface MyInterface {

	default void newMethod() {
		System.out.println("MyInterface");
	}
}
