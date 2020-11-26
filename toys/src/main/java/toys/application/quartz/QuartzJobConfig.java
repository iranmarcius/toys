package toys.application.quartz;

import toys.MapToys;

import java.util.Map;

public class QuartzJobConfig {
    private String name;
    private String jobClassName;
    private String cronSchedule;
    private Integer oneTimeRunDelay;
    private Map<String, String> params;

    public QuartzJobConfig() {
    }

    public QuartzJobConfig(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("name=%s, className=%s, cronSchedule=%s, oneTimeRunDelay=%s, params=%s",
            name, jobClassName, cronSchedule, oneTimeRunDelay, MapToys.asString(params));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getCronSchedule() {
        return cronSchedule;
    }

    public void setCronSchedule(String cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    public Integer getOneTimeRunDelay() {
        return oneTimeRunDelay;
    }

    public void setOneTimeRunDelay(Integer oneTimeRunDelay) {
        this.oneTimeRunDelay = oneTimeRunDelay;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

}
