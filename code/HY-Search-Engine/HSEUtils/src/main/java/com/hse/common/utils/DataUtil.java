package com.hse.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2022.01.06
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class DataUtil {

    /**
     * ex,有A、B两集合，需要把B集合中对应A集合的数据添加到A集合中
     * A: [{"opId":"123", "name":"test"},{} //...多条记录]
     * 现在要在A中新增一个数组变为:A: [{
     *                                  "opId":"123",
     *                                  "name":"test",
     *                                  "family": [{},{}] //这个datas就是集合B中符合A规则的数据，因为集合B是无序的所以集合B不一定全部都能用完
     *                               },
     *                               {} //...多条记录
     *                              ]
     *  B集合的数据可能是这样的[{"uuid":"123","sister":"堂妹", "brother":"堂兄"},
     *                       {"uuid":"123","sister":"堂姐", "brother":"堂哥"},
     *                        {"uuid":"456","sister":"表姐", "brother":"表哥"}
     *                       {}//...多条记录]
     *  显然我们就要从B集合中把uuid与A集合中opId相同的记录拿出来,其实就是uuid=opId=123,作为数据集以key为family加入A集合中
     *
     * @param parents:父集合,就是例子中的A集合
     * @param parentKey:就是A中的opId
     * @param parentAddKey:就是A中的family
     * @param childs:子集合就是B中的uuid
     * @param childKey:就是B中的uuid
     */
    public void addChildDatasToParent(List<Map<String, Object>> parents,
                                       String parentKey,
                                       String parentAddKey,
                                       List<Map<String, Object>> childs,
                                       String childKey) {
        //切记小数据集要驱动大数据集哈
        for(Map<String, Object> parent: parents) {

            String parentValue = (String) parent.get(parentKey);
            List<Map<String, Object>> tempList = new ArrayList<>();

            for(Map<String, Object> child: childs) {

                String childValue = (String) child.get(childKey);

                if(parentValue.equals(childValue)) {
                    tempList.add(child);
                }
            }

            if(tempList.size() == 0) {
                continue;
            }

            parent.put(parentAddKey, tempList);
            childs.removeAll(tempList);
        }
    }
}
