package cn.tedu.sp11.filter;

import cn.tedu.web.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.RequestContent;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//添加过滤器
@Component
public class AcessFilter extends ZuulFilter {
    //过滤器的类型  pre-前置过滤器  post-后置过滤器 routing--路由过滤器, error
    @Override


    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
     /*顺序号--把自定义过滤器插到哪个位置  放在6位置  默认过滤器中，第五个过滤器在上下文对象中添加了  service-id
      在第五个过滤器之后，才能从上下文对象访问到service-id
      */
    @Override
    public int filterOrder() {
        return 6;
    }
  //对当前请求是否要进行过滤 返回true 执行过滤  执行过滤方法  返回false 不执行 跳过过滤代码 执行后面程序
    @Override
    public boolean shouldFilter() {
        //判断用户请求的是不是商品服务  商品服务进行过滤 其它服务不过滤
        //当前请求的上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        //从上下文对象获得客户端调用的 service-id
        String serviceId=(String)ctx.get(FilterConstants.SERVICE_ID_KEY);
        return "item-service".equals(serviceId);
    }

    /**
     * 过滤代码
     * 它的返回值，在当前zuul版本中，没有启用，返回任何数据都无效
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //要求 参数中带有token    token=2156
        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest request = cxt.getRequest();
        String token = request.getParameter("token");
        if ((StringUtils.isBlank(token))){
            //没有token，阻止这次调用继续执行
            cxt.setSendZuulResponse(false);
            //直接向客户端响应
            //JsonResult:{code:400,msg:not login,data:null}
            cxt.setResponseStatusCode(JsonResult.NOT_LOGIN);
            cxt.setResponseBody(JsonResult.err().code(JsonResult.NOT_LOGIN).msg("NOT LOG IN").toString());
        }
        return null;
    }
}
