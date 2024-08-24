package rey.bos.highload.sn.core.config.database;

public class DataSourceContextHolder {

    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public enum DataSourceType {
        MASTER, REPLICA
    }

    public static void setDataSourceType(DataSourceType type) {
        contextHolder.set(type);
    }

    public static DataSourceType getDataSourceType() {
        return contextHolder.get() != null ? contextHolder.get() : DataSourceType.MASTER;
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

}
