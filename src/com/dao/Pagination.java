package com.dao;

import java.util.List;

/**
 * 查询结果集的片段数，及分页查询后的结果存放在这个类中
 * 
 * @author tangwei
 * 
 */
public class Pagination {

	private int pageSize = 10;     // 分页的大小,初始化10条记录
	private int pageNumber = 1;    // 当前的页码，默认为第一页
	private int totalCount;        // 分页的总数,就是总共有多少条记录
	private int offset;            // 分页的偏移量
	
	@SuppressWarnings("rawtypes")
	private List list; // 经过计算后的分页结果集

	public Pagination() {
	}
	public Pagination(int totalCount) {
		this.totalCount = totalCount;
	}
	public Pagination(int pageSize, int pageNumber, int totalCount) {
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.totalCount = totalCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		int totalPage = (int) Math.ceil(totalCount / pageSize); //计算出总共多少页
		if (pageNumber > totalPage) {
			this.pageNumber = totalPage;
		} else if (pageNumber <= 0) {
			this.pageNumber = 1;
		} else {
			this.pageNumber = pageNumber;
		}
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		if (offset > totalCount) {
			this.offset = totalCount - 1;
		} else if (offset < 0) {
			this.offset = 0;
		} else {
			this.offset = offset;
		}
	}
	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}
	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}
}
