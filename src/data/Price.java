package data;

public class Price {
	String backNews="";
	String unit="";
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	double unitSum=0;
	public String getBackNews() {
		return backNews;
	}
	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}
	public double getUnitSum() {
		return unitSum;
	}
	public void setUnitSum(double unitSum) {
		this.unitSum = unitSum;
	}
}
