package data;

import com.sun.rowset.CachedRowSetImpl;

public class Query {
	CachedRowSetImpl rowSet=null;//存储表中全部记录的行集对象
	int totalPages=1;		//分页后的总页数
	int currentPage=1;		//当前显示页
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public CachedRowSetImpl getRowSet() {
		return rowSet;
	}
	public void setRowSet(CachedRowSetImpl rowSet) {
		this.rowSet = rowSet;
	}
	
}
