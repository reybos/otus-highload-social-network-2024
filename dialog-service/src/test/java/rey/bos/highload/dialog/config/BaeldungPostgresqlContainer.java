package rey.bos.highload.dialog.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class BaeldungPostgresqlContainer extends PostgreSQLContainer<BaeldungPostgresqlContainer> {

    private static final String IMAGE_VERSION = "citusdata/citus:latest";
    private static final DockerImageName CITUS_IMAGE = DockerImageName.parse(IMAGE_VERSION)
        .asCompatibleSubstituteFor("postgres");

    private static BaeldungPostgresqlContainer container;

    private BaeldungPostgresqlContainer() {
        super(CITUS_IMAGE);
    }

    public static BaeldungPostgresqlContainer getInstance() {
        if (container == null) {
            container = new BaeldungPostgresqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}