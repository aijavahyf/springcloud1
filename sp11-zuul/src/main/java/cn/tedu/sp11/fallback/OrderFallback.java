package cn.tedu.sp11.fallback;

import cn.tedu.web.util.JsonResult;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
@Component
public class OrderFallback implements FallbackProvider {
    /**
     * 返回service id
     * 针对哪个服务应用当前降级类
     * 如果返回 “*” 或null，表示对所有的服务都执行降级类
     * @return
     */
    @Override
    public String getRoute() {
        return "order-service";
    }
    /**
     *
     * @param route  封装降级响应对象
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {

        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                //JsonResult:{code:200,msg:"调用远程服务失败"，data:null}
                String json = JsonResult.err().msg("调用订单服务失败").toString();
                ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes());
                return in;
            }

            @Override
            public HttpHeaders getHeaders() {
                //Content-Type
                HttpHeaders h = new HttpHeaders();
                h.setContentType(MediaType.APPLICATION_JSON);
                return h;
            }
        };
    }
}
