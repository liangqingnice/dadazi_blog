package online.dadazi.dadaziblog.config.listens;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 启动失败监听
 * @author lqk
 */
@Component
@Slf4j
public class AppStartEventFiledListener implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        log.error("启动失败");
    }
}
