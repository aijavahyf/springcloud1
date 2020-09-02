package cn.tedu.sp04.order.feign;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name="item-service",fallback = ItemFeignClientFB.class)
public interface ItemFeignClient {
    //@RequestMapping(path = "",method = RequestMethod.GET)  和下面等价
    @GetMapping("/{orderId}}")
    JsonResult<List<Item>> getItems(@PathVariable String orderId);
    @PostMapping("/decreaseNumber")
    JsonResult decreaseNumber(@RequestBody List<Item> items);
}
