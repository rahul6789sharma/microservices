package org.stocksrin;

public class Test {

	public static void main(String[] args) throws Exception {
		try {
			System.out.println("system");
			Thread.currentThread().suspend();
		} catch (Exception e) {
			System.out.println("Exception");
			throw new Exception("hello");
		}finally {
			System.out.println("Fianlly");
		}
	}
}
