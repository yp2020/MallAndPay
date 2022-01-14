package com.study.mall.service.Impl;

import com.study.mall.dao.UserMapper;
import com.study.mall.enums.ResponseEnum;
import com.study.mall.pojo.User;
import com.study.mall.service.IUserService;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author yang
 * @date 2022/01/14 21:19
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo register(User user) {
        error();
        // username 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername>0){
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }

        // email 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail>0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        //处理密码  MD5 加密
        user.setPassword(DigestUtils.md5DigestAsHex(
                user.getPassword().getBytes(StandardCharsets.UTF_8)));


        // 写入数据库
        int resultCount = userMapper.insertSelective(user);
        if(resultCount==0){
           return ResponseVo.error(ResponseEnum.ERROR);
        }
        return  ResponseVo.success();
    }

    public void error(){
        throw new RuntimeException("运行时错误");
    }
}