package com.pepper.entity;

import java.io.Serializable;

/**
 * @author
 * 
 */
public class IOSReleaseInfo implements Serializable {
    private Integer id;

    private String ipaName;

    private String bundleVersion;

    private String dsymPath;

    private String buildVersion;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpaName() {
        return ipaName;
    }

    public void setIpaName(String ipaName) {
        this.ipaName = ipaName;
    }

    public String getBundleVersion() {
        return bundleVersion;
    }

    public void setBundleVersion(String bundleVersion) {
        this.bundleVersion = bundleVersion;
    }

    public String getDsymPath() {
        return dsymPath;
    }

    public void setDsymPath(String dsymPath) {
        this.dsymPath = dsymPath;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        IOSReleaseInfo other = (IOSReleaseInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getIpaName() == null ? other.getIpaName() == null
                        : this.getIpaName().equals(other.getIpaName()))
                && (this.getBundleVersion() == null ? other.getBundleVersion() == null
                        : this.getBundleVersion().equals(other.getBundleVersion()))
                && (this.getDsymPath() == null ? other.getDsymPath() == null
                        : this.getDsymPath().equals(other.getDsymPath()))
                && (this.getBuildVersion() == null ? other.getBuildVersion() == null
                        : this.getBuildVersion().equals(other.getBuildVersion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIpaName() == null) ? 0 : getIpaName().hashCode());
        result = prime * result + ((getBundleVersion() == null) ? 0 : getBundleVersion().hashCode());
        result = prime * result + ((getDsymPath() == null) ? 0 : getDsymPath().hashCode());
        result = prime * result + ((getBuildVersion() == null) ? 0 : getBuildVersion().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ipaName=").append(ipaName);
        sb.append(", bundleVersion=").append(bundleVersion);
        sb.append(", dsymPath=").append(dsymPath);
        sb.append(", buildVersion=").append(buildVersion);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}