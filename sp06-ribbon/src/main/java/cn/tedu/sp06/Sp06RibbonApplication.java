package cn.tedu.sp06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient//可以省略
@SpringBootApplication
public class Sp06RibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp06RibbonApplication.class, args);
    }
   @Bean
   @LoadBalanced
    public RestTemplate restTemplate()
   {
       //设置超时时间
       SimpleClientHttpRequestFactory s=new SimpleClientHttpRequestFactory();
       s.setConnectTimeout(1000);
       s.setReadTimeout(1000);
        return new RestTemplate(s);
   }
}
