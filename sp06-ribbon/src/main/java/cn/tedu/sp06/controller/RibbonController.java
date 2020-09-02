package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod="getItemsFB") //指定降级方法的方法名
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        log.info("ribbon...........");
        JsonResult r=restTemplate.getForObject(
                "http://item-service/{1}",
                JsonResult.class,
                orderId);
        return r;
    }

    @HystrixCommand(fallbackMethod="decreaseNumberFB")
    @PostMapping("/item-service/decreaseNumber")
    public  JsonResult decreaseNumber(@RequestBody List<Item> items){
        log.info("ribbon...........");
        JsonResult r=restTemplate.postForObject(
                "http://item-service/decreaseNumber",
                    items,
                  JsonResult.class);
        return r;
    }

    @HystrixCommand(fallbackMethod="getUserFB")
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return restTemplate.getForObject(
                "http://user-service/{1}",
                JsonResult.class,
                userId);
    }

    @HystrixCommand(fallbackMethod="addScoreFB")
    @GetMapping("/user-service/{userId}/score")
    public JsonResult addScore(@PathVariable Integer userId,Integer score){
        return  restTemplate.getForObject(
                "http://user-service/{1}",
                JsonResult.class,
                userId,score);
    }

    @HystrixCommand(fallbackMethod="getOrderFB")
    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return restTemplate.getForObject(
                "http://order-service/{1}",
                JsonResult.class,
                orderId);
    }

    @HystrixCommand(fallbackMethod="addOrderFB")
    @GetMapping("/order-service")
    public JsonResult addOrder() {
        return restTemplate.getForObject(
                "http://order-service/",
                JsonResult.class);
    }
    /////////////////////////////////////////////////////////////////


    public JsonResult<List<Item>> getItemsFB( String orderId){

        return JsonResult.err().msg("获取商品信息失败");
    }


    public  JsonResult decreaseNumberFB( List<Item> items){
        return JsonResult.err().msg("减少商品库存失败");
    }


    public JsonResult<User> getUserFB(Integer userId){
        return JsonResult.err().msg("获取用户失败");
    }


    public JsonResult addScoreFB( Integer userId,Integer score){
        return JsonResult.err().msg("增加用户积分失败");
    }


    public JsonResult<Order> getOrderFB(String orderId){
        return JsonResult.err().msg("获取订单失败");
    }


    public JsonResult addOrderFB() {
        return JsonResult.err().msg("保存订单失败");
    }
}
