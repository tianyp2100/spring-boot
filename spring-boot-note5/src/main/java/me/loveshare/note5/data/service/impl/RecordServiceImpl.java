package me.loveshare.note5.data.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.loveshare.note5.data.entity.dbo.ResourceData;
import me.loveshare.note5.data.service.RecordService;
import org.springframework.stereotype.Service;

/**
 * Created by Tony on 2017/8/5.
 */
@Slf4j
@Service
public class RecordServiceImpl implements RecordService {
    @Override
    public String record(ResourceData data) {
        log.info("资源记录数据:" + JSON.toJSONString(data));
        return data.getCode();
    }
}
