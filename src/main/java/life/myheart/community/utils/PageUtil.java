package life.myheart.community.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageUtil<T> {
        private int code = 0;
        private String msg;
        private Long count; //总条数
        private List<T> data = new ArrayList(); //装前台当前页的数据
        //getter/setter方法...

    }

