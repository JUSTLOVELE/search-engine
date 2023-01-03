package com.hse.common.utils;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-07-10
 * @version 1.00.00
 * @history:
 */
public class CommonConstant {

	public static String Constant_PUBLIC_KEY = "";

	public static String Constant_PRIVATE_KEY = "";

	public static final String SUCCESS_SAVE_MSG = "保存成功!";

	public static final String SUCCESS_QUERY_MSG = "查询成功!";

	public static final String SUCCESS_LOGOUT_MSG = "注销成功!";

	public static final String SUCCESS_DELETE_MSG = "删除成功!";

	public static final String FAILURE_DELETE_MSG = "删除失败!";

	public static final String SUCCESS_UPDATE_MSG = "更新成功!";

	public static final String FAILURE_UPDATE_MSG = "更新失败!";

	public static final String SUCCESS_NO_DATA = "暂无数据!";

	public static final String FAILURE_MSG = "保存失败!";

	public static final String FAILURE_EDIT_MSG = "编辑失败!";

	public static final String SUCCESS_EDIT_MSG = "编辑成功!";

	public static final String SUCCESS_DOWNLOAD_MSG = "下载成功!";

	public static final String SUCCESS_REQUEST_MSG = "请求成功!";

	public static final String FAILURE_REQUEST_MSG = "请求失败!";

	public static final String SEND_SUCCESS = "发送成功";

	public interface Key {

		String CODE = "code";

		String SUCCESS = "success";

		String TOTAL = "total";

		String DESC = "desc";

		String DATA = "data";

		String DATAS = "datas";

		String PAGE = "page";

		String VALUE = "value";
	}
	
	public interface Status {
		
		public final static int SUCCESS_CODE = 100;
		
		public final static int FAILURE_CODE = 101;
		
		public final static int PRINT_CODE = 102;
		
		public int DELETE_CODE = 103;
	}

	public interface Menu{
		/**根节点**/
		public final static int ROOT = 0;
		/**子节点**/
		public final static int SON = 1;
		/**叶子节点**/
		public final static int LEAF = 2;
		/**菜单根节点**/
		public final static int MENU_ROOT = 21;
		/**所属平台:公示网**/
		public final static int SYS = 3;
	}
}
