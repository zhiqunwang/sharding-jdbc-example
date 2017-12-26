/**
* @date 2017年2月20日 下午2:20:03
* @version V1.0   
*/ 
package org.elvis.wang.service.test;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
*<b>测试基础类</b> 
* @ClassName: AbstractServiceTestSupport 
* @author zhiqun.wang
* @date 2017年2月20日 下午2:20:03 
*  
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:sharding-applicationContext.xml" })
public class AbstractShardingServiceTestSupport implements ApplicationContextAware {

    private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
