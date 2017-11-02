package com.zhtx.goodscore.util;

import com.github.pagehelper.Page;

import java.util.List;

public class PageHelper {

    // 总共的数据量
    private int total;

    // 每页显示多少条
    private int pageSize;

    // 共有多少页
    private int totalPage;

    // 当前是第几页
    private int index;

    // 数据
    private List<?> data;

    // 连接路径
    private String path = "";

    /**
     * 页码HTML信息
     */
    @SuppressWarnings("unused")
    private String pageHTML;

    private int startPage; // 开始页面

    private int endPage; // 结束页面

    private int displayNum = 10; // 显示的页数

    /**
     * @return the startPage
     */
    public int getStartPage() {
        return startPage;
    }

    /**
     * @return the endPage
     */
    public int getEndPage() {
        return endPage;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 设置路径前缀，页面第一页index为1
     *
     * @param path 此path含有参数形式，如/aa/article?page=,或者/bb/article/list/
     */
    public void setPath(String path) {
        this.path = path;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return (this.total + this.pageSize - 1) / this.pageSize;
    }

    public int getIndex() {
        return index;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    public String getPageHTML() {
        totalPage = getTotalPage();
        StringBuffer displayInfo = new StringBuffer();
        if (totalPage != 0 && pageSize != 0) {
            displayInfo.append("<div class='page-nav'>");
            // 判断如果当前是第一页 则“首页”和“第一页”失去链接
            if (index <= 1) {
                displayInfo.append("<a class='disabled'>首页</a>");
                displayInfo.append("<a class='disabled'>上一页</a>");
            } else {
                displayInfo.append("<a href='" + path + "1'>首页</a>");
                displayInfo.append("<a href='" + path + (index - 1) + "'>上一页</a>");
            }
            countPages();
            for (int i = startPage; i <= endPage; i++) {
                if (i == index) {
                    displayInfo.append("<span  style='cursor:pointer' class='current'>" + i + "</span>");
                } else {
                    displayInfo.append("<a href='" + path + i + "'>" + i + "</a>");
                }
            }
            if (index >= totalPage) {
                displayInfo.append("<a class='disabled'>下一页</a>");
                displayInfo.append("<a class='disabled'>尾页</a>");
            } else {
                displayInfo.append("<a href='" + path + (index + 1) + "'>下一页</a>");
                displayInfo.append("<a href='" + path + totalPage + "'>尾页</a>");
            }
            displayInfo.append(" 共 " + totalPage + " 页");
            displayInfo.append("</div>");
        }
        return displayInfo.toString();
    }

    public String getManagerPage() {
        totalPage = getTotalPage();
        StringBuffer displayInfo = new StringBuffer();
        if (totalPage != 0 && pageSize != 0) {
            displayInfo.append("<div class=\"row\"><div class=\"col-sm-6\"><div class=\"dataTables_info\"  role=\"alert\" aria-live=\"polite\" aria-relevant=\"all\">当前 " + index + "页 共 " + totalPage + " 页，共 " + total + " 条</div></div>" +
                    "<div class=\"col-sm-6\"><div class=\"dataTables_paginate paging_simple_numbers\" id=\"DataTables_Table_0_paginate\">");
            displayInfo.append("<ul class=\"pagination\">");
            // 判断如果当前是第一页 则“首页”和“第一页”失去链接
            if (index <= 1) {
                displayInfo.append(" <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\"  <a href=\"javascript:void(0);\">首页</a></li>\n");
                displayInfo.append("  <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" <a href=\"#\">上一页</a></li>");
            } else {
                displayInfo.append(" <li class=\"paginate_button previous\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">\n" + " <a href=\"" + path + "1\">首页</a></li>\n");
                displayInfo.append("  <li class=\"paginate_button previous\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">\n" + "<a href='" + path + (index - 1) + "'>上一页</a></li>");
                //displayInfo.append("<a href='" + path + "1'>首页</a>");
                //displayInfo.append("<a href='" + path + (index - 1) + "'>上一页</a>");
            }
            countPages();
            for (int i = startPage; i <= endPage; i++) {
                if (i == index) {
                    displayInfo.append("  <li class=\"paginate_button active\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\">\n" + " <a href=\"#\">" + i + "</a></li>");
                    // displayInfo.append("<span  style='cursor:pointer' class='current'>" + i + "</span>");
                } else {
                    displayInfo.append("  <li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\">\n" + " <a href='" + path + i + "'>" + i + "</a></li>");
                    // displayInfo.append("<a href='" + path + i + "'>" + i + "</a>");
                }
            }
            if (index >= totalPage) {
                displayInfo.append("  <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">\n" + "<a href=\"#\">下一页</a></li>");
                displayInfo.append("  <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">\n" + "<a href=\"#\">末页</a></li>");

                //displayInfo.append("<a class='disabled'>下一页</a>");
                //displayInfo.append("<a class='disabled'>尾页</a>");
            } else {

                displayInfo.append("  <li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\">\n" + " <a href='" + path + (index + 1) + "'>下一页 </a></li>");
                displayInfo.append("  <li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\">\n" + " <a href='" + path + totalPage + "'>末页 </a></li>");


                //displayInfo.append("<a href='" + path + (index + 1) + "'>下一页</a>");
                //displayInfo.append("<a href='" + path + totalPage + "'>尾页</a>");
            }
            //displayInfo.append(" 共 " + totalPage + " 页");
            //displayInfo.append("</div>");
            displayInfo.append("</ul></div></div></div>");
        }
        return displayInfo.toString();
    }


    String getPageNum(int pageindex) {
        return path.replace("##", String.valueOf(pageindex));
    }

    public String getAjaxManagerPage() {

        totalPage = getTotalPage();
        StringBuffer displayInfo = new StringBuffer();
        if (totalPage != 0 && pageSize != 0) {
            displayInfo.append("<div class=\"row\"><div class=\"col-sm-6\"><div class=\"dataTables_info\"  role=\"alert\" aria-live=\"polite\" aria-relevant=\"all\">当前 " + index + "页 共 " + totalPage + " 页，共 " + total + " 条</div></div>" +
                    "<div class=\"col-sm-6\"><div class=\"dataTables_paginate paging_simple_numbers\"  >");
            displayInfo.append("<ul class=\"pagination\">");
            // 判断如果当前是第一页 则“首页”和“第一页”失去链接
            if (index <= 1) {
                displayInfo.append(" <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\"  > <a href=\"javascript:void(0);\">首页</a></li>\n");
                displayInfo.append("  <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\"  ><a href=\"#\">上一页</a></li>");
            } else {
                displayInfo.append(" <li class=\"paginate_button previous\" aria-controls=\"DataTables_Table_0\"    >\n" + " <a href=\"" + getPageNum(1) + "\">首页</a></li>\n");
                displayInfo.append("  <li class=\"paginate_button previous\" aria-controls=\"DataTables_Table_0\"  >\n" + "<a href='" + getPageNum( index - 1) + "'>上一页</a></li>");
                //displayInfo.append("<a href='" + path + "1'>首页</a>");
                //displayInfo.append("<a href='" + path + (index - 1) + "'>上一页</a>");
            }
            countPages();
            for (int i = startPage; i <= endPage; i++) {
                if (i == index) {
                    displayInfo.append("  <li class=\"paginate_button active\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\">\n" + " <a href=\"javascript:void(0);\">" + i + "</a></li>");
                    // displayInfo.append("<span  style='cursor:pointer' class='current'>" + i + "</span>");
                } else {
                    displayInfo.append("  <li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\">\n" + " <a href='" + getPageNum( i) + "'>" + i + "</a></li>");
                    // displayInfo.append("<a href='" + path + i + "'>" + i + "</a>");
                }
            }
            if (index >= totalPage) {
                displayInfo.append("  <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\">\n" + "<a href=\"javascript:void(0);\">下一页</a></li>");
                displayInfo.append("  <li class=\"paginate_button previous disabled\" aria-controls=\"DataTables_Table_0\">\n" + "<a href=\"javascript:void(0);\">末页</a></li>");

                //displayInfo.append("<a class='disabled'>下一页</a>");
                //displayInfo.append("<a class='disabled'>尾页</a>");
            } else {

                displayInfo.append("  <li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" >\n" + " <a href='" + getPageNum( index + 1) + "'>下一页 </a></li>");
                displayInfo.append("  <li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" >\n" + " <a href='" + getPageNum( totalPage) + "'>末页 </a></li>");


                //displayInfo.append("<a href='" + path + (index + 1) + "'>下一页</a>");
                //displayInfo.append("<a href='" + path + totalPage + "'>尾页</a>");
            }
            //displayInfo.append(" 共 " + totalPage + " 页");
            //displayInfo.append("</div>");
            displayInfo.append("</ul></div></div></div>");
        }
        return displayInfo.toString();
    }

    public static <E> String GetHtml(Page<E> page, String path) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setTotal((int) page.getTotal());
        pageHelper.setIndex(page.getPageNum());
        pageHelper.setPageSize(page.getPageSize());
        pageHelper.setPath(path + "?pageIndex=");
        return pageHelper.getManagerPage();
    }

    public static <E> String GetAjaxHtml(Page<E> page, String path) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setTotal((int) page.getTotal());
        pageHelper.setIndex(page.getPageNum());
        pageHelper.setPageSize(page.getPageSize());
        pageHelper.setPath(path);
        return pageHelper.getAjaxManagerPage();
    }

    /**
     * 计算起始页和结束页
     */
    public void countPages() {

        if (index - displayNum / 2 < 1) {
            startPage = 1;
            endPage = displayNum > totalPage ? totalPage : displayNum;
        } else if (index + displayNum / 2 > totalPage) {
            int n = totalPage - displayNum + 1;
            startPage = n > 0 ? n : 1;
            endPage = totalPage;
        } else {
            startPage = index - displayNum / 2;
            endPage = startPage + displayNum - 1;
        }
    }

    /**
     * @param pageHTML the pageHTML to set
     */
    public void setPageHTML(String pageHTML) {
        this.pageHTML = pageHTML;
    }

//	public static String getPage(int pageSize, int currentPage,int totalRecord, int totalPage,String serfix) {
//		String result=getPage(pageSize,  currentPage, totalRecord,  totalPage);
//		result=result.replace("jss.search", "jss"+serfix+".search");
//		result=result.replace("pagesearchcurrentpage", "pagesearchcurrentpage"+serfix);
//		result=result.replace("pagesearchmax", "pagesearchmax"+serfix);
//		result=result.replace("pagesearchvalue", "pagesearchvalue"+serfix);
//		result=result.replace("pagesearch\"", "pagesearch"+serfix+"\"");
//		return result;
//	}
//	/**
//	 * 生成分页相关的html
//	 * @author hailongzhao
//	 * @date 20150827
//	 * @param pageSize
//	 * @param currentPage
//	 * @param totalRecord
//	 * @param totalPage
//	 * @return
//	 */
//	public static String getPage(int pageSize, int currentPage,
//			int totalRecord, int totalPage) {
//		if (totalPage <= 0)
//			return "";
//		//共 39 页 572 条记录，当前为第 1 页,本页 15 条
//		StringBuilder sb = new StringBuilder();
//		sb.append("<div class=\"row\">");
//		sb.append("<div class=\"col-sm-6\">");
//		sb.append("<div class=\"dataTables_info\" id=\"DataTables_Table_0_info\" role=\"alert\"");
//		sb.append("aria-live=\"polite\" aria-relevant=\"all\">共 " + totalPage
//				+ "页," + totalRecord + "条记录,当前为第"+currentPage+"页,每页"+pageSize+"条</div>");
//		sb.append("</div>");
//
//		sb.append("<div class=\"col-sm-6\">");
//		sb.append("<div class=\"dataTables_paginate paging_simple_numbers\" id=\"DataTables_Table_0_paginate\">");
//		sb.append("<ul class=\"pagination\">");
//
//		// 首页
//		sb.append(getFirstPage(currentPage, totalPage));
//		sb.append(getBackPage(currentPage, totalPage));
//		sb.append(getPageInfo(currentPage, totalPage));
//		sb.append(getNextPage(currentPage, totalPage));
//
//		// 尾页
//		sb.append(getEndPage(currentPage, totalPage));
//
//		//跳转到指定页
//		sb.append("<input type=\"hidden\" id=\"pagesearchcurrentpage\" value=\""+currentPage+"\">");
//		sb.append("<input type=\"hidden\" id=\"pagesearchmax\" value=\""+totalPage+"\">");
//		sb.append("<input type=\"text\" id=\"pagesearchvalue\"  value=\""+currentPage+"\" style=\"width:30px;height:28px;\">");
//		sb.append("<input type=\"button\" id=\"pagesearch\" value=\"跳转\" data-submitbutton=\"true\">");
//		sb.append("</ul></div></div></div>");
//
//		return sb.toString();
//	}
//
///**
// * 生成首页的html
// * @author hailongzhao
// * @date 20050827
// * @param currentPage
// * @param totalPage
// * @return
// */
//	private static String getFirstPage(int currentPage, int totalPage) {
//		String Disabled = "";
//		String Href = "javascript:jss.search(1)";
//		if (currentPage <= 1 || totalPage == 1) {
//			Disabled = " previous disabled";
//			Href = "#";
//		}
//		StringBuilder sb = new StringBuilder();
//		sb.append("<li class=\"paginate_button "
//				+ Disabled
//				+ "\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">");
//		sb.append("<a href=\"" + Href + "\">首页</a></li>");
//		return sb.toString();
//	}
//
///**
// * 生成上一页的html
// * @author hailongzhao
// * @date 20150827
// * @param currentPage
// * @param totalPage
// * @return
// */
//	private static String getBackPage(int currentPage, int totalPage) {
//		String Disabled = "";
//		String Href = "javascript:jss.search(" + (currentPage - 1) + ")";
//		if (currentPage <= 1 || totalPage <= 1) {
//			Disabled = " previous disabled";
//			Href = "#";
//		}
//		StringBuilder sb = new StringBuilder();
//		sb.append("<li class=\"paginate_button "
//				+ Disabled
//				+ "\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">");
//		sb.append("<a href=\"" + Href + "\">上一页</a></li>");
//		return sb.toString();
//	}
//
//	/**
//	 * 生成页码的html
//	 * @author hailongzhao
//	 * @date 20150827
//	 * @param currentPage
//	 * @param totalPage
//	 * @return
//	 */
//	private static String getPageInfo(int currentPage, int totalPage) {
//		StringBuilder sb = new StringBuilder();
//		String headMoreString="";
//		String endMoreString="";
//		int showPage=5;//一次显示10个分页的按钮
//		int headPage=1;
//		int endPage=totalPage;
//		if (totalPage>showPage) {
//			if (currentPage>showPage) {
//				headPage=currentPage-showPage/2;
//				if (currentPage==totalPage) {
//					headPage=totalPage-showPage+1;
//				}
//				endPage=headPage+showPage-1;
//				if (endPage>totalPage) {
//					endPage=totalPage;
//				}
//				headMoreString="<li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\"><a href=\"javascript:jss.search("
//						+ (headPage-1) + ")\">...</a></li>";
//
//				if (endPage<totalPage) {
//					endMoreString="<li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\"><a href=\"javascript:jss.search("
//							+ (endPage+1) + ")\">...</a></li>";
//				}
//			}else {
//				endPage=showPage;
//				endMoreString="<li class=\"paginate_button\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\"><a href=\"javascript:jss.search("
//						+ (endPage+1) + ")\">...</a></li>";
//			}
//		}
//
//		for (int i = headPage; i <= endPage; i++) {
//			int myPage = i;
//			String Href = "javascript:jss.search(" + (myPage) + ")";
//			String activeStr = myPage == currentPage ? " active" : "";
//			sb.append("<li class=\"paginate_button"
//					+ activeStr
//					+ "\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\"><a href=\""
//					+ Href + "\">" + myPage + "</a></li>");
//		}
//		return headMoreString+sb.toString()+endMoreString;
//	}
//
///**
// * 生成下一页的html
// * @author hailongzhao
// * @date 20150827
// * @param currentPage
// * @param totalPage
// * @return
// */
//	private static String getNextPage(int currentPage, int totalPage) {
//		String Disabled = "";
//		String Href = "javascript:jss.search(" + (currentPage + 1) + ")";
//		if (currentPage >= totalPage) {
//			Disabled = " previous disabled";
//			Href = "#";
//		}
//		StringBuilder sb = new StringBuilder();
//		sb.append("<li class=\"paginate_button "
//				+ Disabled
//				+ "\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">");
//		sb.append("<a href=\"" + Href + "\">下一页</a></li>");
//		return sb.toString();
//	}
//
///**
// * 生成尾页的html
// * @author hailongzhao
// * @date 20150827
// * @param currentPage
// * @param totalPage
// * @return
// */
//	private static String getEndPage(int currentPage, int totalPage) {
//		String Disabled = "";
//		String Href = "javascript:jss.search(" + totalPage + ")";
//		if (currentPage == totalPage) {
//			Disabled = " previous disabled";
//			Href = "#";
//		}
//		StringBuilder sb = new StringBuilder();
//		sb.append("<li class=\"paginate_button "
//				+ Disabled
//				+ "\" aria-controls=\"DataTables_Table_0\" tabindex=\"0\" id=\"DataTables_Table_0_previous\">");
//		sb.append("<a href=\"" + Href + "\">尾页</a></li>");
//		return sb.toString();
//	}
}
