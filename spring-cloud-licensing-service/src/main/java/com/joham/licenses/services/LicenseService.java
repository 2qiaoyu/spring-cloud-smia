package com.joham.licenses.services;

import com.joham.licenses.clients.OrganizationDiscoveryClient;
import com.joham.licenses.clients.OrganizationFeignClient;
import com.joham.licenses.clients.OrganizationRestTemplateClient;
import com.joham.licenses.config.ServiceConfig;
import com.joham.licenses.model.License;
import com.joham.licenses.model.Organization;
import com.joham.licenses.repository.LicenseRepository;
import com.joham.licenses.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 二、配置信息（default或HystrixCommandKey）最常用的几项
 * 超时时间（默认1000ms，单位：ms）
 * hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds
 * 在调用方配置，被该调用方的所有方法的超时时间都是该值，优先级低于下边的指定配置
 * hystrix.command.HystrixCommandKey.execution.isolation.thread.timeoutInMilliseconds
 * 在调用方配置，被该调用方的指定方法（HystrixCommandKey方法名）的超时时间是该值
 * 线程池核心线程数
 * hystrix.threadpool.default.coreSize（默认为10）
 * Queue
 * hystrix.threadpool.default.maxQueueSize（最大排队长度。默认-1，使用SynchronousQueue。其他值则使用 LinkedBlockingQueue。如果要从-1换成其他值则需重启，即该值不能动态调整，若要动态调整，需要使用到下边这个配置）
 * hystrix.threadpool.default.queueSizeRejectionThreshold（排队线程数量阈值，默认为5，达到时拒绝，如果配置了该选项，队列的大小是该队列）
 * 注意：如果maxQueueSize=-1的话，则该选项不起作用
 * 断路器
 * hystrix.command.default.circuitBreaker.requestVolumeThreshold（当在配置时间窗口内达到此数量的失败后，进行短路。默认20个）
 * For example, if the value is 20, then if only 19 requests are received in the rolling window (say a window of 10 seconds) the circuit will not trip open even if all 19 failed.
 * hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds（短路多久以后开始尝试是否恢复，默认5s）
 * hystrix.command.default.circuitBreaker.errorThresholdPercentage（出错百分比阈值，当达到此阈值后，开始短路。默认50%）
 * fallback
 * hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests（调用线程允许请求HystrixCommand.GetFallback()的最大数量，默认10。超出时将会有异常抛出，注意：该项配置对于THREAD隔离模式也起作用）
 * </p>
 *
 * @author joham
 */
@Service
@Slf4j
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;


    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;


    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    @HystrixCommand(
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")},
            threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")},
            fallbackMethod = "buildFallbackLicenseList", threadPoolKey = "licenseByOrgThreadPool")
    public List<License> getLicensesByOrg(String organizationId) {
        log.info("LicenseService.getLicensesByOrg  Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());

        licenseRepository.save(license);

    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.delete(license.getLicenseId());
    }

    private void randomlyRunLong() {
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName("Sorry no licensing information currently available");

        fallbackList.add(license);
        return fallbackList;
    }
}
