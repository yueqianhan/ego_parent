package com.ego.cart.interceptor;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.commons.utils.ServletUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hanyueqian
 * @date 2019/10/11 0011-下午 16:10
 */
@Component
public class LoginInterceptor implements HandlerInterceptor
{

    @Value("${custom.passport.getUser}")
    private String url;

    @Value("${custom.passport.showLogin}")
    private String loginUrl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String json = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN");
        if (json!=null&&!json.equals(""))
        {
            String result = HttpClientUtil.doGet(url + json);
            EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
            if (er.getStatus()==200)
            {
                return true;
            }
        }
        //用户未登录
        response.sendRedirect(loginUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

    }
}
