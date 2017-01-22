package com.lxwde.spaghetti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ItemMapper itemMapper;

    @RequestMapping(method = GET)
    public List<Item> getItems() {
        return itemMapper.getAllItems();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Item getItem(@PathVariable Integer id) {
        return itemMapper.getItemById(id);
    }

    @RequestMapping(method = POST)
    public Item addItem(@RequestBody Item item) {
        item.setId(null);
        itemMapper.insertItem(item);
        return item;
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public Item updateItem(@RequestBody Item item, @PathVariable Integer id) {
        item.setId(id);
        itemMapper.updateItem(item);
        return item;
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void deleteItem(@PathVariable Integer id) {
        itemMapper.deleteItem(id);
    }
}
