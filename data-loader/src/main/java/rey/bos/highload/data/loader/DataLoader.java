package rey.bos.highload.data.loader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rey.bos.highload.social.network.core.io.repository.UserRepository;
import rey.bos.highload.social.network.core.util.Utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Profile("load-data")
@Slf4j
@Component
@AllArgsConstructor
public class DataLoader {

    private static final int THREAD_COUNT = 8;
    private static final int BATCH_SIZE = 10;

    private final UserRepository userRepository;
    private final Utils utils;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        if (userRepository.count() > 10_000) {
            log.info("downloading test users is not required");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        ClassPathResource resource = new ClassPathResource("people_dataset.csv");
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            List<String[]> recordsBatch = new ArrayList<>();
            String[] row;
            while ((row = reader.readNext()) != null) {
                recordsBatch.add(row);
                if (recordsBatch.size() == BATCH_SIZE) {
                    executor.execute(new UserLoaderTask(recordsBatch, userRepository, utils, passwordEncoder));
                    recordsBatch = new ArrayList<>();
                }
            }
            if (!recordsBatch.isEmpty()) {
                executor.execute(new UserLoaderTask(recordsBatch, userRepository, utils, passwordEncoder));
            }
        } catch (IOException | CsvException e) {
            log.error("Error loading data for load testing", e);
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                log.error("Executor interrupted during shutdown", e);
            }
        }
    }
}