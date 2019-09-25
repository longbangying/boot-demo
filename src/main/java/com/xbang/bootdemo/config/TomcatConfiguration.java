package com.xbang.bootdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextClosedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Import(TomcatConfiguration.MyTomcatServletWebServerFactoryCustomizer.class)
@Slf4j
public class TomcatConfiguration {
    @Bean
    public ServletWebServerFactory tomcatServletWebServerFactory(MyTomcatServletWebServerFactoryCustomizer myTomcatServletWebServerFactoryCustomizer) {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        List<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new ArrayList<>(1);
        tomcatConnectorCustomizers.add(myTomcatServletWebServerFactoryCustomizer);
        tomcatServletWebServerFactory.setTomcatConnectorCustomizers(tomcatConnectorCustomizers);
        return tomcatServletWebServerFactory;
    }



    public static class MyTomcatServletWebServerFactoryCustomizer implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

        private Connector connector;

        @Override
        public void customize(Connector connector) {
            this.connector = connector;
            //Executor 是一个线程池对象   取出来后可以对它的属性进行修改
            //Executor executor = connector.getProtocolHandler().getExecutor();
        }


        @Override
        public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
            log.info("source:{}",contextClosedEvent.getSource().getClass().getName());
            if(null == connector){
                return ;
            }
            //不再接受新的请求
            connector.pause();
            Executor executor = connector.getProtocolHandler().getExecutor();
            ThreadPoolExecutor threadPoolExecutor = null;
            if(executor instanceof ThreadPoolExecutor){
                threadPoolExecutor = (ThreadPoolExecutor) executor;
            }
            if(null == threadPoolExecutor){
                return ;
            }
            log.info("currentSize:{}",threadPoolExecutor.getActiveCount());
            try {
                if(threadPoolExecutor.getActiveCount() > 0){
                    //如果还有请求
                    threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS);
                }
                threadPoolExecutor.shutdown();
            }catch (InterruptedException ex){
                log.error("shutdown service with error:{}",ex.getMessage());
            }
        }
    }

}
