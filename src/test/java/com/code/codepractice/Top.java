package com.code.codepractice;
import lombok.Data;
import com.code.codepractice.dto.Student;

import java.util.List;

/**
 * @Author: dongxin
 * @Date: 2019/4/17 15:01
 **/
@Data
public class Top {
    private List<Student> students;
     private Double amount;
    private String orderNo;

    public Top() {
    }

    private Top(Builder builder) {
        this.students = builder.students;
        this.amount = builder.amount;
        this.orderNo = builder.orderNo;
    }

    public static Builder newTop() {
        return new Builder();
    }

    public static final class Builder {
        private List<Student> students;
        private Double amount;
        private String orderNo;

        private Builder() {
        }

        public Top build() {
            return new Top(this);
        }

        public Builder students(List<Student> students) {
            this.students = students;
            return this;
        }

        public Builder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder orderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }
    }


}
