package rey.bos.highload.sn.core.config.database;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(ReadOnly)")
    public void setReadOnlyDataSource(JoinPoint joinPoint) {
        DataSourceContextHolder.setDataSourceType(DataSourceContextHolder.DataSourceType.REPLICA);
    }

    @After("@annotation(ReadOnly)")
    public void clearDataSourceType(JoinPoint joinPoint) {
        DataSourceContextHolder.clearDataSourceType();
    }

}