package com.huan.springboottest.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

public class PageUtil<T> implements Serializable {
	private static final long serialVersionUID = -1698628566020108251L;
	// 指定的或是页面参数
	private int currentPage; // 当前页
	private int numPerPage = 1; // 每页显示多少条

	// 查询数据库
	private int totalCount; // 总记录数
	private List<T> recordList; // 本页的数据列表

	// 计算
	private int pageCount; // 总页数
	private int beginPageIndex; // 页码列表的开始索引（包含）
	private int endPageIndex; // 页码列表的结束索引（包含）

	private Map<String, Object> countResultMap; // 当前分页条件下的统计结果
	
	public PageUtil() {
	}

	/**
	 * 使用com.github.pagehelper分页插件实现分页
	 * 
	 * @param recordList
	 */
	public PageUtil(List<T> recordList) {
		if (recordList == null) {
			this.recordList = new ArrayList<>();
		} else if (recordList instanceof Page) {
			Page<T> page = (Page<T>) recordList;
			this.totalCount = (int) page.getTotal();
			this.currentPage = page.getPageNum();
			this.numPerPage = page.getPageSize();
			this.recordList = recordList;
		} else {
			throw new RuntimeException("分页异常");
		}
	}

	/**
	 * 使用com.github.pagehelper分页插件实现分页
	 * 
	 * @param pageList
	 *            含有分页信息的集合
	 * @param recordList
	 *            分页的记录
	 */
	public PageUtil(List<?> pageList, List<T> recordList) {
		if (pageList instanceof Page) {
			Page page = (Page) pageList;
			this.totalCount = (int) page.getTotal();
			this.currentPage = page.getPageNum();
			this.numPerPage = page.getPageSize();
			this.recordList = recordList;
		} else {
			throw new RuntimeException("分页异常");
		}
	}

	/**
	 * 对传入的list进行分页
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param recordList
	 */
	public PageUtil(int currentPage, int numPerPage, List<T> recordList) {
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalCount = recordList == null ? 0 : recordList.size();
		// 分页
		List<T> subList = new ArrayList<>();
		if (currentPage < getPageCount()) {
			int startIndex = currentPage * numPerPage;
			int endIndex = startIndex + numPerPage;
			for (int i = startIndex; i < endIndex && i < totalCount; i++) {
				subList.add(recordList.get(i));
			}
		}
		this.recordList = subList;
	}

	/**
	 * 只接受前4个必要的属性，会自动的计算出其他3个属生的值>>自己手动分页需要调用的参数
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param totalCount
	 * @param recordList
	 */
	public PageUtil(int currentPage, int numPerPage, int totalCount, List<T> recordList) {
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.recordList = recordList;
	}

	/**
	 * 只接受前5个必要的属性，会自动的计算出其他3个属生的值
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param totalCount
	 * @param recordList
	 */
	public PageUtil(int currentPage, int numPerPage, int totalCount, List<T> recordList,
			Map<String, Object> countResultMap) {
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.recordList = recordList;
		this.countResultMap = countResultMap;

	}

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		// 计算总页码
		return (getTotalCount() + getNumPerPage() - 1) / getNumPerPage();
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBeginPageIndex() {
		Integer index = getCountResultMap() == null ? 0 : 1;
		// >> 总页数不多于10页，则全部显示
		if (getPageCount() <= 10) {
			beginPageIndex = index;
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			// 当前面的页码不足4个时，则显示前10个页码
			if (getCurrentPage() - 4 < 1) {
				beginPageIndex = index;
			} else {
				// 当后面的页码不足5个时，则显示后10个页码
				if (getCurrentPage() + 5 > getPageCount()) {
					beginPageIndex = getPageCount() - 10 + 1;
				} else {
					beginPageIndex = getCurrentPage() - 4;
				}
			}
		}
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		Integer index = getCountResultMap() == null ? 0 : 1;
		// >> 总页数不多于10页，则全部显示
		if (getPageCount() <= 10) {
			endPageIndex = getPageCount() == 0 ? 0 : getPageCount() + index - 1;
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			// 当前面的页码不足4个时，则显示前10个页码
			if (getCurrentPage() - 4 < 1) {
				endPageIndex = 9;
			} else {
				// 当后面的页码不足5个时，则显示后10个页码
				if (getCurrentPage() + 5 > getPageCount()) {
					endPageIndex = getPageCount() - 1;
				} else {
					endPageIndex = getCurrentPage() + 4;
				}
			}

		}
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public Map<String, Object> getCountResultMap() {
		return countResultMap;
	}

	public void setCountResultMap(Map<String, Object> countResultMap) {
		this.countResultMap = countResultMap;
	}

}
