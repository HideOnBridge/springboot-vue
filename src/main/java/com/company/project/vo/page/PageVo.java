package com.company.project.vo.page;

import com.company.project.entity.FileDocument;
import java.io.Serializable;
import java.util.List;

/**
 * *自定义分页VO
* @author machao
* @version V1.0
* @date 2021/2/3
*/

public class PageVo implements Serializable {
    private int Size;  //每页几条记录*
    private int Pages; //总页数
    private List<FileDocument> records;  //数据集合list*
    private int current;  //当前页*
    private int Total;    //总条数

    public PageVo(){}

    public PageVo(List<FileDocument> list, int current, int size){
        this.records = list;
        this.current = current;
        this.Size = size;
    }


    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public int getPages() {
        return Pages;
    }

    public void setPages(int pages) {
        Pages = pages;
    }

    public List<FileDocument> getRecords() {
        return records;
    }

    public void setRecords(List<FileDocument> records) {
        this.records = records;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
