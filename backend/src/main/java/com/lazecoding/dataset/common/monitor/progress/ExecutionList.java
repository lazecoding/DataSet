package com.lazecoding.dataset.common.monitor.progress;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 执行列表
 *
 * @author lazecoding
 */
public class ExecutionList {

    private static final Map<String, ExecutionState> LIST = new LinkedHashMap<>();

    /**
     * 存放
     */
    public static void push(ExecutionState executionState) {
        if (ObjectUtils.isEmpty(executionState)) {
            return;
        }
        // 更新时间
        executionState.setUpdateDate(new Date());
        LIST.put(executionState.getUid(), executionState);
    }

    /**
     * 获取
     */
    public static ExecutionState get(String uid) {
        return LIST.get(uid);
    }

    /**
     * 是否存在
     */
    public static boolean isExist(String uid) {
        return LIST.containsKey(uid);
    }

    /**
     * 移除
     */
    public static void remove(ExecutionState executionState) {
        if (ObjectUtils.isEmpty(executionState)) {
            return;
        }
        remove(executionState.getUid());
    }

    /**
     * 移除
     */
    public static void remove(String uid) {
        if (!StringUtils.hasText(uid)) {
            return;
        }
        LIST.remove(uid);
    }

    /**
     * 清空
     */
    public static void clear() {
        LIST.clear();
    }

    /**
     * 获取列表
     */
    public static List<ExecutionState> list() {
        if (CollectionUtils.isEmpty(LIST)) {
            return null;
        }
        List<ExecutionState> executionStateList = new ArrayList<>();
        LIST.forEach((k, v) -> executionStateList.add(v));
        return executionStateList;
    }

    public static void main(String[] args) {
        ExecutionState one = new ExecutionState("one", "one");
        ExecutionList.push(one);
        ExecutionState two = new ExecutionState("two", "two");
        ExecutionList.push(two);
        System.out.println(ExecutionList.list().toString());
    }

}
