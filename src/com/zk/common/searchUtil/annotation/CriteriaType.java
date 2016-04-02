package com.zk.common.searchUtil.annotation;

//枚举值的类型分别代表：等于，小于，小于等于，大于，大于等于，或，模糊前包含，模糊后包含。模糊任意包含，包含在内，不包含在内
public enum  CriteriaType{EQ,LT,LE,GT,GE,OR,BEFOR_LIKE,END_LIKE,ANY_LIKE,IN,NOTIN};
