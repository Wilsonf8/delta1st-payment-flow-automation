package ui.base;

import java.nio.file.Path;

public final class DeviceProfile {
    private final String deviceName;
    private final String platformVersion;
    private final Path appPath;       // optional if app already installed
    private final String appPackage;  // Android
    private final String appActivity; // Android
    private final String sidebar;
    private final String bundleId;    // iOS

    private DeviceProfile(Builder b) {
        this.deviceName = b.deviceName;
        this.platformVersion = b.platformVersion;
        this.appPath = b.appPath;
        this.appPackage = b.appPackage;
        this.appActivity = b.appActivity;
        this.sidebar = b.amend;
        this.bundleId = b.bundleId;
    }

    public String getDeviceName() { return deviceName; }
    public String getPlatformVersion() { return platformVersion; }
    public Path getAppPath() { return appPath; }
    public String getAppPackage() { return appPackage; }
    public String getAppActivity() { return appActivity; }
    public String getSidebar() { return sidebar; }
    public String getBundleId() { return bundleId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String deviceName;
        private String platformVersion;
        private Path appPath;
        private String appPackage;
        private String appActivity;
        private String bundleId;
        private String amend;

        public Builder deviceName(String v) { this.deviceName = v; return this; }
        public Builder platformVersion(String v) { this.platformVersion = v; return this; }
        public Builder appPath(Path v) { this.appPath = v; return this; }
        public Builder appPackage(String v) { this.appPackage = v; return this; }
        public Builder appActivity(String v) { this.appActivity = v; return this; }
        public Builder bundleId(String v) { this.bundleId = v; return this; }

        public DeviceProfile build() { return new DeviceProfile(this); }
    }
}

