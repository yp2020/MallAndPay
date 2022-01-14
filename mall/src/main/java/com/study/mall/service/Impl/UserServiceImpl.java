package com.study.mall.service.Impl;

import com.study.mall.dao.UserMapper;
import com.study.mall.pojo.User;
import com.study.mall.service.IUserService;
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
    public void register(User user) {
        // username 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername>0){
            throw new RuntimeException("用户名已存在");
        }

        // email 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail>0){
            throw new RuntimeException("该 email 已存在");
        }

        //处理密码  MD5 加密
        user.setPassword(DigestUtils.md5DigestAsHex(
                user.getPassword().getBytes(StandardCharsets.UTF_8)));


        // 写入数据库
        int resultCount = userMapper.insertSelective(user);
        if(resultCount==0){
            throw new RuntimeException("注册失败");
        }
    }
}