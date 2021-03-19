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

}