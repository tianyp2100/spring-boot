package me.loveshare.note1.data.entity.vo.common;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Tony on 2017/3/22.
 * 时间查询对象(包括值、排序、分页查询)--其他类可继承扩展使用
 */
public class TimeQuery extends ValueSortQuery implements Serializable {

    private static final long serialVersionUID = -4448002788433358542L;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
