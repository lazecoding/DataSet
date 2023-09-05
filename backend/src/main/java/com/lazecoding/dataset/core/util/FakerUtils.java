package com.lazecoding.dataset.core.util;

import com.lazecoding.dataset.core.enums.MockParamsRandomTypeEnum;
import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 随机数据生成
 *
 * @author lazecoding
 */
public class FakerUtils {

    /**
     * 中文实例
     */
    private final static Faker ZH_FAKER = new Faker(new Locale("zh-CN"));

    /**
     * 英文实例
     */
    private final static Faker EN_FAKER = new Faker();

    /**
     * 格式化时间
     */
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取随机值
     */
    public static String getRandomValue(MockParamsRandomTypeEnum randomTypeEnum) {
        String defaultValue = RandomStringUtils.randomAlphanumeric(2, 20);
        if (randomTypeEnum == null) {
            return defaultValue;
        }
        switch (randomTypeEnum) {
            case NAME:
                return ZH_FAKER.name().name();
            case CITY:
                return ZH_FAKER.address().city();
            case EMAIL:
                return EN_FAKER.internet().emailAddress();
            case URL:
                return EN_FAKER.internet().url();
            case IP:
                return ZH_FAKER.internet().ipV4Address();
            case INTEGER:
                return String.valueOf(ZH_FAKER.number().randomNumber());
            case DECIMAL:
                return String.valueOf(RandomUtils.nextFloat(0, 100000));
            case UNIVERSITY:
                return ZH_FAKER.university().name();
            case DATE:
                return EN_FAKER.date()
                        .between(Timestamp.valueOf("1996-01-01 00:00:00"), Timestamp.valueOf("2023-01-01 00:00:00"))
                        .toLocalDateTime().format(DATE_TIME_FORMATTER);
            case TIMESTAMP:
                return String.valueOf(EN_FAKER.date()
                        .between(Timestamp.valueOf("1996-01-01 00:00:00"), Timestamp.valueOf("2023-01-01 00:00:00"))
                        .getTime());
            case PHONE:
                return ZH_FAKER.phoneNumber().cellPhone();
            default:
                return defaultValue;
        }
    }

}
