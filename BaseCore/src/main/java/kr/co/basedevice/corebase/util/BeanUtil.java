package kr.co.basedevice.corebase.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BeanUtil {
    private final ApplicationContext applicationContext;

    public Object getBean(String name){
        return applicationContext.getBean(name);
    }

}