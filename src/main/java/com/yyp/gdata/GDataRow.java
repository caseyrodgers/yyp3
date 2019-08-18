package com.yyp.gdata;

public class GDataRow {

	GDataColumn col;
	int row;
	String value;

	public GDataRow(GDataColumn col, int row, String value) {
		this.col = col;
		this.row = row;
		this.value = value;
	}

	public GDataColumn getCol() {
		return col;
	}

	public void setCol(GDataColumn col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
