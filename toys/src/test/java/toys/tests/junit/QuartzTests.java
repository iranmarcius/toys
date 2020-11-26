package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.application.quartz.QuartzJobConfig;
import toys.application.quartz.QuartzUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuartzTests {

    @Test
    void testRead() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        var props = new Properties();
        props.put("job.job1.jobClassName", "org.chuckwalla.MeuJob1");
        props.put("job.job1.cronSchedule", "*/5");
        props.put("job.job2.jobClassName", "org.chuckwalla.MeuJob2");
        props.put("job.job2.cronSchedule", "*/10");
        props.put("job.job2.oneTimeRunDelay", "2000");
        props.put("job.job2.umaConfiguracao", "Um valor");
        var quartzInitializer = new QuartzUtils();
        var configs = quartzInitializer
            .configsFromProps(props).stream()
            .sorted(Comparator.comparing(QuartzJobConfig::getName))
            .collect(Collectors.toList());
        System.out.println(configs.get(0));
        System.out.println(configs.get(1));
        assertEquals(2, configs.size());
        assertEquals("job1", configs.get(0).getName());
        assertEquals("org.chuckwalla.MeuJob1", configs.get(0).getJobClassName());
        assertEquals("job2", configs.get(1).getName());
        assertEquals("org.chuckwalla.MeuJob2", configs.get(1).getJobClassName());
        assertEquals(2000, configs.get(1).getOneTimeRunDelay());
        assertEquals("Um valor", configs.get(1).getParams().get("umaConfiguracao"));
    }

}
