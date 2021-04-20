package com.ecommerce.demo.config;

import com.ecommerce.demo.service.impl.OrderServiceImpl;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

/**
 * JobDetail 的配置有两种方式：MethodInvokingJobDetailFactoryBean 和 JobDetailFactoryBean 。
 * 使用 MethodInvokingJobDetailFactoryBean 可以配置目标 Bean 的名字和目标方法的名字，这种方式不支持传参。
 * 使用 JobDetailFactoryBean 可以配置 JobDetail ，任务类继承自 QuartzJobBean ，这种方式支持传参，将参数封装在 JobDataMap 中进行传递。
 * Trigger 是指触发器，Quartz 中定义了多个触发器，这里向大家展示其中两种的用法，SimpleTrigger 和 CronTrigger 。
 * SimpleTrigger 有点类似 @Scheduled 的基本用法。
 * CronTrigger 则有点类似于 @Scheduled 中 cron 表达式的用法。
 */

/**
 * 定时任务
 */
@Configuration
public class TimedTask {
    //    @Bean
//    MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
//        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
//        bean.setTargetBeanName("myJob1");
//        bean.setTargetMethod("sayHello");
//        return bean;
//    }
    @Bean
    public JobDetail QuartzDetail() {
        return JobBuilder.newJob(OrderServiceImpl.class).withIdentity("orderServiceImpl").storeDurably().build();
    }

    //    @Bean
//    SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
//        SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();
//        bean.setStartTime(new Date());
//        bean.setRepeatCount(5);
//        bean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
//        bean.setRepeatInterval(3000);
//        return bean;
//    }
    @Bean
    public Trigger QuartzTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(10) //设置时间周期单位分钟
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(QuartzDetail())
                .withIdentity("orderServiceImpl")
                .withSchedule(scheduleBuilder)
                .build();
    }
//    @Bean
//    SchedulerFactoryBean schedulerFactoryBean() {
//        SchedulerFactoryBean bean = new SchedulerFactoryBean();
//        bean.setTriggers(cronTrigger().getObject(), simpleTriggerFactoryBean().getObject());
//        return bean;
//    }
//    @Bean
//    long getValidTime(){
//    return System.currentTimeMillis() - 1000*60*10;
//    }
}
