package cn.eastseven;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void matchUrl() {
        //  matcher.match(pattern=/users/{id}, request=/users/1)=false, p=false
        Pattern pattern = Pattern.compile("/users/*/group");
        String url = "/users/1/group?t="+System.currentTimeMillis();
        Matcher matcher = pattern.matcher(url);
        log.debug(">>> p={}, url={}, matches={}", pattern.pattern(), url, matcher.find());
    }
}

