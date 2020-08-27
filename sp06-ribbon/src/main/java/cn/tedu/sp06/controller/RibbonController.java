package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class RibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        log.info("ribbon...........");
        JsonResult r=restTemplate.getForObject(
                "http://item-service/{1}",
                JsonResult.class,
                orderId);
        return r;
    }
    @PostMapping("/item-service/decreaseNumber")
    public  JsonResult decreaseNumber(@RequestBody List<Item> items){
        log.info("ribbon...........");
        JsonResult r=restTemplate.postForObject(
                "http://item-service/decreaseNumber",
                    items,
                  JsonResult.class);
        return r;
    }
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return restTemplate.getForObject(
                "http://user-service/{1}",
                JsonResult.class,
                userId);
    }
    @GetMapping("/user-service/{userId}/score")
    public JsonResult addScore(@PathVariable Integer userId,Integer score){
        return  restTemplate.getForObject(
                "http://user-service/{1}",
                JsonResult.class,
                userId,score);
    }
    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return restTemplate.getForObject(
                "http://order-service/{1}",
                JsonResult.class,
                orderId);
    }
    @GetMapping("/order-service")
    public JsonResult addOrder() {
        return restTemplate.getForObject(
                "http://order-service/",
                JsonResult.class);
    }

}
