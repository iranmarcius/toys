package toys.tests.junit;


import java.util.*;
/**
 * Um bean genérico para teste
 */
public class TestBean {
	private String aString;
	private int anInteger;
	private float aFloat;
	private java.sql.Date aDate;
	private java.sql.Time aTime;
	private boolean aBoolean;


	public TestBean() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 7);
		c.set(Calendar.MONTH, 6);
		c.set(Calendar.YEAR, 1972);
		c.set(Calendar.HOUR_OF_DAY, 13);
		c.set(Calendar.MINUTE, 45);
		c.set(Calendar.SECOND, 22);

		aBoolean = true;
		aDate = new java.sql.Date(c.getTime().getTime()); // configura a data para 07/07/1972
		aTime = new java.sql.Time(c.getTime().getTime()); // configura a hora para 13:45:22
		aFloat = (float)10.3;
		anInteger = 10;
		aString = "Chuckwalla";
	}

	public java.sql.Date getADate() {
		return aDate;
	}

	public float getAFloat() {
		return aFloat;
	}

	public int getAnInteger() {
		return anInteger;
	}

	public String getAString() {
		return aString;
	}

	public java.sql.Time getATime() {
		return aTime;
	}

	public boolean isABoolean() {
		return aBoolean;
	}

	public void setABoolean(boolean newABoolean) {
		aBoolean = newABoolean;
	}

	public void setADate(java.sql.Date newADate) {
		aDate = newADate;
	}

	public void setAFloat(float newAFloat) {
		aFloat = newAFloat;
	}

	public void setAnInteger(int newAnInteger) {
		anInteger = newAnInteger;
	}

	public void setAString(java.lang.String newAString) {
		aString = newAString;
	}

	public void setATime(java.sql.Time newATime) {
		aTime = newATime;
	}
}
