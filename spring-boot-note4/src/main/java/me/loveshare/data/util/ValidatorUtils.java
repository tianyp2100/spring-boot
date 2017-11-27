package me.loveshare.data.util;

import me.loveshare.data.entity.bo.common.BizResult;
import me.loveshare.data.entity.bo.common.JsonResult;
import me.loveshare.data.entity.bo.common.JsonResultEnum;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Tony on 2017/10/11.
 */
public class ValidatorUtils {
    /**
     * Validator数据验证  --单个对象
     *
     * @param t   待验证的对象
     * @param <T> 验证对象的泛型
     * @return BizResult的bizResult.getResult().isFlag()=true验证通过，否则，bizResult.getResult()为信息封装完成的JsonResult对象:code = 807
     */
    public static final <T> BizResult<JsonResult> validator(T t) {
        BizResult<JsonResult> bizResult = BizResult.create(JsonResult.class);
        bizResult.getResult().setCode(JsonResultEnum.WARMTIPS.getCode());
        Set<ConstraintViolation<T>> constraintViolationSet = validatorFactory().validate(t);
        if (CollectionUtils.isNotEmptyList(constraintViolationSet)) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolationSet.iterator();
            Set<String> messageList = new HashSet<String>();
            while (iterator.hasNext()) {
                messageList.add(iterator.next().getMessage());
            }
            bizResult.getResult().setData(messageList);
        } else {
            bizResult.setFlag(true);
        }
        return bizResult;
    }

    /**
     * Validator数据验证  --列表集合数据
     *
     * @param t   待验证的对象
     * @param <T> 验证对象的泛型
     * @return BizResult的bizResult.getResult().isFlag()=true验证通过，否则，bizResult.getResult()为信息封装完成的JsonResult对象:code = 807
     */
    public static final <T> BizResult<JsonResult> validator(Collection<T> t) {
        BizResult<JsonResult> bizResult = BizResult.create(JsonResult.class);
        bizResult.getResult().setCode(JsonResultEnum.WARMTIPS.getCode());
        Set<String> messageList = new HashSet<String>();
        Iterator<T> contactIterator = t.iterator();
        while (contactIterator.hasNext()) {
            T ti = contactIterator.next();
            Set<ConstraintViolation<T>> constraintViolationSet = validatorFactory().validate(ti);
            Iterator<ConstraintViolation<T>> iterator = constraintViolationSet.iterator();
            while (iterator.hasNext()) {
                messageList.add(iterator.next().getMessage());
            }
        }
        if (CollectionUtils.isNotEmptyList(messageList)) {
            bizResult.getResult().setData(messageList);
        } else {
            bizResult.setFlag(true);
        }
        return bizResult;
    }

    /**
     * 数据校验工厂
     *
     * @return
     */
    private static final Validator validatorFactory() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }

    /**
     * 专门校验Set<Integer>
     *
     * @param isMust     是否必须
     * @param integerSet 数据集合
     * @param min        最小值
     * @param max        最大值
     * @param label      提示标识
     * @return
     */
    public static final BizResult<JsonResult> validatorInteger(boolean isMust, Collection<Integer> integerSet, int min, int max, String label) {
        BizResult<JsonResult> bizResult = BizResult.create(JsonResult.class);
        bizResult.getResult().setCode(JsonResultEnum.WARMTIPS.getCode());
        if (CollectionUtils.isEmptyList(integerSet)) {
            if (isMust) {
                bizResult.getResult().setMessage(label + "不能为空");
            } else {
                bizResult.setFlag(true);
            }
            return bizResult;
        }
        boolean resOk = true;
        Iterator<Integer> integerIterator = integerSet.iterator();
        while (integerIterator.hasNext()) {
            int item = integerIterator.next();
            if (item < min) {
                resOk = false;
                bizResult.getResult().setMessage(label + "最小" + max);
                return bizResult;
            }
            if (item > max) {
                resOk = false;
                bizResult.getResult().setMessage(label + "最大" + max);
                return bizResult;
            }
        }
        if (resOk) {
            bizResult.setFlag(true);
        }
        return bizResult;
    }
}
