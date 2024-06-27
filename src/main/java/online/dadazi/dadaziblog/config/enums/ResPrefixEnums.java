package online.dadazi.dadaziblog.config.enums;

/**
 * 访问前缀
 *
 * @author lqk
 */

public enum ResPrefixEnums {

    /**
     * 后台接口前缀
     */
    SYS("sys"),

    /**
     * API接口前缀
     */
    API("api"),

    /**
     * PC接口前缀
     */
    PC(""),

    ;

    private final String prefix;

    ResPrefixEnums(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPathPrefix() {
        final String slash = "/";
        return slash + prefix;
    }

    /**
     * 根据控制器类所在包，判断是否加相应前缀
     *
     * @author yilabao
     * @date 2021年1月25日
     * @param c
     * @param restPrefix
     * @return boolean
     */
    public static boolean isAddPrefix(Class<?> c, ResPrefixEnums restPrefix) {
        final String className = c.getName();
        final String dot = ".";
        final boolean isProjectClass = className.indexOf("online.dadazi.dadaziblog") == 0;
        return isProjectClass && className.contains(dot + restPrefix.getPrefix() + dot);
    }

    /**
     * 根据控制器类所在包，获取接口前缀枚举
     *
     * @author yilabao
     * @date 2021年1月25日
     * @param c
     * @return RestPrefixEnum
     */
    public static ResPrefixEnums getRestPrefixEnum(Class<?> c) {
        ResPrefixEnums[] restPrefixEnumArr = ResPrefixEnums.values();
        for (int i = 0; i < restPrefixEnumArr.length; i++) {
            ResPrefixEnums restPrefix = restPrefixEnumArr[i];
            if (isAddPrefix(c, restPrefix)) {
                return restPrefix;
            }
        }
        return PC;
    }

}
