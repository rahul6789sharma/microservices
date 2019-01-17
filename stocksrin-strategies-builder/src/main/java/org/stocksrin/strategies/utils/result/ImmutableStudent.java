package org.stocksrin.strategies.utils.result;

public class ImmutableStudent {

	private final int id;
	private final String name;
	private final Age age;

	public ImmutableStudent(int id, String name, Age age) {
		this.name = name;
		this.id = id;
		Age cloneAge = new Age();
		cloneAge.setMonth(age.getMonth());
		cloneAge.setYear(age.getYear());
		this.age = cloneAge;
	}

	// getter and setter

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Age getAge() {
		Age cloneAge = new Age();
		cloneAge.setMonth(this.age.getMonth());
		cloneAge.setYear(this.age.getYear());
		return cloneAge;
	}

	
	public static void main(String[] args) {

		Age age = new Age();
		age.setMonth(1);
		age.setYear(1992);
		ImmutableStudent student = new ImmutableStudent(1, "Alex", age);
		System.out.println("Alex age year before modification = " + student.getAge().getYear());
		student.getAge().setYear(1993);
		System.out.println("Alex age year after modification = " + student.getAge().getYear());
	}
}

