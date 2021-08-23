package com.zpmc.ztos.infra.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:beanRefFactory.xml","classpath*:BizApplicationContext.xml","classpath*:FrameworkBizApplicationContext.xml",
                "classpath*:SecurityBizApplicationContext.xml","classpath*:WebserviceApplicationContext.xml"})
public class XmlConfiguration {

}
