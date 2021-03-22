package woo.woot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import woo.woot.domain.Dtype;
import woo.woot.domain.Item;
import woo.woot.repository.ItemRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품_등록 () throws Exception{
        Item item1 = new Item();
        item1.setItem_name("나이키 모자");
        item1.setDtype(Dtype.HEADWEAR);
        item1.setFilePath("/asdasd/asdasd");
        item1.setPrice(10000);
        item1.setStockQuantity(100);
        item1.setOrder_count(0);
        itemService.saveItem(item1);
    }

    @Test
    public void 상품_타입별_가져오기 () throws Exception{
        Item item1 = new Item();
        item1.setItem_name("나이키 모자");
        item1.setDtype(Dtype.HEADWEAR);
        item1.setFilePath("/asdasd/asdasd");
        item1.setPrice(10000);
        item1.setStockQuantity(100);
        item1.setOrder_count(0);
        itemService.saveItem(item1);

        Item item2 = new Item();
        item2.setItem_name("나이키 신발");
        item2.setDtype(Dtype.SHOES);
        item2.setFilePath("/asdasd/asdasd");
        item2.setPrice(100000);
        item2.setStockQuantity(200);
        item2.setOrder_count(0);
        itemService.saveItem(item2);

        Item item3 = new Item();
        item3.setItem_name("아디다스 신발");
        item3.setDtype(Dtype.SHOES);
        item3.setFilePath("/asdasd/asdasd");
        item3.setPrice(100000);
        item3.setStockQuantity(200);
        item3.setOrder_count(0);
        itemService.saveItem(item3);

        List<Item> items = itemRepository.findItemsByDtype(Dtype.SHOES);
        System.out.println(items);
    }

}