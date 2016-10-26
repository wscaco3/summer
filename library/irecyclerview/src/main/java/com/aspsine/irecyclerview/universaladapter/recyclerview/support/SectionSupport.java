package com.aspsine.irecyclerview.universaladapter.recyclerview.support;

public interface SectionSupport<T>
{
    int sectionHeaderLayoutId();

    int sectionTitleTextViewId();

    String getTitle(T t);
}
