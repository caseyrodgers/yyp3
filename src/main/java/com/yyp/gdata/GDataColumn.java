package com.yyp.gdata;

public class GDataColumn {
		String colName, colType;
		int colNumber;
		public GDataColumn(String name, String type, int colNumber) {
			this.colName = name;
			this.colType = type;
			this.colNumber = colNumber;
		}
		
		public GDataColumn(String name, int colNumber) {
			this.colName = name;
			this.colNumber = colNumber;
		}
		
		public String getColName() {
			return colName;
		}
		public void setColName(String colName) {
			this.colName = colName;
		}
		public String getColType() {
			return colType;
		}
		public void setColType(String colType) {
			this.colType = colType;
		}
		public int getColNumber() {
			return colNumber;
		}
		public void setColNumber(int colNumber) {
			this.colNumber = colNumber;
		}

		@Override
        public String toString() {
	        return "GDataColumn [colName=" + colName + ", colType=" + colType + ", colNumber=" + colNumber + "]";
        }
}
