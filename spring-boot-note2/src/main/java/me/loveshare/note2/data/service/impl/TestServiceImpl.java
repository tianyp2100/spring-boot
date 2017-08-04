package me.loveshare.note2.data.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.loveshare.note2.data.dao.T1Mapper;
import me.loveshare.note2.data.dao.T2Mapper;
import me.loveshare.note2.data.entity.dbo.T1;
import me.loveshare.note2.data.entity.dbo.T2;
import me.loveshare.note2.data.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Tony on 2017/8/1.
 */
@Slf4j
@Service
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    private T1Mapper t1Mapper;
    @Autowired
    private T2Mapper t2Mapper;

    @Override
    public boolean save(T1 t1, T2 t2) {
        int row = 0;
        row += t2Mapper.insert(t2);
        if (row == 1) log.info("T2 data insert successful." + t2.getId());
        row += t1Mapper.insert(t1);
        if (row == 2) log.info("T1 data insert successful." + t1.getId());
        return row == 2;
    }

}
