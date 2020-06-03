package com.pepper.entity;

import java.util.ArrayList;
import java.util.List;

public class IOSReleaseInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public IOSReleaseInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIpaNameIsNull() {
            addCriterion("ipa_name is null");
            return (Criteria) this;
        }

        public Criteria andIpaNameIsNotNull() {
            addCriterion("ipa_name is not null");
            return (Criteria) this;
        }

        public Criteria andIpaNameEqualTo(String value) {
            addCriterion("ipa_name =", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameNotEqualTo(String value) {
            addCriterion("ipa_name <>", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameGreaterThan(String value) {
            addCriterion("ipa_name >", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameGreaterThanOrEqualTo(String value) {
            addCriterion("ipa_name >=", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameLessThan(String value) {
            addCriterion("ipa_name <", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameLessThanOrEqualTo(String value) {
            addCriterion("ipa_name <=", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameLike(String value) {
            addCriterion("ipa_name like", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameNotLike(String value) {
            addCriterion("ipa_name not like", value, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameIn(List<String> values) {
            addCriterion("ipa_name in", values, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameNotIn(List<String> values) {
            addCriterion("ipa_name not in", values, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameBetween(String value1, String value2) {
            addCriterion("ipa_name between", value1, value2, "ipaName");
            return (Criteria) this;
        }

        public Criteria andIpaNameNotBetween(String value1, String value2) {
            addCriterion("ipa_name not between", value1, value2, "ipaName");
            return (Criteria) this;
        }

        public Criteria andBundleVersionIsNull() {
            addCriterion("bundle_version is null");
            return (Criteria) this;
        }

        public Criteria andBundleVersionIsNotNull() {
            addCriterion("bundle_version is not null");
            return (Criteria) this;
        }

        public Criteria andBundleVersionEqualTo(String value) {
            addCriterion("bundle_version =", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionNotEqualTo(String value) {
            addCriterion("bundle_version <>", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionGreaterThan(String value) {
            addCriterion("bundle_version >", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionGreaterThanOrEqualTo(String value) {
            addCriterion("bundle_version >=", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionLessThan(String value) {
            addCriterion("bundle_version <", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionLessThanOrEqualTo(String value) {
            addCriterion("bundle_version <=", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionLike(String value) {
            addCriterion("bundle_version like", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionNotLike(String value) {
            addCriterion("bundle_version not like", value, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionIn(List<String> values) {
            addCriterion("bundle_version in", values, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionNotIn(List<String> values) {
            addCriterion("bundle_version not in", values, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionBetween(String value1, String value2) {
            addCriterion("bundle_version between", value1, value2, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andBundleVersionNotBetween(String value1, String value2) {
            addCriterion("bundle_version not between", value1, value2, "bundleVersion");
            return (Criteria) this;
        }

        public Criteria andDsymPathIsNull() {
            addCriterion("dsym_path is null");
            return (Criteria) this;
        }

        public Criteria andDsymPathIsNotNull() {
            addCriterion("dsym_path is not null");
            return (Criteria) this;
        }

        public Criteria andDsymPathEqualTo(String value) {
            addCriterion("dsym_path =", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathNotEqualTo(String value) {
            addCriterion("dsym_path <>", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathGreaterThan(String value) {
            addCriterion("dsym_path >", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathGreaterThanOrEqualTo(String value) {
            addCriterion("dsym_path >=", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathLessThan(String value) {
            addCriterion("dsym_path <", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathLessThanOrEqualTo(String value) {
            addCriterion("dsym_path <=", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathLike(String value) {
            addCriterion("dsym_path like", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathNotLike(String value) {
            addCriterion("dsym_path not like", value, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathIn(List<String> values) {
            addCriterion("dsym_path in", values, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathNotIn(List<String> values) {
            addCriterion("dsym_path not in", values, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathBetween(String value1, String value2) {
            addCriterion("dsym_path between", value1, value2, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andDsymPathNotBetween(String value1, String value2) {
            addCriterion("dsym_path not between", value1, value2, "dsymPath");
            return (Criteria) this;
        }

        public Criteria andBuildVersionIsNull() {
            addCriterion("build_version is null");
            return (Criteria) this;
        }

        public Criteria andBuildVersionIsNotNull() {
            addCriterion("build_version is not null");
            return (Criteria) this;
        }

        public Criteria andBuildVersionEqualTo(String value) {
            addCriterion("build_version =", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionNotEqualTo(String value) {
            addCriterion("build_version <>", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionGreaterThan(String value) {
            addCriterion("build_version >", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionGreaterThanOrEqualTo(String value) {
            addCriterion("build_version >=", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionLessThan(String value) {
            addCriterion("build_version <", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionLessThanOrEqualTo(String value) {
            addCriterion("build_version <=", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionLike(String value) {
            addCriterion("build_version like", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionNotLike(String value) {
            addCriterion("build_version not like", value, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionIn(List<String> values) {
            addCriterion("build_version in", values, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionNotIn(List<String> values) {
            addCriterion("build_version not in", values, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionBetween(String value1, String value2) {
            addCriterion("build_version between", value1, value2, "buildVersion");
            return (Criteria) this;
        }

        public Criteria andBuildVersionNotBetween(String value1, String value2) {
            addCriterion("build_version not between", value1, value2, "buildVersion");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}