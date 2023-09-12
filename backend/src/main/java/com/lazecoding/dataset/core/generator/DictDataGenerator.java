package com.lazecoding.dataset.core.generator;

import com.lazecoding.dataset.common.util.BeanUtil;
import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.model.DictItem;
import com.lazecoding.dataset.service.DictService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 词典数据生成器
 *
 * @author lazecoding
 */
public class DictDataGenerator implements DataGenerator {


    private static final DictService dictService = BeanUtil.getBean(DictService.class);

    @Override
    public List<String> doGenerate(TableSchema.Field field, int rowNum) {
        String dictId = field.getMockParams();
        List<DictItem> dictItems = dictService.listItem(dictId);
        List<String> list = new ArrayList<>(rowNum);
        if (CollectionUtils.isEmpty(dictItems)) {
            for (int i = 0; i < rowNum; i++) {
                if (field.isNotNull()) {
                    list.add("nil");
                } else {
                    list.add("");
                }
            }
        } else {
            for (int i = 0; i < rowNum; i++) {
                DictItem item = dictItems.get(RandomUtils.nextInt(0, dictItems.size()));
                list.add(item.getValue());
            }
        }
        return list;
    }

    @Override
    public List<String> doGenerate(HttpRequest.Param param, int rowNum) {
        String dictId = param.getMockParams();
        List<DictItem> dictItems = dictService.listItem(dictId);
        List<String> list = new ArrayList<>(rowNum);
        if (CollectionUtils.isEmpty(dictItems)) {
            for (int i = 0; i < rowNum; i++) {
                list.add("");
            }
        } else {
            for (int i = 0; i < rowNum; i++) {
                DictItem item = dictItems.get(RandomUtils.nextInt(0, dictItems.size()));
                list.add(item.getValue());
            }
        }
        return list;
    }
}
