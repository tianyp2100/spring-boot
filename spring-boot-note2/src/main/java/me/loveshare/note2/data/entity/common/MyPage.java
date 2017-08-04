package me.loveshare.note2.data.entity.common;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tony on 2017/8/4.
 * 对Pagehelper的Page<E>结果进行包装
 */
public class MyPage<T> extends Page<T> {

    public MyPage(List<T> list) {
        super();
        if (list instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
            this.pageIndex = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.totalPage = page.getPages();
            this.datas = page;
            this.totalCount = (int) page.getTotal();
            this.currentPageDatasCount = this.datas.size();
        } else if (list instanceof Collection) {
            this.pageIndex = 1;
            this.pageSize = list.size();

            this.totalPage = 1;
            this.datas = list;
            this.totalCount = list.size();
            this.currentPageDatasCount = this.datas.size();
        }
    }
}
