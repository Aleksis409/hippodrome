import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Disabled
class MainTest {
    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    void mainMethodCompletesUnder22Seconds() throws Exception {
        Main.main(new String[0]);
    }
}