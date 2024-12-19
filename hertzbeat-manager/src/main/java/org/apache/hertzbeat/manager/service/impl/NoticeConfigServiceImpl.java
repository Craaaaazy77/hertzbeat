/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hertzbeat.manager.service.impl;

import jakarta.persistence.criteria.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hertzbeat.common.cache.CacheFactory;
import org.apache.hertzbeat.common.cache.CommonCacheService;
import org.apache.hertzbeat.common.constants.CommonConstants;
import org.apache.hertzbeat.common.entity.alerter.Alert;
import org.apache.hertzbeat.common.entity.alerter.GroupAlert;
import org.apache.hertzbeat.common.entity.alerter.NoticeReceiver;
import org.apache.hertzbeat.common.entity.alerter.NoticeRule;
import org.apache.hertzbeat.common.entity.alerter.NoticeTemplate;
import org.apache.hertzbeat.alert.notice.DispatcherAlarm;
import org.apache.hertzbeat.alert.dao.NoticeReceiverDao;
import org.apache.hertzbeat.alert.dao.NoticeRuleDao;
import org.apache.hertzbeat.alert.dao.NoticeTemplateDao;
import org.apache.hertzbeat.alert.service.NoticeConfigService;
import org.apache.hertzbeat.common.entity.alerter.SingleAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Message notification configuration implementation
 */
@Service
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class NoticeConfigServiceImpl implements NoticeConfigService, CommandLineRunner {

    private static final String ALERT_TEST_TARGET = "Test Target";

    private static final String ALERT_TEST_CONTENT = "test send msg! \\n This is the test data. It is proved that it can be received successfully";

    private static final Map<Byte, NoticeTemplate> PRESET_TEMPLATE = new HashMap<>(16);
    
    @Autowired
    private NoticeReceiverDao noticeReceiverDao;

    @Autowired
    private NoticeRuleDao noticeRuleDao;
    
    @Autowired
    private NoticeTemplateDao noticeTemplateDao;
    
    @Autowired
    @Lazy
    private DispatcherAlarm dispatcherAlarm;

    @Override
    public List<NoticeReceiver> getNoticeReceivers(String name) {
        Specification<NoticeReceiver> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(name)) {
                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicate = criteriaBuilder.and(predicateName);
            }
            return predicate;
        };
        return noticeReceiverDao.findAll(specification);
    }

    @Override
    public List<NoticeTemplate> getNoticeTemplates(String name) {
        Specification<NoticeTemplate> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(name)) {
                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicate = criteriaBuilder.and(predicateName);
            }
            return predicate;
        };
        List<NoticeTemplate> defaultTemplates = new LinkedList<>(PRESET_TEMPLATE.values());
        defaultTemplates.addAll(noticeTemplateDao.findAll(specification));
        return defaultTemplates;
    }

    @Override
    public List<NoticeRule> getNoticeRules(String name) {
        Specification<NoticeRule> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(name)) {
                Predicate predicateName = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicate = criteriaBuilder.and(predicateName);
            }
            return predicate;
        };
        return noticeRuleDao.findAll(specification);
    }

    @Override
    public void addReceiver(NoticeReceiver noticeReceiver) {
        noticeReceiverDao.save(noticeReceiver);
    }

    @Override
    public void editReceiver(NoticeReceiver noticeReceiver) {
        noticeReceiverDao.save(noticeReceiver);
    }

    @Override
    public void deleteReceiver(Long receiverId) {
        noticeReceiverDao.deleteById(receiverId);
    }

    @Override
    public void addNoticeRule(NoticeRule noticeRule) {
        noticeRuleDao.save(noticeRule);
        clearNoticeRulesCache();
    }

    @Override
    public void editNoticeRule(NoticeRule noticeRule) {
        noticeRuleDao.save(noticeRule);
        clearNoticeRulesCache();
    }

    @Override
    public void deleteNoticeRule(Long ruleId) {
        noticeRuleDao.deleteById(ruleId);
        clearNoticeRulesCache();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NoticeRule> getReceiverFilterRule(GroupAlert alert) {
        // use cache
        CommonCacheService<String, Object> noticeCache = CacheFactory.getNoticeCache();
        List<NoticeRule> rules = (List<NoticeRule>) noticeCache.get(CommonConstants.CACHE_NOTICE_RULE);
        if (CollectionUtils.isEmpty(rules)) {
            rules = noticeRuleDao.findNoticeRulesByEnableTrue();
            noticeCache.put(CommonConstants.CACHE_NOTICE_RULE, rules);
        }

        // The temporary rule is to forward all, and then implement more matching rules: alarm status selection, monitoring type selection, etc.
        return rules.stream()
                .filter(rule -> {
                    if (!rule.isFilterAll()) {
                        // filter labels
                        if (rule.getLabels() != null && !rule.getLabels().isEmpty()) {
                            boolean labelMatch = rule.getLabels().entrySet().stream().allMatch(labelItem -> {
                                if (!alert.getCommonLabels().containsKey(labelItem.getKey())) {
                                    return false;
                                }
                                String alertLabelValue = alert.getCommonLabels().get(labelItem.getKey());
                                return Objects.equals(labelItem.getValue(), alertLabelValue);
                            });
                            if (!labelMatch) {
                                return false;
                            }
                        }
                    }
                    
                    LocalDateTime nowDate = LocalDateTime.now();
                    // filter day
                    int currentDayOfWeek = nowDate.toLocalDate().getDayOfWeek().getValue();
                    if (rule.getDays() != null && !rule.getDays().isEmpty()) {
                        boolean dayMatch = rule.getDays().stream().anyMatch(item -> item == currentDayOfWeek);
                        if (!dayMatch) {
                            return false;
                        }
                    }
                    // filter time
                    LocalTime nowTime = nowDate.toLocalTime();
                    boolean startMatch = rule.getPeriodStart() == null
                            || nowTime.isAfter(rule.getPeriodStart().toLocalTime())
                            || (rule.getPeriodEnd() != null && rule.getPeriodStart().isAfter(rule.getPeriodEnd())
                                    && nowTime.isBefore(rule.getPeriodStart().toLocalTime()));
                    boolean endMatch = rule.getPeriodEnd() == null
                            || nowTime.isBefore(rule.getPeriodEnd().toLocalTime());
                    return startMatch && endMatch;
                })
                .collect(Collectors.toList());
    }

    @Override
    public NoticeTemplate getOneTemplateById(Long id) {
        return noticeTemplateDao.findById(id).orElse(null);
    }


    @Override
    public NoticeReceiver getReceiverById(Long receiverId) {
        return noticeReceiverDao.findById(receiverId).orElse(null);
    }

    @Override
    public NoticeRule getNoticeRulesById(Long ruleId) {
        return noticeRuleDao.findById(ruleId).orElse(null);
    }


    @Override
    public void addNoticeTemplate(NoticeTemplate noticeTemplate) {
        noticeTemplateDao.save(noticeTemplate);
        clearNoticeRulesCache();
    }

    @Override
    public void editNoticeTemplate(NoticeTemplate noticeTemplate) {
        noticeTemplateDao.save(noticeTemplate);
        clearNoticeRulesCache();
    }

    @Override
    public void deleteNoticeTemplate(Long templateId) {
        noticeTemplateDao.deleteById(templateId);
        clearNoticeRulesCache();
    }

    @Override
    public Optional<NoticeTemplate> getNoticeTemplatesById(Long templateId) {
        return noticeTemplateDao.findById(templateId);
    }

    @Override
    public NoticeTemplate getDefaultNoticeTemplateByType(Byte type) {
        if (type == null) {
            return null;
        }
        return PRESET_TEMPLATE.get(type);
    }

    @Override
    public boolean sendTestMsg(NoticeReceiver noticeReceiver) {
        Map<String, String> labels = new HashMap<>(8);
        labels.put(CommonConstants.TAG_MONITOR_ID, "100");
        labels.put(CommonConstants.TAG_MONITOR_NAME, "100Name");
        labels.put(CommonConstants.TAG_MONITOR_HOST, "127.0.0.1");
        labels.put(CommonConstants.TAG_THRESHOLD_ID, "200");
        SingleAlert singleAlert = SingleAlert.builder()
                .labels(labels)
                .content("test send msg! \\n This is the test data. It is proved that it can be received successfully")
                .startAt(System.currentTimeMillis())
                .endAt(System.currentTimeMillis())
                .triggerTimes(2)
                .annotations(labels)
                .status("firing")
                .build();
        GroupAlert groupAlert = GroupAlert.builder()
                .commonLabels(singleAlert.getLabels())
                .commonAnnotations(singleAlert.getAnnotations())
                .alerts(List.of(singleAlert))
                .status("firing")
                .build();
        return dispatcherAlarm.sendNoticeMsg(noticeReceiver, null, groupAlert);
    }

    private void clearNoticeRulesCache() {
        CommonCacheService<String, Object> noticeCache = CacheFactory.getNoticeCache();
        noticeCache.remove(CommonConstants.CACHE_NOTICE_RULE);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("load default notice template in internal jar");
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:templates/*.*");
            for (Resource resource : resources) {
                if (resource.getFilename() == null || (!resource.getFilename().endsWith("txt") && !resource.getFilename().endsWith("html"))) {
                    log.warn("Ignore the template file {}.", resource.getFilename());
                    continue;
                }
                try (InputStream inputStream = resource.getInputStream()) {
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    String content = new String(bytes, StandardCharsets.UTF_8);
                    NoticeTemplate template = new NoticeTemplate();
                    String name = resource.getFilename().replace(".txt", "").replace(".html", "");
                    String[] names = name.split("-");
                    if (names.length != 2) {
                        log.warn("Ignore the template file {}.", resource.getFilename());
                        continue;
                    }
                    byte type = Byte.parseByte(names[0]);
                    name = names[1];
                    template.setName(name);
                    template.setType(type);
                    template.setPreset(true);
                    template.setContent(content);
                    template.setGmtUpdate(LocalDateTime.now());
                    PRESET_TEMPLATE.put(template.getType(), template);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    log.error("Ignore this template file: {}.", resource.getFilename());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
