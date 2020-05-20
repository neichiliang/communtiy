package life.myheart.community.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: community
 * @description: 图片上传
 * @author: ncl
 * @create: 2020-05-20 09:15
 **/
@Configuration
public class UploadConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//映射图片保存地址
        registry.addResourceHandler("/images/**").addResourceLocations("file:C:\\Users\\c_heweibin-001\\IdeaProjects\\communtiy\\src\\main\\resources\\static\\images\\");
//        registry.addResourceHandler("/images/**").addResourceLocations("file:/ncl/springboot/communtiy/src/main/resources/static/images/");
    }
}
