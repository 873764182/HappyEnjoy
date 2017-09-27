package com.pixel.he.bean;

import com.pixel.he.utils.ListUtil;

import pixel.database.library.OnDbIdCallback;
import pixel.database.library.TableColumn;

/**
 * Created by pixel on 2017/9/26.
 */

public class SzBean implements OnDbIdCallback, ListUtil.OnGroupKeyInterface, ListUtil.OnSortValueInterface {

    public Long _id = 0L;

    @TableColumn
    public String year = "";  // 年

    @TableColumn
    public String month = "";  // 月

    @TableColumn
    public String day = "";  // 日

    @TableColumn
    public String hour = "";  // 时

    @TableColumn
    public String minutes = "";  // 分

    @TableColumn
    public Long dateTime = 0L;      // 时间的时间戳形式 为了排序

    @TableColumn
    public String amount = "0";        // 收入/支出 金额

    @TableColumn
    public String type = "0";        // 0.无效数据 1.收入 -1.支出

    @TableColumn
    public String describe = "";        // 工资，购物

    @TableColumn
    public String note = "";        // 金额备注

    @Override
    public void setId(Long aLong) {
        this._id = aLong;
    }

    @Override
    public Object getGroupKey() {
        return year;
    }

    @Override
    public double getSortValue() {
        return dateTime;
    }
}
