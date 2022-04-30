package com.oliverr.scrumw.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Count {

    private Integer count;

    public Count(int count) {
        this.count = count;
    }

    public Count(long count) {
        this.count = Integer.valueOf(count+"");
    }

    public Count() {
        this.count = 0;
    }

}
