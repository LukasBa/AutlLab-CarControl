package de.fh.ka.as.car.model;

public class SomeRequest {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SomeRequest(String text) {
		super();
		this.text = text;
	}

	public SomeRequest() {

	}

	@Override
	public String toString() {
		return text;
	}
}
