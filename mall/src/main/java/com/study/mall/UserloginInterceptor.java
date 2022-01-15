package com.study.mall;

import com.study.mall.consts.MallConst;
import com.study.mall.exception.UserLoginException;
import com.study.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yang
 * @date 2022/01/15 09:07
 **/
@Slf4j
public class UserloginInterceptor implements HandlerInterceptor {
    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      log.info("------------- preHandler ----------");
        User user=(User)request.getSession().getAttribute(MallConst.CURRENT_USER);
        if(user==null){
            log.info("user == null");
            throw  new UserLoginException();
        }
        return true;
    }
}