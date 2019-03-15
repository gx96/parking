package data;

import com.sun.rowset.CachedRowSetImpl;

public class Admin {
	String number="",user="",name="",phone="",backNews="",password="";
	

	CachedRowSetImpl rowSet=null;//存储表中全部记录的行集对象
	int totalPages=1;		//分页后的总页数
	int currentPage=1;		//当前显示页
	
	public CachedRowSetImpl getRowSet() {
		return rowSet;
	}

	public void setRowSet(CachedRowSetImpl rowSet) {
		this.rowSet = rowSet;
	}

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBackNews() {
		return backNews;
	}

	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
