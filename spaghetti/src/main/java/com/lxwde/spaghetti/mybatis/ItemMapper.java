package com.lxwde.spaghetti.mybatis;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<Item> getAllItems();
    Item getItemById(Integer id);
    void insertItem(Item item);
    void updateItem(Item item);
    void deleteItem(Integer itemId);

    @Select("select * from item where id=#{id}")
    Item getItemByIdEx(Integer id);
}
