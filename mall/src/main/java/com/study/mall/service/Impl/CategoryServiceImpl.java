package com.study.mall.service.Impl;

import com.study.mall.dao.CategoryMapper;
import com.study.mall.pojo.Category;
import com.study.mall.service.ICategoryService;
import com.study.mall.vo.CategoryVo;
import com.study.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());

        // 查询子目录
        finSubcategory(categoryVoList,categories);

        return ResponseVo.success(categoryVoList);
    }

    private  void finSubcategory(List<CategoryVo> categoryVoList,List<Category>categories){
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList=new ArrayList<>();

            for (Category category : categories) {
                if(category.getParentId().equals(categoryVo.getId())){
                    //父目录的 id 等于 子目录的 parentId

                    //查询到了，
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
            }
            //进行排序
            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            //完成子目录的设置
            categoryVo.setSubCategories(subCategoryVoList);
            finSubcategory(subCategoryVoList,categories);
        }
    }

    private CategoryVo category2CategoryVo(Category category){
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}