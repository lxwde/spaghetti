package com.lxwde.spaghetti.mybatis;

import com.lxwde.spaghetti.mybatis.Item;
import com.lxwde.spaghetti.mybatis.ItemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemMapperTest {

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void test(){
        List<Item> items = itemMapper.getAllItems();
        assertThat(items.size()).isEqualTo(3);
        items.forEach(item -> System.out.println(item));

        Item item = itemMapper.getItemByIdEx(items.get(0).getId());
        assertThat(item.getDescription()).isEqualTo(items.get(0).getDescription());
    }
}
