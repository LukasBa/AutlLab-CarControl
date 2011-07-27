package de.fh.ka.as.car.gui;

/**
 * Event of a Button
 */
public class ButtonEvent extends ActionEvent {
	/**
	 * Action Type of the {@link ButtonEvent}
	 */
	public enum Type {
		/**
		 * Pressed Mouse
		 */
		Down, /**
		 * Released Mouse
		 */
		Up
	};

	private Type type;

	/**
	 * @param source
	 * @param type
	 */
	public ButtonEvent(Object source, Type type) {
		super(source, type.toString());
		this.type = type;
	}

	public ButtonEvent(Object source, String name) {
		super(source, name);
	}

	public Type getType() {
		return type;
	}
}
