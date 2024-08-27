package rey.bos.highload.dialog;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
@Profile("test")
public class TestUtil {

    public String readJson(String file, Object... args) throws IOException {
        String jsonTemplate = new String(
            Files.readAllBytes(Paths.get(String.format("src/test/resources/%s", file)))
        );

        Object[] formattedArgs = Arrays.stream(args)
            .map(arg -> arg == null ? null : String.format("\"%s\"", arg))
            .toArray(Object[]::new);

        return String.format(jsonTemplate, formattedArgs);
    }

}
