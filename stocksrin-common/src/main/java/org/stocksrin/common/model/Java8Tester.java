package org.stocksrin.common.model;

import java.util.Optional;

public class Java8Tester {

	public static void main(String args[]) {
		Java8Tester obj = new Java8Tester();
		obj.test();
	}

	public void test() {
		String text = "Text present";

		System.out.println("Using orElseGet:");
		String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
		System.out.println(defaultText);
		System.out.println("---------------");

		System.out.println("Using orElse:");
		defaultText = Optional.ofNullable(text).orElse(getMyDefault());
		System.out.println(defaultText);
	}

	public String getMyDefault() {
		System.out.println("Getting Default Value");
		return "Default Value";
	}
}
