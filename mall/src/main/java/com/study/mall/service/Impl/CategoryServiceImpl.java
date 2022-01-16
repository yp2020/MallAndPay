package com.study.mall.service.Impl;

import com.study.mall.dao.CategoryMapper;
import com.study.mall.pojo.Category;
import com.study.mall.service.ICategoryService;
import com.study.mall.vo.CategoryVo;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.mall.consts.MallConst.ROOT_PARENT_ID;

/**
 * @author yang
 * @date 2022/01/16 19:39
 **/

@Service
public class CategoryServiceImpl implements ICategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        //要返回的数据对象
       // List<CategoryVo> categoryVoList=new ArrayList<>();

        //避免多次查询数据库, 所以一次先全部查出来，再针对拿到的数据进行操作
        List<Category> categories = categoryMapper.selectAll();

        //查出 parentId=0 的数据
//        for (Category category : categories) {
//            if(category.getParentId().equals(ROOT_PARENT_ID)){
//                CategoryVo categoryVo=new CategoryVo();
//                BeanUtils.copyProperties(category,categoryVo);
//                categoryVoList.add(categoryVo);
//            }
//        }

        //lambda + stream 优化
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .collect(Collectors.toList());

        // 查询子目录

        return ResponseVo.success(categoryVoList);
    }

    private  void finSubcategory(List<CategoryVo> categoryVoList,List<Category>categories){

    }

    private CategoryVo category2CategoryVo(Category category){
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}